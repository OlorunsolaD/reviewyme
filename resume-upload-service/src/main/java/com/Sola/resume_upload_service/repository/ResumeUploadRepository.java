package com.Sola.resume_upload_service.repository;

import com.Sola.resume_upload_service.model.ResumeUploadEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeUploadRepository extends MongoRepository<ResumeUploadEntity,String> {
    Optional<ResumeUploadEntity> getResumeById(String id);
    List<ResumeUploadEntity> findByUserId (String userId);
}
