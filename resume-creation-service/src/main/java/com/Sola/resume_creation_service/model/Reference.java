package com.Sola.resume_creation_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reference")
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String positionTitle;
    private String email;
    private String phoneNumber;
    private String companyName;
    private String relationshipToUser;


    @ManyToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;
}
