package com.printease.application.security.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendAssociatedServiceRequestDto {
    private Long orientationId;
    private Long paperSizeId;
    private Long paperTypeId;
    private Long printSideId;
    private Long printTypeId;
    private Long bindingTypeId;
    private Long printServiceId;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
}
