package com.Sola.resume_creation_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {

    @NotBlank(message = "Job title must not be blank")
    private String jobTitle;
    private String companyName;
    private String country;
    private String state;
    private String city;

    @NotNull(message = "Start date must not be blank")
    private LocalDate startDate;
    private LocalDate startYear;

    @NotNull(message = "end date must not be blank")
    private LocalDate endDate;
    private LocalDate endYear;

    private List<String> jobResponsibilities;

    @Pattern(regexp = "^(http|https:)://.*$", message = "URL Format Invalid")
    private String linkedin;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL format Invalid")
    private String twitter;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL format Invalid")
    private String website;

    private boolean currentWork;
}
