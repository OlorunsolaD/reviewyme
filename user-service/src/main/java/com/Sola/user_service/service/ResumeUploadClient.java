package com.Sola.user_service.service;


import com.Sola.resume_upload_service.model.ResumeUploadEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "resume-upload-service")
@RequestMapping("/api/v2/resumes")
public interface ResumeUploadClient {

    @PostMapping(value = "/uploadCV", consumes = "multipart/form-data")
    ResponseEntity<String>  uploadResume(@RequestParam("file") MultipartFile file);

    @PutMapping(value = "/update/{id}", consumes = "multipart/form-data")
    ResponseEntity<String> updateResume(@PathVariable("id") String id, @RequestParam("file") MultipartFile file);

    @GetMapping("/findFile/{id}")
    ResponseEntity<ResumeUploadEntity> getResumeById (@PathVariable("id") String id);

    @DeleteMapping("/deleteFile/{id}")
    ResponseEntity<ResumeUploadEntity> deleteResumeById(@PathVariable("id") String id);
}
