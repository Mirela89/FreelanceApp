package com.freelanceplatform.backend.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewRequest {

    @NotNull
    @Min(1)
    @Max(5)
    private Integer score;

    private String comment;
}
