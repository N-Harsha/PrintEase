package com.printease.application.security.mapper;

import com.printease.application.model.*;
import com.printease.application.security.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AssociatedServiceMapper {
    AssociatedServiceMapper INSTANCE = Mappers.getMapper(AssociatedServiceMapper.class);
    AssociatedService convertToAssociatedService(AssociatedServiceDto associatedServiceDto);
    @Named("convertToAssociatedServiceDto")
    @Mapping(source = "orientation", target = "orientation", qualifiedByName = "convertToOrientationDto")
    @Mapping(source = "paperSize", target = "paperSize", qualifiedByName = "convertToPaperSizeDto")
    @Mapping(source = "paperType", target = "paperType", qualifiedByName = "convertToPaperTypeDto")
    @Mapping(source = "printSide", target = "printSide", qualifiedByName = "convertToPrintSideDto")
    @Mapping(source = "printType", target = "printType", qualifiedByName = "convertToPrintTypeDto")
    @Mapping(source = "bindingType", target = "bindingType", qualifiedByName = "convertToBindingTypeDto")
    AssociatedServiceDto convertToAssociatedServiceDto(AssociatedService associatedService);

    @Mapping(source = "associatedService.serviceProvider.id", target = "serviceProviderId")
    @Mapping(source = "associatedService.serviceProvider.businessName", target = "serviceProviderName")
    @Mapping(source = "associatedService", target = "associatedService", qualifiedByName = "convertToAssociatedServiceDto")
    RecommendAssociatedServiceResponseDto convertToRecommendAssociatedServiceResponseDto(AssociatedService associatedService);

    @Named("convertToOrientationDto")
    default OrientationDto convertToOrientationDto(Orientation orientation){
        return OrientationMapper.INSTANCE.convertToOrientationDto(orientation);
    }
    @Named("convertToPaperSizeDto")
    default PaperSizeDto convertToPaperSizeDto(com.printease.application.model.PaperSize paperSize){
        return PaperSizeMapper.INSTANCE.convertToPaperSizeDto(paperSize);
    }
    @Named("convertToPaperTypeDto")
    default PaperTypeDto convertToPaperTypeDto(PaperType paperType){
        return PaperTypeMapper.INSTANCE.convertToPaperTypeDto(paperType);
    }
    @Named("convertToPrintSideDto")
    default PrintSideDto convertToPrintSideDto(PrintSide printSide){
        return PrintSideMapper.INSTANCE.convertToPrintSideDto(printSide);
    }

    @Named("convertToPrintTypeDto")
    default PrintTypeDto convertToPrintTypeDto(PrintType printType){
        return PrintTypeMapper.INSTANCE.convertToPrintTypeDto(printType);
    }
    @Named("convertToBindingTypeDto")
    default BindingTypeDto convertToBindingTypeDto(BindingType bindingType){
        return BindingTypeMapper.INSTANCE.convertToBindingTypeDto(bindingType);
    }

}
