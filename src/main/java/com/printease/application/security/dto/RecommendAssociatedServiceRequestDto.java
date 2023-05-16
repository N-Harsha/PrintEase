package com.printease.application.security.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendAssociatedServiceRequestDto {
    @NotNull
    private Long printServiceId;
    private List<Long> orientationIds;
    private List<Long> paperSizeIds;
    private List<Long> paperTypeIds;
    private List<Long> printSideIds;
    private List<Long> printTypeIds;
    private List<Long> bindingTypeIds;
    @NotNull
    private Double longitude;
    @NotNull
    private Double latitude;
}
