package com.freelanceplatform.backend.entity;

import com.freelanceplatform.backend.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "freelancer_skill")
@Data
@NoArgsConstructor
public class FreelancerSkill {

    // Composite primary key consisting of idFreelancer and idSkill
    @EmbeddedId
    private FreelancerSkillId id;

    @ManyToOne(fetch = FetchType.LAZY) // Many-to-one relationship to Freelancer
    @MapsId("idFreelancer")
    @JoinColumn(name = "id_freelancer")
    private Freelancer freelancer;

    @ManyToOne(fetch = FetchType.LAZY) // Many-to-one relationship to Skill
    @MapsId("idSkill")
    @JoinColumn(name = "id_skill")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SkillLevel level;
}
