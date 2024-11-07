package com.Sola.Review_Service.repository;

import com.Sola.Review_Service.model.ReviewEntity;
import com.Sola.Review_Service.model.ReviewStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends MongoRepository<ReviewEntity, String> {

//    Optional<ReviewEntity> findByStatus(String status);
int countByReviewerIdAndStatusIn(String reviewerId, List<ReviewStatus> statuses);
}
