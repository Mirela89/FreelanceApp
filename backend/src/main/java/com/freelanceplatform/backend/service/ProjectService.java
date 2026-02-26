package com.freelanceplatform.backend.service;

import com.freelanceplatform.backend.dto.request.ProjectRequest;
import com.freelanceplatform.backend.dto.response.ProjectResponse;
import com.freelanceplatform.backend.entity.Client;
import com.freelanceplatform.backend.entity.Project;
import com.freelanceplatform.backend.enums.ProjectStatus;
import com.freelanceplatform.backend.mapper.ProjectMapper;
import com.freelanceplatform.backend.repository.ClientRepository;
import com.freelanceplatform.backend.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ClientRepository clientRepository;
    private final ProjectMapper projectMapper;

    // Create a new project and associate it with the client based on the email
    @Transactional
    public ProjectResponse createProject(ProjectRequest request, String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectMapper.toEntity(request, client);
        Project saved = projectRepository.save(project);
        return projectMapper.toResponse(saved);
    }

    // Retrieve all open projects
    public List<ProjectResponse> getAllOpenProjects() {
        return projectRepository.findByStatus(ProjectStatus.OPEN)
                .stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Retrieve a project by its ID
    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.toResponse(project);
    }

    // Retrieve all projects associated with a specific client
    public List<ProjectResponse> getProjectsByClient(String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        return projectRepository.findByClientId(client.getIdUser())
                .stream()
                .map(projectMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Delete a project by its ID
    @Transactional
    public void deleteProject(Long id, String email) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if(!project.getClient().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized to delete this project");
        }

        projectRepository.delete(project);
    }
}
