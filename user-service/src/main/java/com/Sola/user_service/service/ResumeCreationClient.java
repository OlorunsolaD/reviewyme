package com.Sola.user_service.service;


import com.Sola.resume_creation_service.dto.ResumeCreationRequest;
import com.Sola.resume_creation_service.model.Resume;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "resume-creation-service")
@RequestMapping("/api/v1/resumes")
public interface ResumeCreationClient {

    @PostMapping("/create")
    Resume createResume(@RequestBody ResumeCreationRequest resumeCreationRequest);

    @PutMapping("/{id}/update")
    Resume updateResume(@PathVariable("id") Long id);

    @GetMapping("/find/{id}")
    Resume getResumeById (@PathVariable("id") Long id);

    @GetMapping("/all")
    List<Resume> getAllResumes();

    @DeleteMapping("/delete{id}/")
    void deleteResumeById (@PathVariable("id") Long id);
}


//This Feign client will help ensure that user-service can
//communicate with resume-creation-service seamlessly for all CRUD operations on Resume.