package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Client;
import com.freelanceplatform.backend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Client,Long> {
    List<Portfolio> findByFreelancerIdUser(Long freelancerId);
}
