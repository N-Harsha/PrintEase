package com.printease.application.mapper;

import com.printease.application.dto.*;
import com.printease.application.model.*;
import com.printease.application.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PrintServiceMapper {
    PrintServiceMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(PrintServiceMapper.class);
    PrintServiceDto convertToPrintServiceDto(PrintService printService);

    @Mapping(source = "printService.orientations", target = "orientations", qualifiedByName = "convertToOrientationDtoList")
    @Mapping(source = "printService.printTypes", target = "printTypes", qualifiedByName = "convertToPrintTypeServiceDtoList")
    @Mapping(source = "printService.printSides", target = "printSides", qualifiedByName = "convertToPrintSideDtoList")
    @Mapping(source = "printService.paperSizes", target = "paperSizes", qualifiedByName = "convertToPaperSizeDtoList")
    @Mapping(source = "printService.paperTypes", target = "paperTypes", qualifiedByName = "convertToPaperTypeDtoList")
    @Mapping(source = "printService.bindingTypes", target = "bindingTypes", qualifiedByName = "convertToBindingTypeDtoList")
    SpecificPrintServiceDto convertToSpecificPrintServiceDto(PrintService printService);

    @Named("convertToOrientationDtoList")
    default List<OrientationDto> convertToOrientationDtoList(List<Orientation> orientationList){
        return OrientationMapper.INSTANCE.convertToOrientationDtoList(orientationList);
    }
    @Named("convertToPrintTypeServiceDtoList")
    default List<PrintTypeDto> convertToPrintTypeServiceDtoList(List<PrintType> printTypes) {
        return PrintTypeMapper.INSTANCE.convertToPrintTypeDtoList(printTypes);
    }
    @Named("convertToPrintSideDtoList")
    default List<PrintSideDto> convertToPrintSideDtoList(List<PrintSide> printSides) {
        return PrintSideMapper.INSTANCE.convertToPrintSideDtoList(printSides);
    }

    @Named("convertToPaperSizeDtoList")
    default List<PaperSizeDto> convertToPaperSizeDtoList(List<PaperSize> paperSizes) {
        return PaperSizeMapper.INSTANCE.convertToPaperSizeDtoList(paperSizes);
    }

    @Named("convertToPaperTypeDtoList")
    default List<PaperTypeDto> convertToPaperTypeDtoList(List<PaperType> paperTypes) {
        return PaperTypeMapper.INSTANCE.convertToPaperTypeDtoList(paperTypes);
    }

    @Named("convertToBindingTypeDtoList")
    default List<BindingTypeDto> convertToBindingTypeDtoList(List<BindingType> bindingTypes) {
        return BindingTypeMapper.INSTANCE.convertToBindingTypeDtoList(bindingTypes);
    }


}
