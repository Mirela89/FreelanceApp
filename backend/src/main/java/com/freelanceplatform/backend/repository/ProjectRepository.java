package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Project;
import com.freelanceplatform.backend.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(ProjectStatus status);

    @Query("SELECT p FROM Project p WHERE p.client.idUser = :clientId")
    List<Project> findByClientId(@Param("clientId") Long clientId);
}
