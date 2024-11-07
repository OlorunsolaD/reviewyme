package com.Sola.Review_Service.repository;

import com.Sola.Review_Service.model.ReviewerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewerRepository extends MongoRepository<ReviewerEntity, String> {
    Optional<ReviewerEntity> findFirstByAvailableTrue();
}
