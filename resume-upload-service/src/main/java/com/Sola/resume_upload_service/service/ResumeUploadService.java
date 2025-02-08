package com.Sola.resume_upload_service.service;

import com.Sola.resume_upload_service.dto.ResumeUploadRequest;
import com.Sola.resume_upload_service.model.ResumeUploadEntity;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ResumeUploadService {

    ResumeUploadEntity uploadResume(ResumeUploadRequest resumeUploadRequest);
    ResumeUploadEntity getResumeById(String id);
    ResumeUploadEntity updateResume(String id, ResumeUploadRequest resumeUploadRequest);
    List<ResumeUploadEntity> GetAllResumesByUserId (String userId);
    void deleteResumeById(String id);
}
