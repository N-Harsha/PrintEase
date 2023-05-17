package com.printease.application.mapper;

import com.printease.application.model.PrintSide;
import com.printease.application.dto.PrintSideDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrintSideMapper {
    PrintSideMapper INSTANCE = Mappers.getMapper(PrintSideMapper.class);

    @Mapping(target = "type", source = "printSide.printSideType")
    PrintSideDto convertToPrintSideDto(PrintSide printSide);

    List<PrintSideDto> convertToPrintSideDtoList(List<PrintSide> printSides);
}
