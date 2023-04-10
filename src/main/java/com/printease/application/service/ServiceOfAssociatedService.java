package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.*;
import com.printease.application.repository.AssociatedServiceRepository;
import com.printease.application.security.dto.AssociatedServiceDto;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceOfAssociatedService {
    private final AssociatedServiceRepository associatedServiceRepository;
    private final ServiceOfServiceProvider serviceOfServiceProvider;
    private final ServiceOfPrintService serviceOfPrintService;
    private final ExceptionMessageAccessor exceptionMessageAccessor;
    private final GeneralMessageAccessor generalMessageAccessor;
    private final String ASSOCIATED_SERVICE_CREATED_SUCCESSFULLY = "associated_service_created_successfully";
    private final String ASSOCIATED_SERVICE_UPDATED_SUCCESSFULLY = "associated_service_updated_successfully";
    private final String ASSOCIATED_SERVICE_DELETED_SUCCESSFULLY = "associated_service_deleted_successfully";
    private final String ASSOCIATED_SERVICE_NOT_FOUND = "associated_service_not_found";

    private AssociatedService generateAssociatedService(AssociatedServiceDto associatedServiceDto, PrintService printService, ServiceProvider serviceProvider) {

        Orientation orientation;
        String INVALID_ASSOCIATED_SERVICE_CONFIGURATION = "invalid_associated_service_configuration";
        if(associatedServiceDto.getOrientationId()==null){
            orientation = null;
        }
        else {
            Map<Long, Orientation> orientationIdtoOrientationMap = printService.getOrientations().stream().collect(
                    Collectors.toMap(Orientation::getId, Function.identity()));
            if (!orientationIdtoOrientationMap.containsKey(associatedServiceDto.getOrientationId())) {
                log.error("invalid orientation id {} for print service with id {}", associatedServiceDto.getOrientationId()
                        , printService.getId());
                throw new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, INVALID_ASSOCIATED_SERVICE_CONFIGURATION),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now()
                ));
            }
            orientation = orientationIdtoOrientationMap.get(associatedServiceDto.getOrientationId());
        }

        PaperSize paperSize;
        if(associatedServiceDto.getPaperSizeId()==null){
            paperSize = null;
        }
        else {
            Map<Long, PaperSize> paperSizeIdtoPaperSizeMap = printService.getPaperSizes().stream().collect(
                    Collectors.toMap(PaperSize::getId, Function.identity()));
            if (!paperSizeIdtoPaperSizeMap.containsKey(associatedServiceDto.getPaperSizeId())) {
                log.error("invalid paper size id {} for print service with id {}", associatedServiceDto.getPaperSizeId()
                        , printService.getId());
                throw new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, INVALID_ASSOCIATED_SERVICE_CONFIGURATION),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now()
                ));
            }
            paperSize = paperSizeIdtoPaperSizeMap.get(associatedServiceDto.getPaperSizeId());
        }

        PaperType paperType;
        if(associatedServiceDto.getPaperTypeId()==null){
            paperType = null;
        }
        else {
            Map<Long, PaperType> paperTypeIdtoPaperTypeMap = printService.getPaperTypes().stream().collect(
                    Collectors.toMap(PaperType::getId, Function.identity()));
            if (!paperTypeIdtoPaperTypeMap.containsKey(associatedServiceDto.getPaperTypeId())) {
                log.error("invalid paper type id {} for print service with id {}", associatedServiceDto.getPaperTypeId()
                        , printService.getId());
                throw new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, INVALID_ASSOCIATED_SERVICE_CONFIGURATION),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now()
                ));
            }
            paperType = paperTypeIdtoPaperTypeMap.get(associatedServiceDto.getPaperTypeId());
        }

        PrintSide printSide;
        if(associatedServiceDto.getPrintSideId()==null){
            printSide = null;
        }
        else {
            Map<Long, PrintSide> printSideIdtoPrintSideMap = printService.getPrintSides().stream().collect(
                    Collectors.toMap(PrintSide::getId, Function.identity()));
            if (!printSideIdtoPrintSideMap.containsKey(associatedServiceDto.getPrintSideId())) {
                log.error("invalid print side id {} for print service with id {}", associatedServiceDto.getPrintSideId()
                        , printService.getId());
                throw new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, INVALID_ASSOCIATED_SERVICE_CONFIGURATION),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now()
                ));
            }
            printSide = printSideIdtoPrintSideMap.get(associatedServiceDto.getPrintSideId());
        }

        PrintType printType;
        if(associatedServiceDto.getPrintTypeId()==null){
            printType = null;
        }
        else {
            Map<Long, PrintType> printTypeIdtoPrintTypeMap = printService.getPrintTypes().stream().collect(
                    Collectors.toMap(PrintType::getId, Function.identity()));
            if (!printTypeIdtoPrintTypeMap.containsKey(associatedServiceDto.getPrintTypeId())) {
                log.error("invalid print type id {} for print service with id {}", associatedServiceDto.getPrintTypeId()
                        , printService.getId());
                throw new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, INVALID_ASSOCIATED_SERVICE_CONFIGURATION),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now()
                ));
            }
            printType = printTypeIdtoPrintTypeMap.get(associatedServiceDto.getPrintTypeId());
        }

        BindingType bindingType;
        if(associatedServiceDto.getBindingTypeId()==null){
            bindingType = null;
        }
        else {
            Map<Long, BindingType> bindingTypeIdtoBindingTypeMap = printService.getBindingTypes().stream().collect(
                    Collectors.toMap(BindingType::getId, Function.identity()));
            if (!bindingTypeIdtoBindingTypeMap.containsKey(associatedServiceDto.getBindingTypeId())) {
                log.error("invalid binding type id {} for print service with id {}", associatedServiceDto.getBindingTypeId()
                        , printService.getId());
                throw new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null, INVALID_ASSOCIATED_SERVICE_CONFIGURATION),
                        HttpStatus.BAD_REQUEST, LocalDateTime.now()
                ));
            }
            bindingType = bindingTypeIdtoBindingTypeMap.get(associatedServiceDto.getBindingTypeId());
        }

        log.info("associated service configuration is valid, generating");

        return AssociatedService.builder().serviceProvider(serviceProvider).service(printService)
                .price(associatedServiceDto.getPrice())
                .orientation(orientation)
                .paperSize(paperSize)
                .paperType(paperType)
                .printSide(printSide)
                .printType(printType)
                .bindingType(bindingType)
                .build();

    }
    public ResponseEntity<String> addAssociatedService(String email, AssociatedServiceDto associatedServiceDto) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        PrintService printService = serviceOfPrintService.getService(associatedServiceDto.getPrintServiceId());
        AssociatedService associatedService = generateAssociatedService(associatedServiceDto, printService, serviceProvider);
        associatedServiceRepository.save(associatedService);
        log.info("generated associated service with id {}", associatedService.getId());
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null,ASSOCIATED_SERVICE_CREATED_SUCCESSFULLY));
    }


    public ResponseEntity<List<AssociatedService>> getAssociatedServices(String email, Long printServiceId) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());

        if(printServiceId==null){
            log.info("fetching all associated services");
            return ResponseEntity.ok(associatedServiceRepository.findAllByServiceProvider(serviceProvider));
        }
        else {
            log.info("fetching associated services for print service with id {}", printServiceId);
            PrintService printService = serviceOfPrintService.getService(printServiceId);
            return ResponseEntity.ok(associatedServiceRepository.findAllByServiceProviderAndService(serviceProvider, printService));
        }
    }

    public ResponseEntity<String> updateAssociatedService(String email, Long associatedServiceId, AssociatedServiceDto associatedServiceDto) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        if(!associatedServiceRepository.existsByIdAndServiceProvider(associatedServiceId, serviceProvider))
            throw new CustomException(new ApiExceptionResponse(generalMessageAccessor.getMessage(
                    null, ASSOCIATED_SERVICE_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now()));
        log.info("associated service with id {} exists", associatedServiceId);
        PrintService printService = serviceOfPrintService.getService(associatedServiceDto.getPrintServiceId());
        AssociatedService associatedService = generateAssociatedService(associatedServiceDto,printService , serviceProvider);
        associatedService.setId(associatedServiceId);
        associatedServiceRepository.save(associatedService);
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null,ASSOCIATED_SERVICE_UPDATED_SUCCESSFULLY));
    }


    public ResponseEntity<String> deleteAssociatedService(String email, Long associatedServiceId) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        if(!associatedServiceRepository.existsByIdAndServiceProvider(associatedServiceId, serviceProvider))
            throw new CustomException(new ApiExceptionResponse(generalMessageAccessor.getMessage(
                    null, ASSOCIATED_SERVICE_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now()));
        log.info("associated service with id {} exists", associatedServiceId);
        associatedServiceRepository.deleteById(associatedServiceId);
        log.info("associated service with id {} deleted", associatedServiceId);
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null,ASSOCIATED_SERVICE_DELETED_SUCCESSFULLY));
    }
}
