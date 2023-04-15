package com.printease.application.security.mapper;

import com.printease.application.model.PaperSize;
import com.printease.application.security.dto.PaperSizeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaperSizeMapper {
    PaperSizeMapper INSTANCE = Mappers.getMapper(PaperSizeMapper.class);

    @Mapping(target = "type", source = "paperSize.size")
    PaperSizeDto convertToPaperSizeDto(PaperSize paperSize);

    List<PaperSizeDto> convertToPaperSizeDtoList(List<PaperSize> paperSizes);
}
