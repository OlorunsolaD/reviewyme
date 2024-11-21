package com.Sola.resume_creation_service.service.impl;
import com.Sola.resume_creation_service.dto.*;
import com.Sola.resume_creation_service.exception.ResumeNotFoundException;
import com.Sola.resume_creation_service.model.*;
import com.Sola.resume_creation_service.repository.*;
import com.Sola.resume_creation_service.service.ResumeCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.Sola.resume_creation_service.model.ResumeStatus.PENDING_REVIEW;


@Service
@Transactional
public class ResumeCreationServiceImpl implements ResumeCreationService {


    private final ResumeCreationRepository resumeCreationRepository;


    @Autowired
    public ResumeCreationServiceImpl(ResumeCreationRepository resumeCreationRepository) {

        this.resumeCreationRepository = resumeCreationRepository;

    }

    @Override
    public Resume createResume(ResumeCreationRequest resumeCreationRequest) {
        // Build the Resume object using the Builder pattern
        Resume.ResumeBuilder resumeBuilder = Resume.builder()
                .resumeStatus(PENDING_REVIEW); // Spring Security isAdmin configuration required! //

        // Map Contact information using the Builder
        if (resumeCreationRequest.getContact() != null) {
            Contact contact = mapContact(resumeCreationRequest.getContact());
            resumeBuilder.contact(contact);
        }

        if (resumeCreationRequest.getEducationList() != null && !resumeCreationRequest.getEducationList()
                .isEmpty()) {
            List<Education> educationList = resumeCreationRequest.getEducationList().stream()
                    .map(this::mapEducation)  // Convert each Education DTO to an Education entity
                    .collect(Collectors.toList());
            resumeBuilder.educationList(educationList);  // Associate education list with the resume
        }

        if (resumeCreationRequest.getExperienceList() != null && !resumeCreationRequest.getExperienceList()
                .isEmpty()) {
            List<Experience> experienceList = resumeCreationRequest.getExperienceList().stream()
                    .map(this::mapExperience)
                    .collect(Collectors.toList());
            resumeBuilder.experienceList(experienceList);
        }

        if (resumeCreationRequest.getSkillsList() != null && !resumeCreationRequest.getSkillsList().isEmpty()) {
            List<Skills> skillsList = resumeCreationRequest.getSkillsList().stream()
                    .map(this::mapSkills)
                    .collect(Collectors.toList());
            resumeBuilder.skillsList(skillsList);
        }

        if (resumeCreationRequest.getCertificationList() != null && !resumeCreationRequest.getCertificationList().isEmpty()) {
            List<Certification> certificationList = resumeCreationRequest.getCertificationList().stream()
                    .map(this::mapCertification)
                    .collect(Collectors.toList());
            resumeBuilder.certificationList(certificationList);
        }
        if (resumeCreationRequest.getReferenceList() != null && !resumeCreationRequest.getReferenceList().isEmpty()) {
            List<Reference> referenceList = resumeCreationRequest.getReferenceList().stream()
                    .map(this::mapReference)
                    .collect(Collectors.toList());
            resumeBuilder.referenceList(referenceList);
        }

        if (resumeCreationRequest.getSummary() != null) {
            Summary summary = mapSummary(resumeCreationRequest.getSummary());
            resumeBuilder.summary(summary);  // Associate summary with the resume
        }

        Resume resume = resumeBuilder.build();
        return resumeCreationRepository.save(resume);

    }


    @Override
    public Resume updateResume(Long id, ResumeCreationRequest resumeCreationRequest) {
        Resume resume = resumeCreationRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found with ID:" + id));

        if (resumeCreationRequest.getContact() != null) {
            Contact contact = mapContact(resumeCreationRequest.getContact());
            resume.setContact(contact);
        }

        if (resumeCreationRequest.getEducationList() != null) {
            List<Education> educationList = resumeCreationRequest.getEducationList().stream()
                    .map(this::mapEducation)
                    .collect(Collectors.toList());
            resume.setEducationList(educationList);
        }

        if (resumeCreationRequest.getExperienceList() != null) {
            List<Experience> experienceList = resumeCreationRequest.getExperienceList()
                    .stream()
                    .map(this::mapExperience)
                    .collect(Collectors.toList());
            resume.setExperienceList(experienceList);
        }

        if (resumeCreationRequest.getCertificationList() != null){
            List<Certification> certificationList = resumeCreationRequest.getCertificationList()
                    .stream()
                    .map(this::mapCertification)
                    .collect(Collectors.toList());
            resume.setCertificationList(certificationList);
        }

        if (resumeCreationRequest.getSkillsList() != null){
            List<Skills> skillsList = resumeCreationRequest.getSkillsList()
                    .stream()
                    .map(this::mapSkills)
                    .collect(Collectors.toList());
            resume.setSkillsList(skillsList);
        }

        if (resumeCreationRequest.getReferenceList() != null){
            List<Reference> referenceList = resumeCreationRequest.getReferenceList()
                    .stream()
                    .map(this::mapReference)
                    .collect(Collectors.toList());
            resume.setReferenceList(referenceList);
        }

        if (resumeCreationRequest.getSummary() != null) {
            Summary summary = mapSummary(resumeCreationRequest.getSummary());
            resume.setSummary(summary);
        }

        return resumeCreationRepository.save(resume);
    }


