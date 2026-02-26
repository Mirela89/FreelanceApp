package com.freelanceplatform.backend.controller;

import com.freelanceplatform.backend.dto.request.ProjectRequest;
import com.freelanceplatform.backend.dto.response.ProjectResponse;
import com.freelanceplatform.backend.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    // Endpoint to retrieve all open projects
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllOpenProjects());
    }

    // Endpoint to retrieve a project by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // Endpoint to retrieve all projects associated with the authenticated client
    @GetMapping("/my-projects")
    public ResponseEntity<List<ProjectResponse>> getMyProjects(Authentication authentication) {
        return ResponseEntity.ok(projectService.getProjectsByClient(authentication.getName()));
    }

    // Endpoint to create a new project associated with the authenticated client
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @RequestBody @Valid ProjectRequest request,
            Authentication authentication) {
        ProjectResponse response = projectService.createProject(request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable Long id,
            Authentication authentication) {
        projectService.deleteProject(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
