package com.Sola.user_service.service.interfac;

import com.Sola.user_service.dto.UserRegistrationRequest;
import com.Sola.user_service.exception.UserNotFoundException;
import com.Sola.user_service.model.UserEntity;
import com.Sola.user_service.model.UserStatus;

import java.util.List;


public interface UserServiceInterface {
    public UserEntity createUser(UserRegistrationRequest userRegistrationRequest);

    public UserEntity LoginUser(String email, String password) throws UserNotFoundException;

    public UserEntity updateUser(String id, UserRegistrationRequest userRegistrationRequest);

    public UserRegistrationRequest updateUserStatus(String id, UserStatus status) throws UserNotFoundException;

    public UserRegistrationRequest getUserId (String id) throws UserNotFoundException;

    List<UserRegistrationRequest> findByEmailOrAddress(String email, String address) throws UserNotFoundException;


}
