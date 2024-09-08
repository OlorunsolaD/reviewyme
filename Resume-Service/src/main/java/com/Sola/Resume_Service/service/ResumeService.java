package com.Sola.Resume_Service.service;

import com.Sola.Resume_Service.dto.ResumeRequest;
import com.Sola.Resume_Service.model.ResumeEntity;

import java.io.IOException;
import java.util.Optional;

public interface ResumeService {

    ResumeEntity saveResume(ResumeRequest resumeRequest) throws IOException;
    Optional<ResumeEntity> getResumeById(String id);
    ResumeEntity updateResume(String id, ResumeRequest resumeRequest) throws IOException;
    Optional<ResumeEntity> deleteResumeById(String id);
}
