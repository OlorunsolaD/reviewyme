package com.Sola.resume_upload_service.controller;

import com.Sola.resume_upload_service.dto.ResumeUploadRequest;
import com.Sola.resume_upload_service.model.ResumeUploadEntity;
import com.Sola.resume_upload_service.service.ResumeUploadService;
import com.Sola.resume_upload_service.service.ResumeUploadServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/resume")
@Tag(name = "Resume Controller", description = "Controller that manages and upload resumes")
public class ResumeUploadController {


    @Autowired
    private final ResumeUploadService resumeUploadService; // Inject the interface, not the implementation

    public ResumeUploadController(ResumeUploadService resumeUploadService){
        this.resumeUploadService = resumeUploadService;
    }



    @PostMapping("/uploadCV")
    @Operation(summary = "uploadResume", description = "Upload resume docs or PDF files to database",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resume Uploaded Successfully",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "400", description = "No file provided",
                            content = @Content(mediaType = "text/plain")),
                    @ApiResponse(responseCode = "500", description = "Failed to Upload CV or process file",
                            content = @Content(mediaType = "text/plain"))
            })
    public ResponseEntity<String> uploadResume(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file provided");
            }
            ResumeUploadRequest resumeUploadRequest = new ResumeUploadRequest(); // Assume ResumeUploadRequest includes a MultipartFile
            resumeUploadRequest.setFile(file); // Set the file in the DTO
            resumeUploadService.saveResume(resumeUploadRequest); // Use DTO directly
            return ResponseEntity.ok("Resume Uploaded Successfully");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to Upload CV: " + e.getMessage());
        }

    }

    @PostMapping("/update/{id}")
    @Operation(summary = "Update Resume", description = "Updates an existing resume by uploading new file.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resume updated successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "No file provided", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error while processing the file", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resume not found with provided ID", content = @Content)
            })
    public ResponseEntity<String> updateResume(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No file provided");
        }

        try {
            ResumeUploadRequest resumeUploadRequest = new ResumeUploadRequest(file);
            resumeUploadService.updateResume(id, resumeUploadRequest);
            return ResponseEntity.ok("Resume updated successfully");
        } catch (IllegalStateException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating resume: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    @GetMapping("/findResume/{id}")
    public ResponseEntity<ResumeUploadEntity> getResumeById (@PathVariable String id){
        Optional<ResumeUploadEntity> resume = resumeUploadService.getResumeById(id);
        return resume.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());

    }

    @DeleteMapping("/deleteResume/{id}")
    public ResponseEntity<ResumeUploadEntity> deleteResumeById(@PathVariable String id) {
        Optional<ResumeUploadEntity> resumeFile = resumeUploadService.deleteResumeById(id);
        return resumeFile.map(resumeUploadEntity -> ResponseEntity.ok(resumeUploadEntity))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }



}
