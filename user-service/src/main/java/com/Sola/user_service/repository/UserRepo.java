package com.Sola.user_service.repository;

import com.Sola.user_service.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepo extends MongoRepository <UserEntity,String>{

    List<UserEntity> findByEmailOrAddress(String email, String address);
    Optional<UserEntity> findByEmailAndPassword(String email, String password);
}
