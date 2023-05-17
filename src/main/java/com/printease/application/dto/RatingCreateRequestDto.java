package com.printease.application.dto;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingCreateRequestDto {
    @NotNull(message = "Service Provider Id should not be null")
    private Long serviceProviderId;
    @Max(value=5,message = "Rating should not be greater than 5")
    @Min(value=0,message = "Rating should not be less than 0")
    @NotNull(message = "Rating should not be null")
//    todo: later check whether this is working or not
    private Float rating;
    private String comment;
}
