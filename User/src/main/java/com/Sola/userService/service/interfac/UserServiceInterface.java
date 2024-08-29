package com.Sola.userService.service.interfac;

import com.Sola.userService.dto.UserLoginRequest;
import com.Sola.userService.dto.UserRegistrationRequest;
import com.Sola.userService.exception.UserNotFoundException;
import com.Sola.userService.model.UserEntity;
import com.Sola.userService.model.UserStatus;

import java.util.List;


public interface UserServiceInterface {
    public UserEntity createUser(UserRegistrationRequest userRegistrationRequest);

    public UserEntity LoginUser(String email, String password) throws UserNotFoundException;

    public UserEntity updateUser(String id, UserRegistrationRequest userRegistrationRequest);

    public UserRegistrationRequest updateUserStatus(String id, UserStatus status) throws UserNotFoundException;

    public UserRegistrationRequest getUserId (String id) throws UserNotFoundException;

    List<UserRegistrationRequest> findByEmailOrAddress(String email, String address) throws UserNotFoundException;


}
