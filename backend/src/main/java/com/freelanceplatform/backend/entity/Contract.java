package com.freelanceplatform.backend.entity;

import com.freelanceplatform.backend.enums.ContractStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "contract")
@Data
@NoArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contract")
    private Long idContract;

    @OneToOne // One contract is associated with one application
    @JoinColumn(name = "id_application", nullable = false, unique = true)
    private Application application;

    @ManyToOne(fetch = FetchType.LAZY) // Many contracts can be associated with one freelancer
    @JoinColumn(name = "id_freelancer", nullable = false)
    private Freelancer freelancer;

    @OneToOne // One contract is associated with one project
    @JoinColumn(name = "id_project", nullable = false, unique = true)
    private Project idProject;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ContractStatus status = ContractStatus.ACTIVE;
}