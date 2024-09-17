package com.Sola.resume_creation_service.service;

import com.Sola.resume_creation_service.dto.ResumeCreationRequest;
import com.Sola.resume_creation_service.model.Resume;

import java.util.List;

public interface ResumeCreationService {

    Resume createResume(ResumeCreationRequest resumeCreationRequest);
    Resume updateResume(Resume resume);
    Resume getResumeById (Long id);
    List<Resume> getAllResumes ();
    void deleteResumeById (Long id);


}
