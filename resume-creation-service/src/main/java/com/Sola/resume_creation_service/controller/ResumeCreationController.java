package com.Sola.resume_creation_service.controller;

import com.Sola.resume_creation_service.dto.ResumeCreationRequest;
import com.Sola.resume_creation_service.model.Resume;
import com.Sola.resume_creation_service.service.ResumeCreationService;
import jakarta.validation.Valid;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
public class ResumeCreationController {

    private final ResumeCreationService resumeCreationService;

    public ResumeCreationController(ResumeCreationService resumeCreationService){
        this.resumeCreationService = resumeCreationService;
    }

    @PostMapping("/create")
    public ResponseEntity<Resume> createResume (@Valid @RequestBody ResumeCreationRequest resumeCreationRequest){
        Resume resume = resumeCreationService.createResume(resumeCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(resume);

    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Resume> updateResume (@PathVariable Long id){
        Resume resume = resumeCreationService.getResumeById(id);

        // Save the updated resume
        Resume updatedResume = resumeCreationService.updateResume(id, ResumeCreationRequest.builder().build());
        return ResponseEntity.status(HttpStatus.OK).body(updatedResume);
    }

    @PutMapping("/{id}/under_review") // Admin Only Access point
    @PreAuthorize("hasRole ('ADMIN')")
    public ResponseEntity<String> markResumeUnderReview (@PathVariable Long id){
        resumeCreationService.markResumeCreatedUnderReview(id);
        return ResponseEntity.status(HttpStatus.OK).body("Resume marked as UNDER_REVIEW.");
    }

    @PutMapping("/{id}/completed") // Admin Only Access point
    @PreAuthorize("hasRole ('ADMIN')")
    public ResponseEntity<String> markResumeCompleted (@PathVariable Long id){
        resumeCreationService.markResumeCreatedCompleted(id);
        return ResponseEntity.status(HttpStatus.OK).body("Resume marked as COMPLETED");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resume>> getAllResumes (){
        List<Resume> resumes = resumeCreationService.getAllResumes();
        return ResponseEntity.status(HttpStatus.OK).body(resumes);

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Resume> getResumeById (@PathVariable Long id){

        Resume resume = resumeCreationService.getResumeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(resume);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteResumeById (@PathVariable Long id){
            resumeCreationService.deleteResumeById(id);
            return ResponseEntity.noContent().build();
    }
}

