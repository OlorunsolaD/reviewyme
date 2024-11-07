package com.Sola.user_service.service;

import com.Sola.user_service.dto.UserRegistrationRequest;
import com.Sola.user_service.exception.UserNotFoundException;
import com.Sola.user_service.model.UserEntity;
import com.Sola.user_service.model.UserStatus;
import org.springframework.web.multipart.MultipartFile;


public interface UserService {
    UserEntity createUserandResume(UserRegistrationRequest userRegistrationRequest, MultipartFile file);

    UserEntity findByEmailAndPassword (String email, String rawPassword) throws UserNotFoundException;

    UserEntity updateUser(String id, UserRegistrationRequest userRegistrationRequest);

    UserRegistrationRequest updateUserStatus(String id, UserStatus status) throws UserNotFoundException;

    UserRegistrationRequest findUserById (String id) throws UserNotFoundException;

    UserEntity findUserStatusById (String id);

}
