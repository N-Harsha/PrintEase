package com.printease.application.mapper;

import com.printease.application.model.PrintType;
import com.printease.application.dto.PrintTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrintTypeMapper {
    PrintTypeMapper INSTANCE = Mappers.getMapper(PrintTypeMapper.class);

    @Mapping(target = "type", source = "printType.type")
    PrintTypeDto convertToPrintTypeDto(PrintType printType);

    List<PrintTypeDto> convertToPrintTypeDtoList(List<PrintType> printTypes);

}