    private Reference mapReference(ReferenceDto referenceDto) {
        return Reference.builder()
                .firstName(referenceDto.getFirstName())
                .lastName(referenceDto.getLastName())
                .positionTitle(referenceDto.getPositionTitle())
                .email(referenceDto.getEmail())
                .phoneNumber(referenceDto.getPhoneNumber())
                .companyName(referenceDto.getCompanyName())
                .relationshipToUser(referenceDto.getRelationshipToUser())
                .build();
    }

    private Summary mapSummary (SummaryDto summaryDto){
        return Summary.builder()
                .summaryText(summaryDto.getSummaryText())
                .build();

    }


    private Contact mapContact(ContactDto contactDto) {
        return Contact.builder()
                .firstName(contactDto.getFirstName())
                .lastName(contactDto.getLastName())
                .email(contactDto.getEmail())
                .phoneNumber(contactDto.getPhoneNumber())
                .country(contactDto.getCountry())
                .city(contactDto.getCity())
                .state(contactDto.getState())
                .postCode(contactDto.getPostCode())
                .dateOfBirth(contactDto.getDateOfBirth())
                .driverLicense(contactDto.getDriverLicense())
                .nationality(contactDto.getNationality())
                .build();
    }

    private Certification mapCertification(CertificationDto certificationDto) {
        return Certification.builder()
                .certificationName(certificationDto.getCertificationName())
                .issuingOrganization(certificationDto.getIssuingOrganization())
                .issueDate(certificationDto.getIssueDate())
                .build();
    }

    private Skills mapSkills(SkillsDto skillsDto) {
        return Skills.builder()
                .skillName(skillsDto.getSkillName())
                .build();
    }

    private Experience mapExperience(ExperienceDto experienceDto) {
        return Experience.builder()
                .jobTitle(experienceDto.getJobTitle())
                .companyName(experienceDto.getCompanyName())
                .country(experienceDto.getCountry())
                .state(experienceDto.getState())
                .city(experienceDto.getCity())
                .startDate(experienceDto.getStartDate())
                .endDate(experienceDto.getEndDate())
                .jobResponsibilities(experienceDto.getJobResponsibilities())
                .currentWork(experienceDto.isCurrentWork())
                .build();
    }

    private Education mapEducation(EducationDto educationDto) {
        return Education.builder()
                .schoolName(educationDto.getSchoolName())
                .schoolLocation(educationDto.getSchoolLocation())
                .degree(educationDto.getDegree())
                .fieldOfStudy(educationDto.getFieldOfStudy())
                .graduationMonthYear(educationDto.getGraduationMonthYear())
                .build();
    }

    @Override
    public Resume getResumeById(Long id) {
        return resumeCreationRepository.findById(id)
                .orElseThrow(() ->new ResumeNotFoundException(" Resume not found with id:" + id));
    }

    @Override
    public List<Resume> getAllResumes() {
        return resumeCreationRepository.findAll();
    }

    @Override
    public void deleteResumeById(Long id) {
        Resume resume = resumeCreationRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException(" Resume not found with id:" + id));
        resumeCreationRepository.delete(resume);

    }

    @Override
    public void markResumeCreatedUnderReview(Long id) {
        Resume resume = resumeCreationRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found with ID: " + id));
        if (resume.getResumeStatus() != PENDING_REVIEW) {
            throw new IllegalStateException("Only resumes in PENDING_REVIEW status can be marked as UNDER_REVIEW.");

        }
        resume.setResumeStatus(ResumeStatus.UNDER_REVIEW);
        resume.setUpdatedAt(new Date());
        resumeCreationRepository.save(resume);
    }

    @Override
    public void markResumeCreatedCompleted(Long id) {
        Resume resume = resumeCreationRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException("Resume not found with ID: " + id));
        if (resume.getResumeStatus() != ResumeStatus.UNDER_REVIEW){

        }
        resume.setResumeStatus(ResumeStatus.COMPLETED);
        resume.setUpdatedAt(new Date());
        resumeCreationRepository.save(resume);
    }
}