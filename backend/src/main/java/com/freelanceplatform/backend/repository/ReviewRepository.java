package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT r FROM Review r WHERE r.contract.idContract = :contractId")
    List<Review> findByContractIdContract(@Param("contractId") Long contractId);

    @Query("SELECT r FROM Review r WHERE r.contract.freelancer.idUser = :freelancerId AND r.evaluatorType = 'CLIENT'")
    List<Review> findClientReviewsByFreelancer(@Param("freelancerId") Long freelancerId);
}
