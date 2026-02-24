package com.freelanceplatform.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreelancerSkillId implements Serializable {

    @Column(name = "id_freelancer")
    private Long idFreelancer;

    @Column(name = "id_skill")
    private Long idSkill;
}
