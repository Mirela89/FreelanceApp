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
public class ProjectSkillId implements Serializable {

    @Column(name = "id_project")
    private Long idProject;

    @Column(name = "id_skill")
    private Long idSkill;
}
