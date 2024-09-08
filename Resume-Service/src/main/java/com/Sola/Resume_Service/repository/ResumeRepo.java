package com.Sola.Resume_Service.repository;

import com.Sola.Resume_Service.model.ResumeEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ResumeRepo extends MongoRepository<ResumeEntity,String> {
    Optional<ResumeEntity> getResumeById(String id);
}
