package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.*;
import com.printease.application.repository.AssociatedServiceRepository;
import com.printease.application.security.dto.AssociatedServiceDto;
import com.printease.application.security.mapper.AssociatedServiceMapper;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
    private final String ASSOCIATED_SERVICE_NOT_FOUND = "associated_service_not_found";

    @Transactional
    public ResponseEntity<String> addAssociatedService(String email, AssociatedServiceDto associatedServiceDto, Long printServiceId) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        PrintService printService = serviceOfPrintService.getService(printServiceId);
        AssociatedService associatedService = AssociatedServiceMapper.INSTANCE.convertToAssociatedService(associatedServiceDto);
        associatedService.setService(printService);
        associatedService.setServiceProvider(serviceProvider);
        associatedServiceRepository.save(associatedService);
        log.info("generated associated service with id {}", associatedService.getId());
        String ASSOCIATED_SERVICE_CREATED_SUCCESSFULLY = "associated_service_created_successfully";
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null, ASSOCIATED_SERVICE_CREATED_SUCCESSFULLY));
    }


    public ResponseEntity<List<AssociatedServiceDto>> getAssociatedServices(String email, Long printServiceId) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        log.info("fetching associated services for print service with id {}", printServiceId);
        PrintService printService = serviceOfPrintService.getService(printServiceId);
        return ResponseEntity.ok(associatedServicesCombinations(printService, serviceProvider).stream().map(AssociatedServiceMapper.INSTANCE::convertToAssociatedServiceDto).collect(Collectors.toList()));
    }

    private List<AssociatedService> associatedServicesCombinations(PrintService service, ServiceProvider serviceProvider) {
        List<AssociatedService> associatedServices = new ArrayList<>();
        List<Orientation> orientations = service.getOrientations();
        List<PaperSize> paperSizes = service.getPaperSizes();
        List<PaperType> paperTypes = service.getPaperTypes();
        List<PrintSide> printSides = service.getPrintSides();
        List<PrintType> printTypes = service.getPrintTypes();
        List<BindingType> bindingTypes = service.getBindingTypes();
        List<List<?>> configList = List.of(orientations, paperSizes, paperTypes, printSides, printTypes, bindingTypes);
        AssociatedService baseAssociatedService = new AssociatedService();
        baseAssociatedService.setService(service);
        baseAssociatedService.setServiceProvider(serviceProvider);
        List<AssociatedService> existingAssociatedServices = associatedServiceRepository.findAllByServiceProviderAndService(serviceProvider, service);
        log.info("fetched existing associated services with size {}", existingAssociatedServices.size());
        associatedServicesCombinationsHelper(configList, 0, baseAssociatedService, associatedServices, existingAssociatedServices);
        associatedServices.addAll(0, existingAssociatedServices);
        return associatedServices;
    }

    private boolean findAssociatedServiceInExistingList(List<AssociatedService> existingAssociatedServices, AssociatedService associatedService) {

        for (AssociatedService existingAssociatedService : existingAssociatedServices) {
            if (
                    (existingAssociatedService.getOrientation() == null || Objects.equals(existingAssociatedService.getOrientation().getId(), associatedService.getOrientation().getId()) )&&
                            (existingAssociatedService.getPaperSize() == null || Objects.equals(existingAssociatedService.getPaperSize().getId(), associatedService.getPaperSize().getId()) )&&
                            (existingAssociatedService.getPaperType() == null || Objects.equals(existingAssociatedService.getPaperType().getId(), associatedService.getPaperType().getId()) )&&
                            (existingAssociatedService.getPrintSide() == null || Objects.equals(existingAssociatedService.getPrintSide().getId(), associatedService.getPrintSide().getId()) )&&
                            (existingAssociatedService.getPrintType() == null || Objects.equals(existingAssociatedService.getPrintType().getId(), associatedService.getPrintType().getId()) )&&
                            (existingAssociatedService.getBindingType() == null || Objects.equals(existingAssociatedService.getBindingType().getId(), associatedService.getBindingType().getId()))
            )
                return true;
        }
        return false;
    }

    private void associatedServicesCombinationsHelper(List<List<?>> configList,
                                                      int idx, AssociatedService baseAssociatedService,
                                                      List<AssociatedService> associatedServices,
                                                      List<AssociatedService> existingAssociatedServices) {
        if (idx == configList.size()) {
            AssociatedService newAssociatedService = AssociatedService.builder()
                    .orientation(baseAssociatedService.getOrientation())
                    .paperSize(baseAssociatedService.getPaperSize())
                    .paperType(baseAssociatedService.getPaperType())
                    .printSide(baseAssociatedService.getPrintSide())
                    .printType(baseAssociatedService.getPrintType())
                    .bindingType(baseAssociatedService.getBindingType())
                    .build();
            if(!findAssociatedServiceInExistingList(existingAssociatedServices, newAssociatedService))
                associatedServices.add(newAssociatedService);
            return;
        }
        if (configList.get(idx).size() == 0) {
            setterForDynamicType(idx, baseAssociatedService, null);
            associatedServicesCombinationsHelper(configList, idx + 1, baseAssociatedService, associatedServices, existingAssociatedServices);
        } else {
            for (int i = 0; i < configList.get(idx).size(); i++) {
                setterForDynamicType(idx, baseAssociatedService, configList.get(idx).get(i));
                associatedServicesCombinationsHelper(configList, idx + 1, baseAssociatedService, associatedServices, existingAssociatedServices);
            }
        }
    }

    private void setterForDynamicType(int idx, AssociatedService baseAssociatedService, Object object) {
        switch (idx) {
            case 0:
                baseAssociatedService.setOrientation((Orientation) object);
                break;
            case 1:
                baseAssociatedService.setPaperSize((PaperSize) object);
                break;
            case 2:
                baseAssociatedService.setPaperType((PaperType) object);
                break;
            case 3:
                baseAssociatedService.setPrintSide((PrintSide) object);
                break;
            case 4:
                baseAssociatedService.setPrintType((PrintType) object);
                break;
            case 5:
                baseAssociatedService.setBindingType((BindingType) object);
                break;
        }
    }


    public ResponseEntity<String> updateAssociatedService(String email, Long associatedServiceId, AssociatedServiceDto associatedServiceDto) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        if (!associatedServiceRepository.existsByIdAndServiceProvider(associatedServiceId, serviceProvider))
            throw new CustomException(new ApiExceptionResponse(generalMessageAccessor.getMessage(
                    null, ASSOCIATED_SERVICE_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now()));
        log.info("associated service with id {} exists", associatedServiceId);
        PrintService printService = serviceOfPrintService.getService(associatedServiceId);
        AssociatedService associatedService = AssociatedServiceMapper.INSTANCE.convertToAssociatedService(associatedServiceDto);
        associatedService.setService(printService);
        associatedService.setServiceProvider(serviceProvider);
        associatedServiceRepository.save(associatedService);
        String ASSOCIATED_SERVICE_UPDATED_SUCCESSFULLY = "associated_service_updated_successfully";
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null, ASSOCIATED_SERVICE_UPDATED_SUCCESSFULLY));
    }


    public ResponseEntity<String> deleteAssociatedService(String email, Long associatedServiceId) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        if (!associatedServiceRepository.existsByIdAndServiceProvider(associatedServiceId, serviceProvider))
            throw new CustomException(new ApiExceptionResponse(generalMessageAccessor.getMessage(
                    null, ASSOCIATED_SERVICE_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now()));
        log.info("associated service with id {} exists", associatedServiceId);
        associatedServiceRepository.deleteById(associatedServiceId);
        log.info("associated service with id {} deleted", associatedServiceId);
        String ASSOCIATED_SERVICE_DELETED_SUCCESSFULLY = "associated_service_deleted_successfully";
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null, ASSOCIATED_SERVICE_DELETED_SUCCESSFULLY));
    }
}
