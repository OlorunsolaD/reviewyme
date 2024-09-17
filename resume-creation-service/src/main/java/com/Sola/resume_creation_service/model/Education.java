package com.Sola.resume_creation_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "education")
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String schoolName;
    private String schoolLocation;
    private String degree;
    private String fieldOfStudy;
    private LocalDate graduationMonthYear;


    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;

}
