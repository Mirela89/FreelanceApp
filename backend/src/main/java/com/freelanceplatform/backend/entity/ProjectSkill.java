package com.freelanceplatform.backend.entity;

import com.freelanceplatform.backend.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_skill")
@Data
@NoArgsConstructor
public class ProjectSkill {

    @EmbeddedId
    private ProjectSkillId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idProject")
    @JoinColumn(name = "id_project")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSkill")
    @JoinColumn(name = "id_skill")
    private Skill skill;

    @Enumerated(EnumType.STRING)
    @Column(name = "minimum_level", length = 20)
    private SkillLevel minimumLevel;

    @Column(nullable = false)
    private Boolean mandatory = true;
}
