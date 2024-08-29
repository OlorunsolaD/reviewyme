package com.Sola.userService.controller;
import com.Sola.userService.dto.UserLoginRequest;
import com.Sola.userService.dto.UserRegistrationRequest;
import com.Sola.userService.model.UserEntity;
import com.Sola.userService.model.UserStatus;
import com.Sola.userService.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // Sorted
    @PostMapping ("/create")
    public String createUserRegistration(@RequestBody UserRegistrationRequest userRegistrationRequest){

        userService.createUser(userRegistrationRequest);
        return "User Registration Created Successfully";
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<UserRegistrationRequest> getUserId(@PathVariable("id") String id){
        UserRegistrationRequest userId = userService.getUserId(id);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<List<UserRegistrationRequest>>getUserEmailOrAddress(@RequestParam(required = false)
                                                                                  String email,
                                                                              @RequestParam(required = false)
                                                                              String address){
        List<UserRegistrationRequest> users = userService.findByEmailOrAddress(email, address);
        return ResponseEntity.ok(users);
    }


    // Sorted
    @PutMapping("/update/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String id,
                                                 @RequestBody UserRegistrationRequest userRegistrationRequest){
        UserEntity updatedUser = userService.updateUser(id,userRegistrationRequest);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<UserRegistrationRequest> updateUserStatus(
            @PathVariable String id,
            @RequestParam UserStatus status) {
        UserRegistrationRequest updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<UserEntity> loginUser(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserEntity user = userService.LoginUser(userLoginRequest.getEmail(),userLoginRequest.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

