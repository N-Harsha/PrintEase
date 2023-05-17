package com.printease.application.mapper;

import com.printease.application.model.PaperType;
import com.printease.application.dto.PaperTypeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaperTypeMapper {
    PaperTypeMapper INSTANCE = Mappers.getMapper(PaperTypeMapper.class);

    @Mapping(target = "type", source = "paperType.type")
    PaperTypeDto convertToPaperTypeDto(PaperType paperType);

    List<PaperTypeDto> convertToPaperTypeDtoList(List<PaperType> paperTypes);
}
