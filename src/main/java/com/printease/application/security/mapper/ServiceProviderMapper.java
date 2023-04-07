package com.printease.application.security.mapper;

import com.printease.application.model.ServiceProvider;
import com.printease.application.model.User;
import com.printease.application.security.dto.AuthenticatedUserDto;
import com.printease.application.security.dto.RegistrationRequest;
import com.printease.application.security.dto.ServiceProviderRegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceProviderMapper {
    ServiceProviderRequestMapper INSTANCE = Mappers.getMapper(ServiceProviderRequestMapper.class);
    ServiceProvider convertToServiceProvider(ServiceProviderRegistrationRequest registrationRequest);

    AuthenticatedUserDto convertToAuthenticatedUserDto(ServiceProvider serviceProvider);

    ServiceProvider convertToServiceProvider(AuthenticatedUserDto authenticatedUserDto);

}
