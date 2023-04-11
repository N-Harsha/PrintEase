package com.printease.application.security.mapper;

import com.printease.application.model.AssociatedService;
import com.printease.application.security.dto.AssociatedServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssociatedServiceMapper {
    AssociatedServiceMapper INSTANCE = Mappers.getMapper(AssociatedServiceMapper.class);
    AssociatedService convertToAssociatedService(AssociatedServiceDto associatedServiceDto);
    AssociatedServiceDto convertToAssociatedServiceDto(AssociatedService associatedService);
}
