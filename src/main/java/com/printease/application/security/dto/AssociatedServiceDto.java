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

    private Long id;

    private OrientationDto  orientation;

    private PaperSizeDto paperSize;

    private PaperTypeDto paperType;

    private PrintSideDto printSide;

    private PrintTypeDto printType;

    private BindingTypeDto bindingType;

    private Long printServiceId;

    @NotNull
    private Float price;
}
