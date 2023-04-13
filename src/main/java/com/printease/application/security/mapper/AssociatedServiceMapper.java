package com.printease.application.security.mapper;

import com.printease.application.model.AssociatedService;
import com.printease.application.security.dto.AssociatedServiceDto;
import com.printease.application.security.dto.RecommendAssociatedServiceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssociatedServiceMapper {
    AssociatedServiceMapper INSTANCE = Mappers.getMapper(AssociatedServiceMapper.class);
    AssociatedService convertToAssociatedService(AssociatedServiceDto associatedServiceDto);
    @Named("convertToAssociatedServiceDto")
    AssociatedServiceDto convertToAssociatedServiceDto(AssociatedService associatedService);

    @Mapping(source = "associatedService.serviceProvider.id", target = "serviceProviderId")
    @Mapping(source = "associatedService.serviceProvider.businessName", target = "serviceProviderName")
    @Mapping(source = "associatedService", target = "associatedService", qualifiedByName = "convertToAssociatedServiceDto")
    RecommendAssociatedServiceResponseDto convertToRecommendAssociatedServiceResponseDto(AssociatedService associatedService);
}
