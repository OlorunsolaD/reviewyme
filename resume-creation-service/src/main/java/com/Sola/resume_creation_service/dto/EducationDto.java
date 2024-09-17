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
public class EducationDto {

    @NotBlank(message = "School name must not be blank")
    private String schoolName;

    private String schoolLocation;

    @NotBlank(message = "Degree must not be blank")
    private String degree;

    @NotBlank(message = "Field of study must not be blank")
    private String fieldOfStudy;

    private LocalDate graduationMonthYear;
}
