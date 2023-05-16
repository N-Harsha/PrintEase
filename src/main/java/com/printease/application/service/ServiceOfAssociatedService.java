package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.*;
import com.printease.application.repository.AssociatedServiceRepository;
import com.printease.application.security.dto.AssociatedServiceDto;
import com.printease.application.security.dto.MessageWrapperDto;
import com.printease.application.security.dto.RecommendAssociatedServiceRequestDto;
import com.printease.application.security.dto.RecommendAssociatedServiceResponseDto;
import com.printease.application.security.mapper.AssociatedServiceMapper;
import com.printease.application.security.mapper.ServiceProviderMapper;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import com.printease.application.utils.GeneralUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final CustomerService customerService;
    private final ExceptionMessageAccessor exceptionMessageAccessor;
    private final GeneralMessageAccessor generalMessageAccessor;
    private final String ASSOCIATED_SERVICE_NOT_FOUND = "associated_service_not_found";


    public void validateAssociatedServiceDto(AssociatedServiceDto associatedServiceDto, PrintService printService) {
        if (associatedServiceDto.getOrientation() != null && printService.getOrientations().stream().noneMatch(orientation -> Objects.equals(orientation.getId(), associatedServiceDto.getOrientation().getId()))) {
            final String ORIENTATION_NOT_FOUND = "orientation_not_found";
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    ORIENTATION_NOT_FOUND), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        if (associatedServiceDto.getPaperType() != null && printService.getPaperTypes().stream().noneMatch(paperType -> Objects.equals(paperType.getId(), associatedServiceDto.getPaperType().getId()))) {
            final String PAPER_TYPE_NOT_FOUND = "paper_type_not_found";
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    PAPER_TYPE_NOT_FOUND), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        if (associatedServiceDto.getPaperSize() != null && printService.getPaperSizes().stream().noneMatch(paperSize -> Objects.equals(paperSize.getId(), associatedServiceDto.getPaperSize().getId()))) {
            final String PAPER_SIZE_NOT_FOUND = "paper_size_not_found";
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    PAPER_SIZE_NOT_FOUND), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        if (associatedServiceDto.getPrintSide() != null && printService.getPrintSides().stream().noneMatch(printSide -> Objects.equals(printSide.getId(), associatedServiceDto.getPrintSide().getId()))) {
            final String PRINT_SIDE_NOT_FOUND = "print_side_not_found";
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    PRINT_SIDE_NOT_FOUND), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        if (associatedServiceDto.getPrintType() != null && printService.getPrintTypes().stream().noneMatch(printType -> Objects.equals(printType.getId(), associatedServiceDto.getPrintType().getId()))) {
            final String PRINT_TYPE_NOT_FOUND = "print_type_not_found";
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    PRINT_TYPE_NOT_FOUND), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        if (associatedServiceDto.getBindingType() != null && printService.getBindingTypes().stream().noneMatch(bindingType -> Objects.equals(bindingType.getId(), associatedServiceDto.getBindingType().getId()))) {
            final String BINDING_TYPE_NOT_FOUND = "binding_type_not_found";
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    BINDING_TYPE_NOT_FOUND), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
    }

    @Transactional
    public ResponseEntity<MessageWrapperDto> addAssociatedService(String email, AssociatedServiceDto associatedServiceDto) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        PrintService printService = serviceOfPrintService.getService(associatedServiceDto.getPrintServiceId());
        validateAssociatedServiceDto(associatedServiceDto, printService);
        AssociatedService associatedService = AssociatedServiceMapper.INSTANCE.convertToAssociatedService(associatedServiceDto);
        List<AssociatedService> associatedServices = associatedServiceRepository.findAllByServiceProviderAndService(serviceProvider, printService);
        if (findAssociatedServiceInExistingList(associatedServices, associatedService)) {
            final String ASSOCIATED_SERVICE_ALREADY_EXISTS = "associated_service_already_exists";
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,
                    ASSOCIATED_SERVICE_ALREADY_EXISTS), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        associatedService.setService(printService);
        associatedService.setServiceProvider(serviceProvider);
        associatedServiceRepository.save(associatedService);
        log.info("generated associated service with id {}", associatedService.getId());
        String ASSOCIATED_SERVICE_CREATED_SUCCESSFULLY = "associated_service_created_successfully";
        return ResponseEntity.ok(new MessageWrapperDto(generalMessageAccessor.getMessage(null,
                ASSOCIATED_SERVICE_CREATED_SUCCESSFULLY)));
    }


    @Transactional
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
                    (existingAssociatedService.getOrientation() == null || Objects.equals(existingAssociatedService.getOrientation().getId(), associatedService.getOrientation().getId())) &&
                            (existingAssociatedService.getPaperSize() == null || Objects.equals(existingAssociatedService.getPaperSize().getId(), associatedService.getPaperSize().getId())) &&
                            (existingAssociatedService.getPaperType() == null || Objects.equals(existingAssociatedService.getPaperType().getId(), associatedService.getPaperType().getId())) &&
                            (existingAssociatedService.getPrintSide() == null || Objects.equals(existingAssociatedService.getPrintSide().getId(), associatedService.getPrintSide().getId())) &&
                            (existingAssociatedService.getPrintType() == null || Objects.equals(existingAssociatedService.getPrintType().getId(), associatedService.getPrintType().getId())) &&
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
            if (!findAssociatedServiceInExistingList(existingAssociatedServices, newAssociatedService))
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


    @Transactional
    public ResponseEntity<MessageWrapperDto> updateAssociatedService(String email, AssociatedServiceDto associatedServiceDto) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        if (!associatedServiceRepository.existsByIdAndServiceProvider(associatedServiceDto.getId(), serviceProvider))
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(
                    null, ASSOCIATED_SERVICE_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now()));
        log.info("associated service with id {} exists", associatedServiceDto.getId());
        PrintService printService = serviceOfPrintService.getService(associatedServiceDto.getPrintServiceId());
        validateAssociatedServiceDto(associatedServiceDto, printService);
        AssociatedService associatedService = AssociatedServiceMapper.INSTANCE.convertToAssociatedService(associatedServiceDto);
        associatedService.setService(printService);
        associatedService.setServiceProvider(serviceProvider);
        associatedServiceRepository.save(associatedService);
        String ASSOCIATED_SERVICE_UPDATED_SUCCESSFULLY = "associated_service_updated_successfully";
        return ResponseEntity.ok(new MessageWrapperDto(generalMessageAccessor.getMessage(null,
                ASSOCIATED_SERVICE_UPDATED_SUCCESSFULLY)));
    }


    @Transactional
    public ResponseEntity<MessageWrapperDto> deleteAssociatedService(String email, Long associatedServiceId) {
        ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
        log.info("fetched service provider with id {}", serviceProvider.getId());
        if (!associatedServiceRepository.existsByIdAndServiceProvider(associatedServiceId, serviceProvider))
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(
                    null, ASSOCIATED_SERVICE_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now()));
        log.info("associated service with id {} exists", associatedServiceId);
        associatedServiceRepository.deleteById(associatedServiceId);
        log.info("associated service with id {} deleted", associatedServiceId);
        String ASSOCIATED_SERVICE_DELETED_SUCCESSFULLY = "associated_service_deleted_successfully";
        return ResponseEntity.ok(new MessageWrapperDto(generalMessageAccessor.getMessage(null,
                ASSOCIATED_SERVICE_DELETED_SUCCESSFULLY)));
    }

    public AssociatedService findAssociatedServiceById(Long id) {
        return associatedServiceRepository.findById(id).orElseThrow(() -> new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(
                null, ASSOCIATED_SERVICE_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now())));
    }

    @Transactional
    //optimize this service to perform the operations like sorting in the database
    public ResponseEntity<List<RecommendAssociatedServiceResponseDto>> recommendAssociatedService(String email, RecommendAssociatedServiceRequestDto requestDto) {
        Customer customer = customerService.findCustomerByUserEmail(email);
        log.info("fetched customer with id {}", customer.getId());
        List<ServiceProvider> serviceProviderList = serviceOfServiceProvider.getAllServiceProviders();
        log.info("fetched all service providers");
//        todo break this into smaller queries fetch the service provider info only the part which is needed and then fetch the associated services with the filter from the db and attach it to the result
        return ResponseEntity.ok(serviceProviderList.stream()
                .map(serviceProvider -> {
                    RecommendAssociatedServiceResponseDto responseDto = ServiceProviderMapper.INSTANCE.convertToRecommendAssociatedServiceResponseDto(serviceProvider);
                    responseDto.setDistance(GeneralUtils.calculateDistance(
                            requestDto.getLongitude(),
                            requestDto.getLatitude(),
                            serviceProvider.getAddress().getLongitude(),
                            serviceProvider.getAddress().getLatitude()));
                    responseDto.setAssociatedServices(responseDto.getAssociatedServices().stream()
                            .filter(associatedService -> associateServiceFilter(associatedService, requestDto))
                            .sorted((a, b) -> {
                                if (a.getPrice() == null && b.getPrice() == null)
                                    return 0;
                                if (a.getPrice() == null)
                                    return 1;
                                if (b.getPrice() == null)
                                    return -1;
                                return a.getPrice().compareTo(b.getPrice());
                            })
                            .collect(Collectors.toList()));
                    return responseDto;
                })
                .filter((serviceProvider) -> serviceProvider.getAssociatedServices().size() > 0)
                .collect(Collectors.toList()));
    }

    private Boolean associateServiceFilter(AssociatedServiceDto associatedService, RecommendAssociatedServiceRequestDto requestDto) {
        return Objects.equals(requestDto.getPrintServiceId(), associatedService.getPrintServiceId())
                && (associatedService.getOrientation() == null || requestDto.getOrientationIds() == null ||
                requestDto.getOrientationIds().size() == 0 || requestDto.getOrientationIds().contains(associatedService.getOrientation().getId()))
                && (associatedService.getPaperSize() == null || requestDto.getPaperSizeIds() == null ||
                requestDto.getPaperSizeIds().size() == 0 || requestDto.getPaperSizeIds().contains(associatedService.getPaperSize().getId()))
                && (associatedService.getPaperType() == null || requestDto.getPaperTypeIds() == null ||
                requestDto.getPaperTypeIds().size() == 0 || requestDto.getPaperTypeIds().contains(associatedService.getPaperType().getId()))
                && (associatedService.getPrintSide() == null || requestDto.getPrintSideIds() == null ||
                requestDto.getPrintSideIds().size() == 0 || requestDto.getPrintSideIds().contains(associatedService.getPrintSide().getId()))
                && (associatedService.getPrintType() == null || requestDto.getPrintTypeIds() == null ||
                requestDto.getPrintTypeIds().size() == 0 || requestDto.getPrintTypeIds().contains(associatedService.getPrintType().getId()))
                && (associatedService.getBindingType() == null || requestDto.getBindingTypeIds() == null ||
                requestDto.getBindingTypeIds().size() == 0 || requestDto.getBindingTypeIds().contains(associatedService.getBindingType().getId()));
    }
}
