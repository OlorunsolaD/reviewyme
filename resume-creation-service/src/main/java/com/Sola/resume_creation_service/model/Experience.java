package com.Sola.resume_creation_service.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "experience")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle;
    private String companyName;
    private String country;
    private String state;
    private String city;
    private LocalDate startDate;
    private LocalDate startYear;
    private LocalDate endDate;
    private LocalDate endYear;

    @ElementCollection
    private List<String> jobResponsibilities;


    @Pattern(regexp = "^(http|https:)://.*$", message = "URL Format Invalid")
    private String linkedin;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL format Invalid")
    private String twitter;

    @Pattern(regexp = "^(http|https)://.*$", message = "URL format Invalid")
    private String website;

    private boolean currentWork;

    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
