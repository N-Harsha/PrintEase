package com.printease.application.controller;

import com.printease.application.dto.AssociatedServiceDto;
import com.printease.application.dto.MessageWrapperDto;
import com.printease.application.dto.RecommendAssociatedServiceRequestDto;
import com.printease.application.dto.RecommendAssociatedServiceResponseDto;
import com.printease.application.service.ServiceOfAssociatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/associatedServices")
@RequiredArgsConstructor
public class AssociatedServiceController {
    private final ServiceOfAssociatedService serviceOfAssociatedService;
    @PostMapping
    @PreAuthorize("hasRole('ROLE_SERVICE_PROVIDER')")
    public ResponseEntity<MessageWrapperDto> addAssociatedService(Principal principal, @Valid @RequestBody AssociatedServiceDto associatedServiceDto){
        return serviceOfAssociatedService.addAssociatedService(principal.getName(), associatedServiceDto);
    }
    @GetMapping
    @PreAuthorize("hasRole('ROLE_SERVICE_PROVIDER')")
    //printServiceId is required
    public ResponseEntity<List<AssociatedServiceDto>> getAssociatedServices(Principal principal,@RequestParam Long printServiceId){
        return serviceOfAssociatedService.getAssociatedServices(principal.getName(),printServiceId);
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_SERVICE_PROVIDER')")
    public ResponseEntity<MessageWrapperDto> updateAssociatedService(Principal principal, @Valid @RequestBody AssociatedServiceDto associatedServiceDto){
        return serviceOfAssociatedService.updateAssociatedService(principal.getName(), associatedServiceDto);
    }

    @DeleteMapping("/{associatedServiceId}")
    @PreAuthorize("hasRole('ROLE_SERVICE_PROVIDER')")
    public ResponseEntity<MessageWrapperDto> deleteAssociatedService(Principal principal, @PathVariable Long associatedServiceId){
        return serviceOfAssociatedService.deleteAssociatedService(principal.getName(), associatedServiceId);
    }

    @GetMapping("/recommended")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<RecommendAssociatedServiceResponseDto>> recommendAssociatedServices(
            Principal principal,
            @RequestParam Long printServiceId,
            @RequestParam Double longitude,
            @RequestParam Double latitude,
            @RequestParam(required = false) List<Long> orientationIds,
            @RequestParam(required = false) List<Long> paperSizeIds,
            @RequestParam(required = false) List<Long> paperTypeIds,
            @RequestParam(required = false) List<Long> printSideIds,
            @RequestParam(required = false) List<Long> printTypeIds,
            @RequestParam(required = false) List<Long> bindingTypeIds
            ){
        return serviceOfAssociatedService.recommendAssociatedService(principal.getName(), RecommendAssociatedServiceRequestDto.builder()
                .printServiceId(printServiceId)
                .longitude(longitude)
                .latitude(latitude)
                .orientationIds(orientationIds)
                .paperSizeIds(paperSizeIds)
                .paperTypeIds(paperTypeIds)
                .printSideIds(printSideIds)
                .printTypeIds(printTypeIds)
                .bindingTypeIds(bindingTypeIds)
                .build());
    }
}
