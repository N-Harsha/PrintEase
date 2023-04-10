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
    public ResponseEntity<String> addAssociatedService(Principal principal,@Valid @RequestBody AssociatedServiceDto associatedServiceDto){
        return serviceOfAssociatedService.addAssociatedService(principal.getName(), associatedServiceDto);
    }
    @GetMapping
    public ResponseEntity<List<AssociatedService>> getAssociatedServices(Principal principal, @RequestParam(required = false) Long printServiceId){
        return serviceOfAssociatedService.getAssociatedServices(principal.getName(),printServiceId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAssociatedService(Principal principal, @PathVariable Long id, @Valid @RequestBody AssociatedServiceDto associatedServiceDto){
        return serviceOfAssociatedService.updateAssociatedService(principal.getName(), id, associatedServiceDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAssociatedService(Principal principal, @PathVariable Long id){
        return serviceOfAssociatedService.deleteAssociatedService(principal.getName(), id);
    }
}
