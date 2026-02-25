package com.freelanceplatform.backend.mapper;

import com.freelanceplatform.backend.dto.request.ProjectRequest;
import com.freelanceplatform.backend.dto.response.ProjectResponse;
import com.freelanceplatform.backend.entity.Client;
import com.freelanceplatform.backend.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(target = "clientName", source = "client.name")
    ProjectResponse toResponse(Project project);

    @Mapping(target = "idProject", ignore = true)
    @Mapping(target = "postingDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "status", constant = "OPEN")
    @Mapping(target = "client", source = "client")
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "skills", ignore = true)
    Project toEntity(ProjectRequest request, Client client);
}
