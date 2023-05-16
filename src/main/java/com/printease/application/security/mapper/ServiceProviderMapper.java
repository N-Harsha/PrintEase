package com.printease.application.security.mapper;

import com.printease.application.model.AssociatedService;
import com.printease.application.model.Orientation;
import com.printease.application.model.ServiceProvider;
import com.printease.application.model.User;
import com.printease.application.security.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceProviderMapper {
    ServiceProviderMapper INSTANCE = Mappers.getMapper(ServiceProviderMapper.class);

    @Mapping(source = "serviceProvider.id", target = "serviceProviderId")
    @Mapping(source = "serviceProvider.name", target = "serviceProviderName")
    @Mapping(source = "serviceProvider.businessName", target = "serviceProviderBusinessName")
    @Mapping(source = "serviceProvider.associatedServices", target = "associatedServices", qualifiedByName = "convertToAssociateServiceDtos")
    RecommendAssociatedServiceResponseDto convertToRecommendAssociatedServiceResponseDto(ServiceProvider serviceProvider);

    @Named("convertToAssociateServiceDtos")
    default List<AssociatedServiceDto> convertToAssociateServiceDtos(List<AssociatedService> associatedServices){
        return associatedServices.stream().map(AssociatedServiceMapper.INSTANCE::convertToAssociatedServiceDto)
                .collect(Collectors.toList());
    }
}
