package com.Sola.resume_upload_service.service;

import com.Sola.resume_upload_service.dto.ResumeUploadRequest;
import com.Sola.resume_upload_service.model.ResumeUploadEntity;
import com.Sola.resume_upload_service.repository.ResumeUploadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ResumeUploadServiceImpl implements ResumeUploadService {

    private final ResumeUploadRepository resumeUploadRepository;


    @Autowired
    public ResumeUploadServiceImpl(ResumeUploadRepository resumeUploadRepository) {
        this.resumeUploadRepository = resumeUploadRepository;
    }

    @Override
    public ResumeUploadEntity saveResume(ResumeUploadRequest resumeUploadRequest) throws IOException {
        MultipartFile file = resumeUploadRequest.getFile(); // Now getting MultipartFile
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot save empty file.");
        }

        String contentType = file.getContentType();
        if (!isAllowedMimeType(contentType)) {
            throw new IllegalStateException("Only PDF or DOCX files are allowed.");
        }

        ResumeUploadEntity resume = new ResumeUploadEntity();
        resume.setFileName(file.getOriginalFilename());
        resume.setData(file.getBytes()); // IOException could be thrown here
        return resumeUploadRepository.save(resume);
    }

    private boolean isAllowedMimeType(String contentType){
        return contentType != null && (contentType.equals("application/pdf") ||
                contentType.equals("application/msword") ||
                contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
    }

    @Override
    public Optional<ResumeUploadEntity> getResumeById(String id) {
        return resumeUploadRepository.findById(id);
    }

    @Override
    public ResumeUploadEntity updateResume(String id, ResumeUploadRequest resumeUploadRequest) throws IOException {
        return resumeUploadRepository.findById(id).map(existingResume -> {
            MultipartFile file = resumeUploadRequest.getFile();
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot update with empty file.");
            }
            updateResumeData(existingResume, file);  // Refactored logic into a helper method
            return resumeUploadRepository.save(existingResume);
        }).orElseThrow(() -> new RuntimeException("Resume not found with id " + id));
    }

    // Helper method to update the existing resume entity with new file data
    private void updateResumeData(ResumeUploadEntity resume, MultipartFile file) {
        resume.setFileName(file.getOriginalFilename());
        try {
            resume.setData(file.getBytes());  // File data (byte[]) is set here
        } catch (IOException e) {
            throw new RuntimeException("Failed to update file data", e);  // Wrap with a cause
        }

    }

    @Override
    public Optional<ResumeUploadEntity> deleteResumeById(String id) {
       ResumeUploadEntity resumeUploadEntity = resumeUploadRepository.findById(id)
               .orElseThrow(() ->new RuntimeException("Resume Not Found with id" + id));
       resumeUploadRepository.deleteById(id);
       return Optional.of(resumeUploadEntity);
    }
}