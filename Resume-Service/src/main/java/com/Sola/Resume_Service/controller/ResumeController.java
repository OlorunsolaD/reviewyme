package com.Sola.Resume_Service.controller;

import com.Sola.Resume_Service.dto.ResumeRequest;
import com.Sola.Resume_Service.model.ResumeEntity;
import com.Sola.Resume_Service.service.ResumeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/resume")
@Tag(name = "Resume Controller", description = "Controller that manages and upload resumes")
public class ResumeController {


    @Autowired
    private ResumeServiceImpl resumeService; // Inject the interface, not the implementation

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
            ResumeRequest resumeRequest = new ResumeRequest(); // Assume ResumeRequest includes a MultipartFile
            resumeRequest.setFile(file); // Set the file in the DTO
            resumeService.saveResume(resumeRequest); // Use DTO directly
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
            ResumeRequest resumeRequest = new ResumeRequest(file);
            resumeService.updateResume(id, resumeRequest);
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
    public ResponseEntity<ResumeEntity> getResumeById (@PathVariable String id){
        Optional<ResumeEntity> resume = resumeService.getResumeById(id);
        return resume.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());

    }

    @DeleteMapping("/deleteResume/{id}")
    public ResponseEntity<ResumeEntity> deleteResumeById(@PathVariable String id) {
        Optional<ResumeEntity> resumeFile = resumeService.deleteResumeById(id);
        return resumeFile.map(resumeEntity -> ResponseEntity.ok(resumeEntity))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }



}
