package com.Sola.Resume_Service.service;

import com.Sola.Resume_Service.dto.ResumeRequest;
import com.Sola.Resume_Service.model.ResumeEntity;
import com.Sola.Resume_Service.repository.ResumeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepo resumeRepo;


    @Autowired
    public ResumeServiceImpl(ResumeRepo resumeRepo) {
        this.resumeRepo = resumeRepo;
    }

    @Override
    public ResumeEntity saveResume(ResumeRequest resumeRequest) throws IOException {
        MultipartFile file = resumeRequest.getFile(); // Now getting MultipartFile
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot save empty file.");
        }

        String contentType = file.getContentType();
        if (!isAllowedMimeType(contentType)) {
            throw new IllegalStateException("Only PDF or DOCX files are allowed.");
        }

        ResumeEntity resume = new ResumeEntity();
        resume.setFileName(file.getOriginalFilename());
        resume.setData(file.getBytes()); // IOException could be thrown here
        return resumeRepo.save(resume);
    }

    private boolean isAllowedMimeType(String contentType){
        return contentType != null && (contentType.equals("application/pdf") ||
                contentType.equals("application/msword") ||
                contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
    }

    @Override
    public Optional<ResumeEntity> getResumeById(String id) {
        return resumeRepo.findById(id);
    }

    @Override
    public ResumeEntity updateResume(String id, ResumeRequest resumeRequest) throws IOException {
        return resumeRepo.findById(id).map(existingResume -> {
            MultipartFile file = resumeRequest.getFile();
            if (file.isEmpty()) {
                throw new IllegalStateException("Cannot update with empty file.");
            }
            updateResumeData(existingResume, file);  // Refactored logic into a helper method
            return resumeRepo.save(existingResume);
        }).orElseThrow(() -> new RuntimeException("Resume not found with id " + id));
    }

    // Helper method to update the existing resume entity with new file data
    private void updateResumeData(ResumeEntity resume, MultipartFile file) {
        resume.setFileName(file.getOriginalFilename());
        try {
            resume.setData(file.getBytes());  // File data (byte[]) is set here
        } catch (IOException e) {
            throw new RuntimeException("Failed to update file data", e);  // Wrap with a cause
        }

    }

    @Override
    public Optional<ResumeEntity> deleteResumeById(String id) {
       ResumeEntity resumeEntity= resumeRepo.findById(id)
               .orElseThrow(() ->new RuntimeException("Resume Not Found with id" + id));
       resumeRepo.deleteById(id);
       return Optional.of(resumeEntity);
    }
}