package com.freelanceplatform.backend.service;

import com.freelanceplatform.backend.dto.request.ReviewRequest;
import com.freelanceplatform.backend.dto.response.ReviewResponse;
import com.freelanceplatform.backend.entity.Contract;
import com.freelanceplatform.backend.entity.Freelancer;
import com.freelanceplatform.backend.entity.Review;
import com.freelanceplatform.backend.entity.User;
import com.freelanceplatform.backend.enums.ContractStatus;
import com.freelanceplatform.backend.enums.EvaluatorType;
import com.freelanceplatform.backend.exception.BusinessException;
import com.freelanceplatform.backend.exception.ResourceNotFoundException;
import com.freelanceplatform.backend.exception.UnauthorizedException;
import com.freelanceplatform.backend.mapper.ReviewMapper;
import com.freelanceplatform.backend.repository.ContractRepository;
import com.freelanceplatform.backend.repository.ReviewRepository;
import com.freelanceplatform.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    // Add a review for a contract, ensuring only the client or freelancer can review after completion, and update freelancer rating accordingly
    @Transactional
    public ReviewResponse addReview(Long contractId, ReviewRequest request, String email) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contract not found with id: " + contractId));

        if (contract.getStatus() != ContractStatus.COMPLETED) {
            throw new BusinessException("Reviews can only be added after contract completion");
        }

        User evaluator = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isClient = contract.getProject().getClient().getEmail().equals(email);
        boolean isFreelancer = contract.getFreelancer().getEmail().equals(email);

        if (!isClient && !isFreelancer) {
            throw new UnauthorizedException("Not authorized to review this contract");
        }

        EvaluatorType evaluatorType = isClient ? EvaluatorType.CLIENT : EvaluatorType.FREELANCER;

        // Check if the user has already reviewed this contract
        boolean alreadyReviewed = reviewRepository.findByContractIdContract(contractId)
                .stream()
                .anyMatch(r -> r.getEvaluatorType() == evaluatorType);

        if (alreadyReviewed) {
            throw new BusinessException("You have already reviewed this contract");
        }

        Review review = reviewMapper.toEntity(request, contract, evaluator, evaluatorType);
        Review saved = reviewRepository.save(review);

        // Update freelancer rating if the client is reviewing
        if (isClient) {
            updateFreelancerRating(contract.getFreelancer());
        }

        return reviewMapper.toResponse(saved);
    }

    // Get all reviews for a contract, ensuring only the client or freelancer can view them
    public List<ReviewResponse> getReviewsByContract(Long contractId, String email) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contract not found with id: " + contractId));

        boolean isClient = contract.getProject().getClient().getEmail().equals(email);
        boolean isFreelancer = contract.getFreelancer().getEmail().equals(email);

        if (!isClient && !isFreelancer) {
            throw new UnauthorizedException("Not authorized to view these reviews");
        }

        return reviewRepository.findByContractIdContract(contractId)
                .stream()
                .map(reviewMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Helper method to update freelancer's average rating based on client reviews
    private void updateFreelancerRating(Freelancer freelancer) {
        List<Review> reviews = reviewRepository
                .findClientReviewsByFreelancer(freelancer.getIdUser());

        if (!reviews.isEmpty()) {
            double average = reviews.stream()
                    .mapToInt(Review::getScore)
                    .average()
                    .orElse(0.0);
            freelancer.setRating(BigDecimal.valueOf(average)
                    .setScale(1, RoundingMode.HALF_UP));
        }
    }
}
