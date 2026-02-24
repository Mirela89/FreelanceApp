package com.freelanceplatform.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "freelancer")
@PrimaryKeyJoinColumn(name = "id_user") // This column will be the primary key
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Freelancer extends User {

    @Column(name = "profile_title", nullable = false, length = 150)
    private String profileTitle;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(length = 20)
    private String availability;

    @Column(precision = 3, scale = 1)
    private BigDecimal rating;
}
