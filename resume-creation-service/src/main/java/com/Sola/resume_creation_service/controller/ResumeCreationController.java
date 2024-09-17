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
@RequestMapping("/api/resumesTemplates")
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
    public ResponseEntity<Resume> completeResumeReview (@Valid @PathVariable Long id){
        Resume resume = resumeCreationService.getResumeById(id);

        if (resume == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, " Resume not found with id: " + id);
        }

        // update status as completed
        resume.setStatus("COMPLETED");
        Resume updatedResume = resumeCreationService.updateResume(resume);

        return ResponseEntity.status(HttpStatus.OK).body(updatedResume);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resume>> getAllResumes (){

        List<Resume> resumes = resumeCreationService.getAllResumes();

        return ResponseEntity.status(HttpStatus.OK).body(resumes);

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Resume> getResumeById (@Valid @PathVariable Long id){

        Resume resume = resumeCreationService.getResumeById(id);

        if (resume == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resume not found with id:" + id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(resume);

    }

    @DeleteMapping("/delete{id}/")
    public ResponseEntity<Void> deleteResumeById (@Valid @PathVariable Long id){
        try {
            resumeCreationService.deleteResumeById(id);
            // Return HTTP 204 (No Content) upon successful deletion
            return ResponseEntity.noContent().build();
        } catch (ConfigDataResourceNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

