package com.Sola.resume_creation_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeCreationRequest {

    @NotNull(message = "Template ID must not be null")
    private Long templateId;

    @Valid
    private ContactDto contact;

    @Valid
    private List<EducationDto> educationList;

    @Valid
    private List<ExperienceDto> experienceList;

    @Valid
    private List<CertificationDto> certificationList;

    @Valid
    private List<SkillsDto> skillsList;

    @Valid
    private SummaryDto summary;

    @Valid
    private List<ReferenceDto> referenceList;

//    private String userId;
}
