package com.freelanceplatform.backend.controller;

import com.freelanceplatform.backend.dto.response.ContractResponse;
import com.freelanceplatform.backend.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    // Create contract from accepted application
    @PostMapping("/create-contract/{applicationId}")
    public ResponseEntity<ContractResponse> createContract(
            @PathVariable Long applicationId,
            Authentication authentication) {
        ContractResponse response = contractService.createContract(applicationId, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get contract by project id (for client and freelancer)
    @GetMapping("/project/{projectId}")
    public ResponseEntity<ContractResponse> getContractByProject(
            @PathVariable Long projectId,
            Authentication authentication) {
        return ResponseEntity.ok(
                contractService.getContractByProject(projectId, authentication.getName()));
    }

    // Get my contracts
    @GetMapping("/my-contracts")
    public ResponseEntity<List<ContractResponse>> getMyContracts(
            Authentication authentication) {
        return ResponseEntity.ok(
                contractService.getMyContracts(authentication.getName()));
    }

    // Complete contract (mark as completed)
    @PatchMapping("/{id}/complete")
    public ResponseEntity<ContractResponse> completeContract(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(
                contractService.completeContract(id, authentication.getName()));
    }

    // Cancel contract (mark as canceled)
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ContractResponse> cancelContract(
            @PathVariable Long id,
            Authentication authentication) {
        return ResponseEntity.ok(
                contractService.cancelContract(id, authentication.getName()));
    }
}
