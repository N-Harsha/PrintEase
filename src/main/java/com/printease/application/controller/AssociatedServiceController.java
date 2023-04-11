package com.printease.application.controller;

import com.printease.application.model.AssociatedService;
import com.printease.application.security.dto.AssociatedServiceDto;
import com.printease.application.service.ServiceOfAssociatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addAssociatedService(Principal principal,@Valid @RequestBody AssociatedServiceDto associatedServiceDto, @RequestParam Long printServiceId){
        return serviceOfAssociatedService.addAssociatedService(principal.getName(), associatedServiceDto,printServiceId);
    }
    @GetMapping
    //printServiceId is required
    public ResponseEntity<List<AssociatedServiceDto>> getAssociatedServices(Principal principal,@RequestParam Long printServiceId){
        return serviceOfAssociatedService.getAssociatedServices(principal.getName(),printServiceId);
    }

    @PutMapping("/{associatedServiceId}")
    public ResponseEntity<String> updateAssociatedService(Principal principal, @PathVariable Long associatedServiceId, @Valid @RequestBody AssociatedServiceDto associatedServiceDto){
        return serviceOfAssociatedService.updateAssociatedService(principal.getName(), associatedServiceId, associatedServiceDto);
    }

    @DeleteMapping("/{associatedServiceId}")
    public ResponseEntity<String> deleteAssociatedService(Principal principal, @PathVariable Long associatedServiceId){
        return serviceOfAssociatedService.deleteAssociatedService(principal.getName(), associatedServiceId);
    }
}
