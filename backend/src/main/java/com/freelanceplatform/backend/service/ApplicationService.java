package com.freelanceplatform.backend.service;

import com.freelanceplatform.backend.dto.request.ApplicationRequest;
import com.freelanceplatform.backend.dto.response.ApplicationResponse;
import com.freelanceplatform.backend.entity.Application;
import com.freelanceplatform.backend.entity.Freelancer;
import com.freelanceplatform.backend.entity.Project;
import com.freelanceplatform.backend.enums.ApplicationStatus;
import com.freelanceplatform.backend.enums.ProjectStatus;
import com.freelanceplatform.backend.exception.BusinessException;
import com.freelanceplatform.backend.exception.ResourceNotFoundException;
import com.freelanceplatform.backend.exception.UnauthorizedException;
import com.freelanceplatform.backend.mapper.ApplicationMapper;
import com.freelanceplatform.backend.repository.ApplicationRepository;
import com.freelanceplatform.backend.repository.FreelancerRepository;
import com.freelanceplatform.backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final FreelancerRepository freelancerRepository;
    private final ProjectRepository projectRepository;
    private final ApplicationMapper applicationMapper;

    // Freelancer applies to a project
    @Transactional
    public ApplicationResponse apply(Long projectId, ApplicationRequest request, String email) {
        Freelancer freelancer = freelancerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        if (project.getStatus() != ProjectStatus.OPEN) {
            throw new BusinessException("Project is not open for applications");
        }

        if (applicationRepository.existsByFreelancerIdAndProjectId(
                freelancer.getIdUser(), projectId)) {
            throw new BusinessException("You have already applied to this project");
        }

        Application application = applicationMapper.toEntity(request, freelancer, project);
        Application saved = applicationRepository.save(application);
        return applicationMapper.toResponse(saved);
    }

    // Client views applications for their project
    public List<ApplicationResponse> getApplicationsByProject(Long projectId, String email) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + projectId));

        if (!project.getClient().getEmail().equals(email)) {
            throw new UnauthorizedException("Not authorized to view these applications");
        }

        return applicationRepository.findByProjectId(projectId)
                .stream()
                .map(applicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Freelancer views their applications
    public List<ApplicationResponse> getMyApplications(String email) {
        Freelancer freelancer = freelancerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Freelancer not found"));

        return applicationRepository.findByFreelancerId(freelancer.getIdUser())
                .stream()
                .map(applicationMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Client accepts an application
    @Transactional
    public ApplicationResponse acceptApplication(Long applicationId, String email) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        if (!application.getProject().getClient().getEmail().equals(email)) {
            throw new UnauthorizedException("Not authorized to accept this application");
        }

        if (application.getProject().getStatus() != ProjectStatus.OPEN) {
            throw new BusinessException("Project is no longer open");
        }

        application.setStatus(ApplicationStatus.ACCEPTED);
        application.getProject().setStatus(ProjectStatus.IN_PROGRESS);

        return applicationMapper.toResponse(applicationRepository.save(application));
    }

    // Client rejects an application
    @Transactional
    public ApplicationResponse rejectApplication(Long applicationId, String email) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        if (!application.getProject().getClient().getEmail().equals(email)) {
            throw new UnauthorizedException("Not authorized to reject this application");
        }

        application.setStatus(ApplicationStatus.REJECTED);
        return applicationMapper.toResponse(applicationRepository.save(application));
    }
}
