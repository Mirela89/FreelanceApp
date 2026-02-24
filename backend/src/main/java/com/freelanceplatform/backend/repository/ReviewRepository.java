package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findByContractIdContract(Long contractId);
}
