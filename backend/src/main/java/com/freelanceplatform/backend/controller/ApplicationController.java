package com.freelanceplatform.backend.controller;

import com.freelanceplatform.backend.dto.request.ApplicationRequest;
import com.freelanceplatform.backend.dto.response.ApplicationResponse;
import com.freelanceplatform.backend.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    // Freelancer applies to a project
    @PostMapping("/projects/{projectId}/apply")
    public ResponseEntity<ApplicationResponse> apply(
            @PathVariable Long projectId,
            @RequestBody @Valid ApplicationRequest request,
            Authentication authentication) {
        ApplicationResponse response = applicationService.apply(
                projectId, request, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Client views applications for their project
    @GetMapping("/projects/{projectId}/applications")
    public ResponseEntity<List<ApplicationResponse>> getApplicationsByProject(
            @PathVariable Long projectId,
            Authentication authentication) {
        return ResponseEntity.ok(
                applicationService.getApplicationsByProject(projectId, authentication.getName()));
    }

    // Freelancer views their applications
    @GetMapping("/applications/my-applications")
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            Authentication authentication) {
        return ResponseEntity.ok(
                applicationService.getMyApplications(authentication.getName()));
    }

    // Client accepts an application
    @PatchMapping("/applications/{id}/accept")
    public ResponseEntity<ApplicationResponse> accept(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(
                applicationService.acceptApplication(id, authentication.getName()));
    }

    // Client rejects an application
    @PatchMapping("/applications/{id}/reject")
    public ResponseEntity<ApplicationResponse> reject(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(
                applicationService.rejectApplication(id, authentication.getName()));
    }
}
