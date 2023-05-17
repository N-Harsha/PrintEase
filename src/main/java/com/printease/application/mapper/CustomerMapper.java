package com.printease.application.mapper;

import com.printease.application.model.Customer;
import com.printease.application.dto.AuthenticatedUserDto;
import com.printease.application.dto.RegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer convertToCustomer(RegistrationRequest customerRegistrationRequest);

    AuthenticatedUserDto convertToAuthenticatedUserDto(Customer customer);

    Customer convertToCustomer(AuthenticatedUserDto authenticatedUserDto);

}
