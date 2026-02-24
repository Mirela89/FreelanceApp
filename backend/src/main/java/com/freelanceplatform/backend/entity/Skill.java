package com.freelanceplatform.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "skill")
@Data
@NoArgsConstructor
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_skill")
    private Long idSkill;

    @Column(name = "skill_name", nullable = false, unique = true, length = 100)
    private String skillName;

    @ManyToOne(fetch = FetchType.LAZY) // Many skills can have one parent skill
    @JoinColumn(name = "id_parent") // Foreign key to the parent skill
    private Skill parent;
}
