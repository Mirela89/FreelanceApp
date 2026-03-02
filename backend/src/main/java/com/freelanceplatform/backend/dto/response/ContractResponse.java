package com.freelanceplatform.backend.dto.response;

import com.freelanceplatform.backend.enums.ContractStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse {
    private Long idContract;
    private String freelancerName;
    private String projectTitle;
    private String clientName;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractStatus status;
}
