package com.Sola.user_service.controller;
import com.Sola.user_service.dto.UserLoginRequest;
import com.Sola.user_service.dto.UserRegistrationRequest;
import com.Sola.user_service.dto.UserResponseDto;
import com.Sola.user_service.model.UserEntity;
import com.Sola.user_service.model.UserStatus;
import com.Sola.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User service", description = "Operations for User Management")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // Public - No Authentication Required!
    @PostMapping ("/register")
    @Operation(summary = "Create User Registration",
            description= "Register New User with Provided Details." +
                    "Note:Password provided will be securely hashed before storage to Database.")

    public ResponseEntity<UserResponseDto> registerUser(
            @Parameter(description = "User Registration Request", required = true)
            @Valid @RequestPart("userRegistrationRequest") UserRegistrationRequest userRegistrationRequest,
            @RequestPart(value = "resumeFile", required = false)MultipartFile file) {

        // Create user and optionally create a resume and upload a file
        UserEntity createdUser = userService.createUserandResume(userRegistrationRequest, file);

        // Map the created user to a UserResponseDto and return it as the response
        UserResponseDto userResponseDto = new UserResponseDto(
                createdUser.getId(),
                createdUser.getFullName(),
                createdUser.getEmail()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    // Public - No Authentication Required!
    @GetMapping("/find/{id}")
    @Operation(summary = "Get User By id",
            description = "User -> Retrieve User details by their ID.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "oauth2")})
    public ResponseEntity<UserRegistrationRequest> getUserById(
            @Parameter(description = "User id", required = true)
            @PathVariable("id") String id){

        UserRegistrationRequest userRegistrationRequest = userService.findUserById(id);
        return new ResponseEntity<>(userRegistrationRequest, HttpStatus.OK);
    }



    // Private - ADMIN CAN CHECK USER STATUS BY ID AND UPDATE USER STATUS
    @PutMapping("/status/{id}")
    @Operation(summary = "Update User Status",
            description= "Admin Update Status of an existing User by their ID.",
            security = {@SecurityRequirement(name = "bearerAuth"), @SecurityRequirement(name = "oauth2")})

    @PreAuthorize("hasRole('ADMIN')") // Only admins can change user status
    public ResponseEntity<UserRegistrationRequest> updateUserStatusById(
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
        UserEntity user = userService.findByEmailAndPassword(userLoginRequest.getEmail(),
                userLoginRequest.getPassword());

        if (passwordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


    }

    @PostMapping("/logout")
    @Operation(summary = "User Logout",
    description = "User Logged Out Successfully without Token Validation or Revocation")
    public ResponseEntity<String> logoutUser(){

        return new ResponseEntity<>("User Logged Out Successfully", HttpStatus.OK);

    }
}

