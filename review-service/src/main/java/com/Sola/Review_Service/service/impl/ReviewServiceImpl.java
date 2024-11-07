package com.Sola.Review_Service.service.impl;

import com.Sola.Review_Service.model.ReviewEntity;
import com.Sola.Review_Service.model.ReviewStatus;
import com.Sola.Review_Service.model.ReviewerEntity;
import com.Sola.Review_Service.repository.ReviewRepository;
import com.Sola.Review_Service.repository.ReviewerRepository;
import com.Sola.Review_Service.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewerRepository reviewerRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewerRepository reviewerRepository,
                             ReviewRepository reviewRepository){
        this.reviewerRepository = reviewerRepository;
        this.reviewRepository = reviewRepository;

    }

    @Override
    public ReviewEntity assignReviewerToResume(String resumeId) {

        List<ReviewerEntity> reviewers = reviewerRepository.findAll();

        if (reviewers.isEmpty()) {
            throw new RuntimeException("No reviewers are registered in the system.");
        }

        // Find the reviewer with the least number of pending reviews
        ReviewerEntity selectedReviewer = reviewers.stream()
                .min(Comparator.comparingInt(this::countPendingReviews))
                .orElseThrow(() -> new RuntimeException("Unable to assign a reviewer."));

        // Create a new ReviewEntity
        ReviewEntity reviewEntity = ReviewEntity.builder()
                .resumeId(resumeId)
                .reviewerId(selectedReviewer.getId())
                .Status(ReviewStatus.PENDING_REVIEW)
                .startedAt(LocalDateTime.now())
                .build();

        // Save the new ReviewEntity
        reviewEntity = reviewRepository.save(reviewEntity);

        return reviewEntity;
    }

    private int countPendingReviews(ReviewerEntity reviewerEntity) {

        return reviewRepository.countByReviewerIdAndStatusIn(
                reviewerEntity.getId(),
                List.of(ReviewStatus.PENDING_REVIEW, ReviewStatus.UNDER_REVIEW)
        );
    }
}
