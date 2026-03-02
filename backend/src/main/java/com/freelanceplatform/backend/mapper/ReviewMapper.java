package com.freelanceplatform.backend.mapper;

import com.freelanceplatform.backend.dto.request.ReviewRequest;
import com.freelanceplatform.backend.dto.response.ReviewResponse;
import com.freelanceplatform.backend.entity.Contract;
import com.freelanceplatform.backend.entity.Review;
import com.freelanceplatform.backend.entity.User;
import com.freelanceplatform.backend.enums.EvaluatorType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "evaluatorName", source = "evaluator.name")
    @Mapping(target = "projectTitle", source = "contract.project.title")
    ReviewResponse toResponse(Review review);

    @Mapping(target = "idReview", ignore = true)
    @Mapping(target = "contract", source = "contract")
    @Mapping(target = "evaluator", source = "evaluator")
    @Mapping(target = "evaluatorType", source = "evaluatorType")
    @Mapping(target = "reviewDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "score", source = "request.score")
    @Mapping(target = "comment", source = "request.comment")
    Review toEntity(ReviewRequest request, Contract contract, User evaluator, EvaluatorType evaluatorType);
}
