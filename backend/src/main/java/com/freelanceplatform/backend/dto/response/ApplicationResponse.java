package com.freelanceplatform.backend.dto.response;

import com.freelanceplatform.backend.enums.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {
    private Long idApplication;
    private String freelancerName;
    private String projectTitle;
    private String proposal;
    private BigDecimal hourlyRate;
    private Integer estimatedDuration;
    private ApplicationStatus status;
    private LocalDate applicationDate;
}
