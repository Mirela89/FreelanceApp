package com.freelanceplatform.backend.mapper;

import com.freelanceplatform.backend.dto.request.ApplicationRequest;
import com.freelanceplatform.backend.dto.response.ApplicationResponse;
import com.freelanceplatform.backend.entity.Application;
import com.freelanceplatform.backend.entity.Freelancer;
import com.freelanceplatform.backend.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {
    @Mapping(target = "freelancerName", source = "freelancer.name")
    @Mapping(target = "projectTitle", source = "project.title")
    ApplicationResponse toResponse(Application application);

    @Mapping(target = "idApplication", ignore = true)
    @Mapping(target = "freelancer", source = "freelancer")
    @Mapping(target = "project", source = "project")
    @Mapping(target = "status", expression = "java(com.freelanceplatform.backend.enums.ApplicationStatus.SUBMITTED)")
    @Mapping(target = "applicationDate", expression = "java(java.time.LocalDate.now())")
    Application toEntity(ApplicationRequest request, Freelancer freelancer, Project project);
}
