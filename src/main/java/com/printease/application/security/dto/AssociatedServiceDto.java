package com.printease.application.security.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssociatedServiceDto {
    private static final long serialVersionUID = 1L;
    @NotNull
    private Long printServiceId;
    private Float price;
    private Long orientationId;
    private Long paperSizeId;
    private Long paperTypeId;
    private Long printSideId;
    private Long printTypeId;
    private Long bindingTypeId;
}
