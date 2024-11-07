package com.Sola.Review_Service.service;

import com.Sola.Review_Service.model.ReviewEntity;

public interface ReviewService {

    ReviewEntity assignReviewerToResume (String resumeId);
}
