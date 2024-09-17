package com.Sola.resume_creation_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "templates")
@Entity
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // Unique ID for each template

    private String templateName;  //  The name of the template (e.g., "Professional CV", "Modern CV")
//    private String description;  // A short description of the template
    private String templateFilePath; // The path to the template file (e.g., DOCX, PDF, or HTML file)
    private String previewImage; // URL to a preview image (to display on the frontend)

}
