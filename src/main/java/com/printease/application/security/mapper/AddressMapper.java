package com.printease.application.security.mapper;

import com.printease.application.model.Address;
import com.printease.application.security.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);
    AddressDto convertToAddressDto(Address address);
}
