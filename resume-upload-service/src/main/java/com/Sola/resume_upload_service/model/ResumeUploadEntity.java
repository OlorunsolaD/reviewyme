package com.Sola.resume_upload_service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "resumes")
public class ResumeUploadEntity {

    @Id
    private String id;
    private String fileName;
    private String filePart;
    private Long fileSize;
    private byte[] data; // Consider storing in GridFS or external storage for large files
    private String userId;

}
