package com.Sola.resume_creation_service.controller;

import com.Sola.resume_creation_service.dto.ResumeCreationRequest;
import com.Sola.resume_creation_service.model.Resume;
import com.Sola.resume_creation_service.service.ResumeCreationService;
import jakarta.validation.Valid;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PutMapping("/{id}/complete-review")
    public ResponseEntity<Resume> completeResumeReview (@PathVariable Long id){
        Resume resume = resumeCreationService.getResumeById(id);

        // update status as completed
//        resume.setStatus("COMPLETED");

        // Save the updated resume
        Resume updatedResume = resumeCreationService.updateResume(id, ResumeCreationRequest.builder().build());
        return ResponseEntity.status(HttpStatus.OK).body(updatedResume);
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

    @DeleteMapping("/delete{id}/")
    public ResponseEntity<Void> deleteResumeById (@PathVariable Long id){
            resumeCreationService.deleteResumeById(id);
            return ResponseEntity.noContent().build();
    }
}

