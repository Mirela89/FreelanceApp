package com.freelanceplatform.backend.dto.request;

import com.freelanceplatform.backend.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class ProjectRequest {
    @NotBlank
    private String title;

    @NotNull
    private BigDecimal budgetMin;

    @NotNull
    private BigDecimal budgetMax;

    @NotNull
    private PaymentType paymentType;

    private List<Long> categoryIds;
    private List<Long> skillIds;
}
