package com.printease.application.mapper;

import com.printease.application.model.Orientation;
import com.printease.application.dto.OrientationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrientationMapper {
    OrientationMapper INSTANCE =  Mappers.getMapper(OrientationMapper.class);
    @Mapping(target = "type", source = "orientation.orientation")
    OrientationDto convertToOrientationDto(Orientation orientation);

    List<OrientationDto> convertToOrientationDtoList(List<Orientation> orientationList);
}
