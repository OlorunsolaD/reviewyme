package com.Sola.Review_Service.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {

    private String id;

    private String resumeId; //The id of the resume being reviewed
    // (obtained from Resume-creation-Service or Resume-Upload-Service)

    private String reviewerId; // Assigned reviewer
    private ReviewStatus Status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

}
