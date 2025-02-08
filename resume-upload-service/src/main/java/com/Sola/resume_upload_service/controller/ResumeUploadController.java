package com.Sola.resume_upload_service.controller;

import com.Sola.resume_upload_service.dto.ResumeUploadRequest;
import com.Sola.resume_upload_service.model.ResumeUploadEntity;
import com.Sola.resume_upload_service.service.ResumeUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v2/resumes")
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
    public ResponseEntity<ResumeUploadEntity> uploadResume(@ModelAttribute ResumeUploadRequest resumeUploadRequest) {
        ResumeUploadEntity uploadedRequest = resumeUploadService.uploadResume(resumeUploadRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(uploadedRequest);


    }

    @PutMapping("/updateCV/{id}")
    @Operation(summary = "Update Resume", description = "Updates an existing resume by uploading new file.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resume updated successfully", content = @Content),
                    @ApiResponse(responseCode = "400", description = "No file provided", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Server error while processing the file", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Resume not found with provided ID", content = @Content)
            })
    public ResponseEntity<ResumeUploadEntity> updateResume(@PathVariable String id,
                                                           @RequestParam("filePart") MultipartFile file,
                                                           @RequestParam("userId") String userId) {

        ResumeUploadRequest request = new ResumeUploadRequest(file, userId);
        ResumeUploadEntity updatedResumeUpload = resumeUploadService.updateResume(id, request);
        return ResponseEntity.ok(updatedResumeUpload);


    }
    @GetMapping("/findCV/{id}")
    public ResponseEntity<ResumeUploadEntity> getResumeById (@PathVariable String id){
        ResumeUploadEntity resumeUpload = resumeUploadService.getResumeById(id);
        return ResponseEntity.ok(resumeUpload);
    }

    @GetMapping("/userCV/{userId}")
    public ResponseEntity<List<ResumeUploadEntity>> getAllResumesByUserId (@PathVariable String userId){
        List<ResumeUploadEntity> resumesByUserId = resumeUploadService.GetAllResumesByUserId(userId);
        return ResponseEntity.ok(resumesByUserId);
    }

    @DeleteMapping("/deleteCV/{id}")
    public ResponseEntity<String> deleteResumeById(@PathVariable String id) {
        resumeUploadService.deleteResumeById(id);
        return ResponseEntity.ok("Resume deleted Successfully");
    }



}
