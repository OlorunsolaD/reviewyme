package com.Sola.resume_creation_service.config;

import com.Sola.resume_creation_service.model.Template;
import com.Sola.resume_creation_service.repository.TemplateRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TemplateDataInitializer implements CommandLineRunner {

    private final TemplateRepository templateRepository;


    public TemplateDataInitializer (TemplateRepository templateRepository){
        this.templateRepository = templateRepository;
    }

    public void run (String... args) throws Exception {
        if (templateRepository.count()==0) {
            Template template1 = new Template(null, "modern cv template", "/path/to/modern-cv-template",
                    "preview-image1");
            Template template2 = new Template(null, "standard-cv-template", "/path/to/professional-cv-template",
                    "preview-image2");

            templateRepository.save(template1);
            templateRepository.save(template2);

            System.out.println("Template Data initialized!");
        } else {
            System.out.println("Templates already exist, skipping initialization.");
        }
    }
}
