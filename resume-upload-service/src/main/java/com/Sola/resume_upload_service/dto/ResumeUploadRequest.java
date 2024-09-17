package com.Sola.resume_upload_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResumeUploadRequest {

    @NotNull(message = "File must not be Null")
    @Size(min = 1, message = "File must not be Empty")
    private MultipartFile file;
}
