package com.freelanceplatform.backend.service;

import com.freelanceplatform.backend.dto.response.ContractResponse;
import com.freelanceplatform.backend.entity.Application;
import com.freelanceplatform.backend.entity.Contract;
import com.freelanceplatform.backend.enums.ApplicationStatus;
import com.freelanceplatform.backend.enums.ContractStatus;
import com.freelanceplatform.backend.enums.ProjectStatus;
import com.freelanceplatform.backend.exception.BusinessException;
import com.freelanceplatform.backend.exception.ResourceNotFoundException;
import com.freelanceplatform.backend.exception.UnauthorizedException;
import com.freelanceplatform.backend.mapper.ContractMapper;
import com.freelanceplatform.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final ApplicationRepository applicationRepository;
    private final ContractMapper contractMapper;

    // Create contract from accepted application
    @Transactional
    public ContractResponse createContract(Long applicationId, String email) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Application with id " + applicationId + " not found"
                ));

        if (!application.getProject().getClient().getEmail().equals(email)) {
            throw new UnauthorizedException("Not authorized to create this contract");
        }

        if (application.getStatus() != ApplicationStatus.ACCEPTED) {
            throw new BusinessException("Application must be accepted before creating a contract");
        }

        // Check if a contract already exists for this project
        contractRepository.findByProjectId(application.getProject().getIdProject())
                .ifPresent(c -> {
                    throw new BusinessException("A contract already exists for this project");
                });

        Contract saved = contractRepository.save(contractMapper.toEntity(application));
        return contractMapper.toResponse(saved);
    }

    // Get contract by project id (for client and freelancer)
    public ContractResponse getContractByProject(Long projectId, String email) {
        Contract contract = contractRepository.findByProjectId(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No contract found for project: " + projectId));

        boolean isClient = contract.getProject().getClient().getEmail().equals(email);
        boolean isFreelancer = contract.getFreelancer().getEmail().equals(email);

        if (!isClient && !isFreelancer) {
            throw new UnauthorizedException("Not authorized to view this contract");
        }

        return contractMapper.toResponse(contract);
    }

    // Get all contracts for freelancer
    public List<ContractResponse> getMyContracts(String email) {
        return contractRepository.findByFreelancerEmail(email)
                .stream()
                .map(contractMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Complete contract (only client or freelancer can complete, and only if active)
    @Transactional
    public ContractResponse completeContract(Long contractId, String email) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contract not found with id: " + contractId));

        boolean isClient = contract.getProject().getClient().getEmail().equals(email);
        boolean isFreelancer = contract.getFreelancer().getEmail().equals(email);

        if (!isClient && !isFreelancer) {
            throw new UnauthorizedException("Not authorized to complete this contract");
        }

        if (contract.getStatus() != ContractStatus.ACTIVE) {
            throw new BusinessException("Only active contracts can be completed");
        }

        contract.setStatus(ContractStatus.COMPLETED);
        contract.setEndDate(LocalDate.now());
        contract.getProject().setStatus(ProjectStatus.COMPLETED);

        return contractMapper.toResponse(contractRepository.save(contract));
    }

    // Cancel contract (only client can cancel, and only if active)
    @Transactional
    public ContractResponse cancelContract(Long contractId, String email) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contract not found with id: " + contractId));

        if (!contract.getProject().getClient().getEmail().equals(email)) {
            throw new UnauthorizedException("Only the client can cancel a contract");
        }

        if (contract.getStatus() != ContractStatus.ACTIVE) {
            throw new BusinessException("Only active contracts can be canceled");
        }

        contract.setStatus(ContractStatus.CANCELED);
        contract.setEndDate(LocalDate.now());
        contract.getProject().setStatus(ProjectStatus.CANCELED);

        return contractMapper.toResponse(contractRepository.save(contract));
    }
}
