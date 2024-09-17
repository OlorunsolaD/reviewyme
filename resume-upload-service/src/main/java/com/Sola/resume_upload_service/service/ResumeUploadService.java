package com.Sola.resume_upload_service.service;

import com.Sola.resume_upload_service.dto.ResumeUploadRequest;
import com.Sola.resume_upload_service.model.ResumeUploadEntity;

import java.io.IOException;
import java.util.Optional;

public interface ResumeUploadService {

    ResumeUploadEntity saveResume(ResumeUploadRequest resumeUploadRequest) throws IOException;
    Optional<ResumeUploadEntity> getResumeById(String id);
    ResumeUploadEntity updateResume(String id, ResumeUploadRequest resumeUploadRequest) throws IOException;
    Optional<ResumeUploadEntity> deleteResumeById(String id);
}
