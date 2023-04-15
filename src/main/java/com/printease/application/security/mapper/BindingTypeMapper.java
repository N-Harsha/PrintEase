package com.printease.application.security.mapper;

import com.printease.application.model.BindingType;
import com.printease.application.security.dto.BindingTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BindingTypeMapper {
    BindingTypeMapper INSTANCE = Mappers.getMapper(BindingTypeMapper.class);
    @Mapping(target = "type", source = "bindingType.name")
    BindingTypeDto convertToBindingTypeDto(BindingType bindingType);

    List<BindingTypeDto> convertToBindingTypeDtoList(List<BindingType> bindingTypeList);
}
