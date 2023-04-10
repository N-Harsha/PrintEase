package com.printease.application.security.mapper;

import com.printease.application.model.PrintService;
import com.printease.application.security.dto.PrintServiceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrintServiceMapper {
    PrintServiceMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PrintServiceMapper.class);
    PrintServiceDto convertToPrintServiceDto(PrintService printService);

}
