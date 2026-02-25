package com.freelanceplatform.backend.mapper;

import com.freelanceplatform.backend.dto.request.RegisterRequest;
import com.freelanceplatform.backend.entity.Client;
import com.freelanceplatform.backend.entity.Freelancer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "passwordHash", source = "encodedPassword")
    @Mapping(target = "profileTitle", source = "request.profileTitle")
    @Mapping(target = "availability", constant = "AVAILABLE")
    @Mapping(target = "rating", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "bio", source = "request.bio")  // ← rezolva warning-ul
    Freelancer toFreelancer(RegisterRequest request, String encodedPassword);

    @Mapping(target = "idUser", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "passwordHash", source = "encodedPassword")
    @Mapping(target = "clientType", source = "request.clientType")
    @Mapping(target = "companyName", source = "request.companyName")
    @Mapping(target = "taxId", source = "request.taxId")
    @Mapping(target = "city", ignore = true)
    Client toClient(RegisterRequest request, String encodedPassword);
}
