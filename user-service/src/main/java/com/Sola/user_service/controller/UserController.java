package com.Sola.user_service.controller;
import com.Sola.user_service.dto.UserLoginRequest;
import com.Sola.user_service.dto.UserRegistrationRequest;
import com.Sola.user_service.model.UserEntity;
import com.Sola.user_service.model.UserStatus;
import com.Sola.user_service.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
@Tag(name = "User service", description = "Operations for User Management")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    // Public - No Authentication Required!
    @PostMapping ("/create")
    @Operation(summary = "Create User Registration",
            description= "Register New User with Provided Details." +
                    "Note:Password provided will be securely hashed before storage to Database.")
    public String createUserRegistration(
            @Parameter(description = "User Registration Request", required = true)
            @RequestBody UserRegistrationRequest userRegistrationRequest){

        userService.createUser(userRegistrationRequest);
        return "User Registration Created Successfully";
    }
    // Protected - Requires JWT or OAuth2 token
    @GetMapping("/find/{id}")
    @Operation(summary = "Get User By id",
            description = "Retrieve User details by their ID.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "oauth2")})
    public ResponseEntity<UserRegistrationRequest> getUserId(
            @Parameter(description = "User id", required = true)
            @PathVariable("id") String id){
        UserRegistrationRequest userId = userService.getUserId(id);
        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    // Protected - Requires JWT or OAuth2 token
    @GetMapping("/search")
    @Operation(summary= "Search Users by Email or Address",
            description = "Search Users based on their email or address",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "oauth2")})
    public ResponseEntity<List<UserRegistrationRequest>>getUserEmailOrAddress(
            @Parameter(description = "User Email", required = false)
            @RequestParam(required = false) String email,

            @Parameter(description = "User Address", required = false)
            @RequestParam(required = false) String address){
        List<UserRegistrationRequest> users = userService.findByEmailOrAddress(email, address);
        return ResponseEntity.ok(users);
    }


    // Protected - Requires JWT or OAuth2 token
    @PutMapping("/update/{id}")
    @Operation(summary = "Update User",
            description = "Update existing User's details by their ID.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name ="oauth2")})
    public ResponseEntity<UserEntity> updateUser(
            @Parameter(description = "User id", required = true)
            @PathVariable String id,

            @Parameter(description = "Updated User details", required = true)
            @RequestBody UserRegistrationRequest userRegistrationRequest){
        UserEntity updatedUser = userService.updateUser(id,userRegistrationRequest);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    // Protected - Requires JWT or OAuth2 token
    @PutMapping("/status/{id}")
    @Operation(summary = "Update User Status",
            description= "Update Status of an existing User by their ID.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "oauth2")})
    public ResponseEntity<UserRegistrationRequest> updateUserStatus(
            @Parameter(description = "User id",required = true)
            @PathVariable String id,

            @Parameter(description = "New User Status", required = true)
            @RequestParam UserStatus status) {
        UserRegistrationRequest updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/login")
    @Operation(summary = "User Login",
            description = "Authenticate User by Email and Password. " +
                    "Note:The provided password will be securely compared with the hashed password stored in the Database")
    public ResponseEntity<UserEntity> loginUser(
            @Parameter(description = "User Login Request", required = true)
            @RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserEntity user = userService.LoginUser(userLoginRequest.getEmail(),userLoginRequest.getPassword());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Operation(summary = "User Logout",
    description = "User Logged Out Successfully without Token Validation or Revocation")
    public ResponseEntity<String> logoutUser(){

        return new ResponseEntity<>("User Logged Out Successfully", HttpStatus.OK);

    }
}

