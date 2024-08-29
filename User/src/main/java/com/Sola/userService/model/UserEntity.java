package com.Sola.userService.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@Builder
@Document(collection = "registration")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private String id;


    private String username;
    private String password;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private String role; //client and admin
    private String resumeId; // Reference to resumes in Resume-Service
    private UserStatus Status;

}