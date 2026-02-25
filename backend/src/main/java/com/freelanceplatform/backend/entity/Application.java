package com.freelanceplatform.backend.entity;

import com.freelanceplatform.backend.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "application")
@Data
@NoArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_application")
    private Long idApplication;

    @ManyToOne(fetch = FetchType.LAZY) //Many applications can be made by one freelancer
    @JoinColumn(name = "id_freelancer", nullable = false)
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String proposal;

    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "estimated_duration", nullable = false)
    private Integer estimatedDuration;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ApplicationStatus status;

    @Column(name = "application_date")
    private LocalDate applicationDate;
}
