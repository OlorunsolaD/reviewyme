package com.Sola.user_service.service.interfac;

import com.Sola.user_service.dto.UserRegistrationRequest;
import com.Sola.user_service.exception.UserNotFoundException;
import com.Sola.user_service.model.UserEntity;
import com.Sola.user_service.model.UserStatus;


public interface UserService {
    UserEntity createUser(UserRegistrationRequest userRegistrationRequest);

    UserEntity findByEmailAndPassword (String email, String password) throws UserNotFoundException;

    UserEntity updateUser(String id, UserRegistrationRequest userRegistrationRequest);

    UserRegistrationRequest updateUserStatus(String id, UserStatus status) throws UserNotFoundException;

    UserRegistrationRequest findUserById (String id) throws UserNotFoundException;

    UserEntity findUserStatusById (String id);

}
