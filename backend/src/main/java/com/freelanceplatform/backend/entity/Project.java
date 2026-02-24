package com.freelanceplatform.backend.entity;

import com.freelanceplatform.backend.enums.PaymentType;
import com.freelanceplatform.backend.enums.ProjectStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_project")
    private Long idProject;

    @ManyToOne(fetch = FetchType.LAZY) //Many projects can belong to one client
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "budget_min", precision = 10, scale = 2)
    private BigDecimal budgetMin;

    @Column(name = "budget_max", precision = 10, scale = 2)
    private BigDecimal budgetMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false, length = 20)
    private PaymentType paymentType;

    @Column(name = "posting_date")
    private LocalDate postingDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ProjectStatus status = ProjectStatus.OPEN;

    // Relationships
    // A project can belong to multiple categories, and a category can have multiple projects
    @ManyToMany
    @JoinTable(
            name = "project_category",
            joinColumns = @JoinColumn(name = "id_project"),
            inverseJoinColumns = @JoinColumn(name = "id_category")
    )
    private Set<Category> categories = new HashSet<>();

    // A project can require multiple skills, and a skill can be required by multiple projects
    @ManyToMany
    @JoinTable(
            name = "project_skill",
            joinColumns = @JoinColumn(name = "id_project"),
            inverseJoinColumns = @JoinColumn(name = "id_skill")
    )
    private Set<Skill> skills = new HashSet<>();
}
