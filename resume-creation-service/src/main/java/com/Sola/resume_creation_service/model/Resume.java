package com.Sola.resume_creation_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private String resumeName;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL)
    private Contact contact;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Experience> experienceList;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Education> educationList;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Certification> certificationList;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Skills> skillsList;

    @OneToOne(mappedBy = "resume", cascade = CascadeType.ALL)
    private Summary summary;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
    private List<Reference> referenceList;

    private ResumeStatus resumeStatus;// Status of the resume process (PENDING_REVIEW, UNDER_REVIEW, COMPLETED, etc.)

    private Set<Roles> roles; // Admin and Resume roles
    private java.util.Date createdAt;

    private java.util.Date updatedAt;
}
