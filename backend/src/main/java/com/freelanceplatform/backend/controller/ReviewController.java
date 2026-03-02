package com.freelanceplatform.backend.controller;

import com.freelanceplatform.backend.dto.request.ReviewRequest;
import com.freelanceplatform.backend.dto.response.ReviewResponse;
import com.freelanceplatform.backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts/{contractId}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Add a review for a contract, ensuring only the client or freelancer can review after completion, and update freelancer rating accordingly
    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(
            @PathVariable Long contractId,
            @RequestBody @Valid ReviewRequest request,
            Authentication authentication) {
        ReviewResponse response = reviewService.addReview(
                contractId, request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get all reviews for a contract, ensuring only the client or freelancer can view them
    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews(
            @PathVariable Long contractId,
            Authentication authentication) {
        return ResponseEntity.ok(
                reviewService.getReviewsByContract(contractId, authentication.getName()));
    }
}
