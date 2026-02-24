package com.freelanceplatform.backend.entity;

import com.freelanceplatform.backend.enums.EvaluatorType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private Long idReview;

    @ManyToOne(fetch = FetchType.LAZY) // Many reviews can be associated with one contract
    @JoinColumn(name = "id_contract", nullable = false)
    private Contract contract;

    @ManyToOne(fetch = FetchType.LAZY) // Many reviews can be associated with one evaluator (user)
    @JoinColumn(name = "id_evaluator", nullable = false)
    private User evaluator;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluator_type", nullable = false, length = 12)
    private EvaluatorType evaluatorType;

    @Column(nullable = false)
    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;
}
