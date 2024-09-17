package com.Sola.resume_creation_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificationDto {

    @NotBlank(message = "Certification name must not be blank")
    private String certificationName;
    private String issuingOrganization;
    private LocalDate issueDate;
    private LocalDate expirationDate;
}
