package com.freelanceplatform.backend.dto.response;

import com.freelanceplatform.backend.enums.EvaluatorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long idReview;
    private String evaluatorName;
    private EvaluatorType evaluatorType;
    private Integer score;
    private String comment;
    private LocalDate reviewDate;
    private String projectTitle;
}
