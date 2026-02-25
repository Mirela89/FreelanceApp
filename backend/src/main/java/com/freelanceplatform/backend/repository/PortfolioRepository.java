package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Client;
import com.freelanceplatform.backend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Client,Long> {
    @Query("SELECT p FROM Portfolio p WHERE p.freelancer.idUser = :freelancerId")
    List<Portfolio> findByFreelancerId(@Param("freelancerId") Long freelancerId);
}
