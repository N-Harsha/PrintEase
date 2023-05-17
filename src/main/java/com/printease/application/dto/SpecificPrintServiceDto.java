package com.printease.application.dto;

import lombok.Data;


import java.util.List;

@Data
public class SpecificPrintServiceDto {
    private Long id;
    private String serviceName;
    private String description;
    private List<PrintTypeDto> printTypes;
    private List<PrintSideDto> printSides;
    private List<PaperSizeDto> paperSizes;
    private List<PaperTypeDto> paperTypes;
    private List<OrientationDto> orientations;
    private List<BindingTypeDto> bindingTypes;
}
