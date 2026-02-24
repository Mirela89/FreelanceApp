package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findByProjectIdProject(Long projectId);
    List<Application> findByFreelancerIdUser(Long freelancerId);
    boolean existsByFreelancerIdUserAndProjectIdProject(Long freelancerId, Long projectId);
}
