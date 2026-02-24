package com.freelanceplatform.backend.repository;

import com.freelanceplatform.backend.entity.Project;
import com.freelanceplatform.backend.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByClientIdUser(Long clientId);
}
