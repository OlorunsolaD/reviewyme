package com.Sola.user_service.service.impl;
import com.Sola.user_service.dto.UserRegistrationRequest;
import com.Sola.user_service.exception.UserNotFoundException;
import com.Sola.user_service.model.UserEntity;
import com.Sola.user_service.model.UserRole;
import com.Sola.user_service.model.UserStatus;
import com.Sola.user_service.repository.UserRepository;
import com.Sola.user_service.service.interfac.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

   @Autowired
   public UserServiceImpl(UserRepository userRepository,
                          PasswordEncoder passwordEncoder){
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
    }


    public UserEntity createUser(UserRegistrationRequest userRegistrationRequest) {

       // Encode Password
       String hashedPassword = passwordEncoder.encode(userRegistrationRequest.getPassword());

       Set<UserRole> roles = new HashSet<>();
       roles.add(UserRole.ROLE_CLIENT);

       if (userRegistrationRequest.isAdmin()){
           roles.add(UserRole.ROLE_ADMIN); // Add Admin Role if requested

       }

       UserEntity userEntity = UserEntity.builder()
               .fullName(userRegistrationRequest.getFullName())
               .email(userRegistrationRequest.getEmail())
               .phoneNumber(userRegistrationRequest.getPhoneNumber())
               .password(hashedPassword)
               .Status(UserStatus.ACTIVE)
               .roles(roles)
               .build();

        return userRepository.save(userEntity);

    }


    public UserEntity updateUser(String id, UserRegistrationRequest userRegistrationRequest) {
        Optional<UserEntity> optionalUserEntity = userRepository.findUserById(id);

        // Check if user exists, if not, throw an exception or handle it accordingly
        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException("User with Id " + id + " not found");
        }

        // If user is found, update the entity with the new values
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setFullName(userRegistrationRequest.getFullName());
        userEntity.setEmail(userRegistrationRequest.getEmail());
        userEntity.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
        userEntity.setPassword(userRegistrationRequest.getPassword());

        // Save the updated entity back to the database
        return userRepository.save(userEntity);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')") // Allow only admins to update user status
    public UserRegistrationRequest updateUserStatus(String id, UserStatus status) {

        // Fetch the user by ID
        UserEntity userEntity = findUserStatusById(id);

        // Ensure the target user is a CLIENT
        ensureClientRole(userEntity);

        // Update the status and save the updated user entity to Repository
        userEntity.setStatus(status);
        UserEntity updatedUserEntity = userRepository.save(userEntity);

        // Map to UserRegistrationRequest DTO and return the response
        return mapToUserRegistrationRequest(updatedUserEntity);
    }

    @Override
    public UserRegistrationRequest findUserById(String id) throws UserNotFoundException {

       UserEntity userEntity = userRepository.findUserById(id)
               .orElseThrow(() -> new RuntimeException(" User with ID :" + id + " not found "));

       return mapToUserRegistrationRequest(userEntity);
    }

    public UserEntity findUserStatusById (String id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID:" + id + ",not found"));

    }
    // Ensure the user has the CLIENT role
    private void ensureClientRole(UserEntity userEntity) {
        if (!userEntity.getRoles().contains(UserRole.ROLE_CLIENT)) {
            throw new IllegalArgumentException("Only CLIENT users' status can be updated.");
        }

    }
    private UserRegistrationRequest mapToUserRegistrationRequest(UserEntity userEntity){

        return UserRegistrationRequest.builder()
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();
    }


   public UserEntity findByEmailAndPassword(String email, String rawPassword) {
       UserEntity userEntity = userRepository.findByEmail(email)
               .orElseThrow(() -> new UserNotFoundException("User with the email: " + email + "was not found"));
       if (passwordEncoder.matches(rawPassword, userEntity.getPassword())) {

       } else {
           throw new IllegalArgumentException("Invalid email or password");
       }
       return userEntity;
   }
}