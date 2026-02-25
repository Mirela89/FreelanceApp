package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    @Query("SELECT a FROM Application a WHERE a.project.idProject = :projectId")
    List<Application> findByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT a FROM Application a WHERE a.freelancer.idUser = :freelancerId")
    List<Application> findByFreelancerId(@Param("freelancerId") Long freelancerId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Application a WHERE a.freelancer.idUser = :freelancerId AND a.project.idProject = :projectId")
    boolean existsByFreelancerIdAndProjectId(@Param("freelancerId") Long freelancerId, @Param("projectId") Long projectId);
}
