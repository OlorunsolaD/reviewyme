package com.Sola.resume_upload_service.service.impl;

import com.Sola.resume_upload_service.dto.ResumeUploadRequest;
import com.Sola.resume_upload_service.model.ResumeUploadEntity;
import com.Sola.resume_upload_service.repository.ResumeUploadRepository;
import com.Sola.resume_upload_service.service.ResumeUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeUploadServiceImpl implements ResumeUploadService {

    private final ResumeUploadRepository resumeUploadRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public ResumeUploadServiceImpl(ResumeUploadRepository resumeUploadRepository) {
        this.resumeUploadRepository = resumeUploadRepository;
    }

    @Override
    public ResumeUploadEntity uploadResume(ResumeUploadRequest resumeUploadRequest) {
        MultipartFile file = resumeUploadRequest.getFilePart(); // Now getting MultipartFile

        validateFile(file);

        try {

            ResumeUploadEntity resume = ResumeUploadEntity.builder()
                    .fileName(file.getOriginalFilename())
                    .filePart(file.getContentType())
                    .fileSize(file.getSize())
                    .data(file.getBytes())
                    .userId(resumeUploadRequest.getUserId())
                    .build();

            return resumeUploadRepository.save(resume);

        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(),e);


        }

    }

    @Override
    public ResumeUploadEntity getResumeById(String id) {
        return resumeUploadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resume not found with ID: " + id));
    }

    public List<ResumeUploadEntity> GetAllResumesByUserId (String userId) {
        return resumeUploadRepository.findByUserId(userId);

}

    @Override
    public ResumeUploadEntity updateResume(String id, ResumeUploadRequest resumeUploadRequest){
        MultipartFile file = resumeUploadRequest.getFilePart();
        validateFile(file);

        try {
            ResumeUploadEntity existingUploadResume = resumeUploadRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Resume not found with ID: " + id));

            existingUploadResume.setFileName(file.getOriginalFilename());
            existingUploadResume.setFilePart(file.getContentType());
            existingUploadResume.setFileSize(file.getSize());
            existingUploadResume.setUserId(resumeUploadRequest.getUserId());
            existingUploadResume.setData(file.getBytes());

            return resumeUploadRepository.save(existingUploadResume);
        } catch (IOException e){
            throw new RuntimeException("Failed to update file " + file.getOriginalFilename(), e);
        }

    }

    @Override
    public void deleteResumeById(String id) {
        ResumeUploadEntity resumeUploadEntity = getResumeById(id);
        resumeUploadRepository.deleteById(id);
    }

    private void validateFile(MultipartFile file) {

        if (file.isEmpty()) {
            throw new RuntimeException("Empty file");
        }
        if (!List.of("application/pdf", "application/msword",
                        "application/application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                .contains(file.getContentType())) {
            throw new RuntimeException("Invalid file type! Only PDF and DOCX files are allowed.");
        }
        if (file.getSize() > 5 * 1024 * 1024) { // 5MB Limit
            throw new RuntimeException("File Size exceeds 5MB Limit.");
        }
    }
}