package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FreelancerRepository extends JpaRepository<Freelancer,Long> {
    List<Freelancer> findByAvailability(String availability);
    Optional<Freelancer> findByEmail(String email);
}
