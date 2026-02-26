package com.freelanceplatform.backend.dto.response;

import com.freelanceplatform.backend.enums.PaymentType;
import com.freelanceplatform.backend.enums.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private Long idProject;
    private String title;
    private BigDecimal budgetMin;
    private BigDecimal budgetMax;
    private PaymentType paymentType;
    private ProjectStatus status;
    private LocalDate postingDate;
    private String clientName;
}
