package com.printease.application.mapper;

import com.printease.application.model.ServiceProvider;
import com.printease.application.dto.RegistrationRequest;
import com.printease.application.dto.ServiceProviderDto;
import com.printease.application.dto.ServiceProviderRegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServiceProviderRequestMapper {

    ServiceProviderRequestMapper INSTANCE = Mappers.getMapper(ServiceProviderRequestMapper.class);
    ServiceProviderRegistrationRequest convertToServiceProviderRegistrationRequest(RegistrationRequest registrationRequest);
    ServiceProvider convertToServiceProvider(ServiceProviderRegistrationRequest serviceProviderRegistrationRequest);

    ServiceProviderDto convertToServiceProviderDto(ServiceProvider serviceProvider);


}
