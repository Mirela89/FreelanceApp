package com.freelanceplatform.backend.mapper;

import com.freelanceplatform.backend.dto.response.ContractResponse;
import com.freelanceplatform.backend.entity.Application;
import com.freelanceplatform.backend.entity.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    @Mapping(target = "freelancerName", source = "freelancer.name")
    @Mapping(target = "projectTitle", source = "project.title")
    @Mapping(target = "clientName", source = "project.client.name")
    ContractResponse toResponse(Contract contract);

    @Mapping(target = "idContract", ignore = true)
    @Mapping(target = "application", source = "application")
    @Mapping(target = "freelancer", source = "application.freelancer")
    @Mapping(target = "project", source = "application.project")
    @Mapping(target = "startDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "endDate", ignore = true)
    @Mapping(target = "status", expression = "java(com.freelanceplatform.backend.enums.ContractStatus.ACTIVE)")
    Contract toEntity(Application application);
}
