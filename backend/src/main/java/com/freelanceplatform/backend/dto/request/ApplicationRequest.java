package com.freelanceplatform.backend.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class ApplicationRequest {

    @NotBlank
    private String proposal;

    private BigDecimal hourlyRate;

    @NotNull
    @Min(1)
    private Integer estimatedDuration;
}
