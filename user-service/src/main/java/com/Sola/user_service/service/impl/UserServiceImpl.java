package com.Sola.user_service.service.impl;
import com.Sola.user_service.dto.UserRegistrationRequest;
import com.Sola.user_service.exception.UserNotFoundException;
import com.Sola.user_service.model.UserEntity;
import com.Sola.user_service.model.UserStatus;
import com.Sola.user_service.repository.UserRepo;
import com.Sola.user_service.service.interfac.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserServiceImpl implements UserServiceInterface {

    private final UserRepo userRepo;
   // private final PasswordEncoder passwordEncoder;

   @Autowired
   public UserServiceImpl(UserRepo userRepo){
       this.userRepo = userRepo;
       //this.PasswordEncoder = passwordEncoder;
    }


    public UserEntity createUser(UserRegistrationRequest userRegistrationRequest) {
       UserEntity userEntity = UserEntity.builder()
               .fullName(userRegistrationRequest.getFullName())
               .email(userRegistrationRequest.getEmail())
               .phoneNumber(userRegistrationRequest.getPhoneNumber())
               .address(userRegistrationRequest.getAddress())
               .password(userRegistrationRequest.getPassword())
               .build();
       // String hashedPassword = passwordEncoder.encode(userRegistrationRequest.getPassword());
        //        user.setPassword(hashedPassword);

        return userRepo.save(userEntity);

    }


    public UserEntity updateUser(String id, UserRegistrationRequest userRegistrationRequest) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);

        // Check if user exists, if not, throw an exception or handle it accordingly
        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException("User with Id " + id + " not found");
        }

        // If user is found, update the entity with the new values
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setFullName(userRegistrationRequest.getFullName());
        userEntity.setEmail(userRegistrationRequest.getEmail());
        userEntity.setPhoneNumber(userRegistrationRequest.getPhoneNumber());
        userEntity.setAddress(userRegistrationRequest.getAddress());
        userEntity.setPassword(userRegistrationRequest.getPassword());

        // Save the updated entity back to the database
        return userRepo.save(userEntity);
    }



    @Override
    public UserRegistrationRequest getUserId(String id) {
       Optional<UserEntity> optionalUserEntity = userRepo.findById(id);
       if (optionalUserEntity.isEmpty()) {
           throw new UserNotFoundException("User with ID " + id + " not found");
       }

           UserEntity userEntity = optionalUserEntity.get();
           UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest();
           userRegistrationRequest.setFullName(userEntity.getFullName());
           userRegistrationRequest.setEmail(userEntity.getEmail());
           userRegistrationRequest.setPhoneNumber(userEntity.getPhoneNumber());
           userRegistrationRequest.setAddress(userEntity.getAddress());
//           userRegistrationRequest.setStatus(userEntity.getStatus());

           return userRegistrationRequest;

    }

    @Override
    public List<UserRegistrationRequest> findByEmailOrAddress(String email, String address) {
        List<UserEntity> users = userRepo.findByEmailOrAddress(email, address);


        if (users.isEmpty()) {
            String errorMessage = "No users found with the provided " +
                    (email != null ? "email: " + email : "") +
                    (email != null && address != null ? " or " : "") +
                    (address != null ? "address: " + address : "");

            throw new UserNotFoundException(errorMessage);
        }

        return users.stream()
                .map(userEntity -> UserRegistrationRequest.builder()
                        .fullName(userEntity.getFullName())
                        .email(userEntity.getEmail())
                        .phoneNumber(userEntity.getPhoneNumber())
                        .address(userEntity.getAddress())
//                        .status(userEntity.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    public UserRegistrationRequest updateUserStatus(String id, UserStatus status) {
        Optional<UserEntity> optionalUserEntity = userRepo.findById(id);

        if (optionalUserEntity.isEmpty()) {
            throw new UserNotFoundException("User with ID " + id + " not found");
        }

        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setStatus(status); // Update status

        UserEntity updatedUserEntity = userRepo.save(userEntity);

        return UserRegistrationRequest.builder()
                .fullName(updatedUserEntity.getFullName())
                .email(updatedUserEntity.getEmail())
                .phoneNumber(updatedUserEntity.getPhoneNumber())
                .address(updatedUserEntity.getAddress())
                .status(updatedUserEntity.getStatus())
                .build();
    }

   public UserEntity LoginUser(String email, String password) {
      return userRepo.findByEmailAndPassword (email, password)
                .orElseThrow(() -> new UserNotFoundException("User with the email: "+email+" was not found"));
  }

}


