package com.Sola.resume_creation_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SummaryDto {

    @NotBlank(message = "Summary text must not be blank")
    private String summaryText;
}
