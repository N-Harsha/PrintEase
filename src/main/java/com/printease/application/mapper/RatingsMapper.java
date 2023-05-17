package com.printease.application.mapper;

import com.printease.application.dto.RatingDto;
import com.printease.application.model.Rating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatingsMapper {
    RatingsMapper INSTANCE = Mappers.getMapper(RatingsMapper.class);
    @Mapping(source = "customer.name", target = "customerName")
    RatingDto convertToRatingDto(Rating rating);
    List<RatingDto> convertToRatingDtoList(List<Rating> ratings);
}
