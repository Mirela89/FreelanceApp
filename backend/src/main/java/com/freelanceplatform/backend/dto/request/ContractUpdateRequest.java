package com.freelanceplatform.backend.dto.request;

import com.freelanceplatform.backend.enums.ContractStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ContractUpdateRequest {

    private LocalDate endDate;
    //private ContractStatus status;
}
