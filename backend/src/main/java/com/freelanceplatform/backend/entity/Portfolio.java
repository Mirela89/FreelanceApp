package com.freelanceplatform.backend.entity;

import com.freelanceplatform.backend.enums.PortfolioType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "portfolio")
@Data
@NoArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_portfolio")
    private Long idPortfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_freelancer", nullable = false)
    private Freelancer freelancer;

    @Column(nullable = false, length = 255)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PortfolioType type;
}
