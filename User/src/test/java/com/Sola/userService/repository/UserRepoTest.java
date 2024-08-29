package com.Sola.userService.repository;

import com.Sola.userService.model.UserEntity;
import com.Sola.userService.model.UserStatus;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataMongoTest
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;
    private UserEntity user1;
    private UserEntity user2;


    @BeforeEach
    void setUp() {
        user1 = new UserEntity("12349", "Corn", "r1234",
                "Maze Down", "Cornith@gmail.com", "Building Street 1256",
                "08901727", "client", "resume123", UserStatus.ACTIVE);

        user2 = new UserEntity("3498", "Gold", "g1234",
                "Gaze Pown", "Goldith@gmail.com", "Building Street 34562",
                "99801727", "client", "resume550", UserStatus.INACTIVE);

        userRepo.save(user1);
        userRepo.save(user2);


    }

    @AfterEach
    void tearDown() {
        userRepo.deleteAll();

    }

    // Test Case to Pass
    @Test
    void findByEmailOrAddress_Found() {

        List<UserEntity> foundUsers = userRepo.findByEmailOrAddress(null,
                "Building Street 1256");
        assertThat(foundUsers.get(0).getEmail()).isEqualTo(user1.getEmail());
        assertThat(foundUsers.get(0).getAddress()).isEqualTo(user1.getAddress());

    }

    // Test Case to Pass if EmailOrAddress Not Found
    @Test
    void findByEmailOrAddress_NotFound() {
        List<UserEntity> foundUsers = userRepo.findByEmailOrAddress("doesnotexist@gmail.com",
                "Nonexistent Address");
        assertThat(foundUsers.isEmpty()).isTrue();

    }

    // Test Case to Success
    @Test
    void findUsernameOrPassword_Found(){
        Optional<UserEntity> FoundUserDetails=userRepo.findByEmailAndPassword("Goldith@gmail.com",
                "g1234");
        assertThat(FoundUserDetails.isPresent());
        assertThat(FoundUserDetails.get().getUsername()).isEqualTo("Gold");

    }

    // Test Case to Pass if no Username or Password Found
    @Test
    void findUsernameOrPassword_NotFound(){
        // Test when email and password don't match
        Optional<UserEntity> FoundUserDetails=userRepo.findByEmailAndPassword("notexist@gmail.com",
                "blank");
        // Validate that no user is found
        assertThat(FoundUserDetails).isNotPresent();
    }

    @Test
    void testFindById() {
        // Attempt to retrieve the user by ID
        Optional<UserEntity> foundUser = userRepo.findById("12349");

        // Validate that the user was found and matches the expected values
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("Corn");
        assertThat(foundUser.get().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }




}


