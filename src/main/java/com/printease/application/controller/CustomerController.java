package com.printease.application.controller;

import com.printease.application.dto.MessageWrapperDto;
import com.printease.application.dto.ServiceProviderDto;
import com.printease.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<ServiceProviderDto>> getFavoriteServiceProvider(Principal principal){
        return customerService.getFavoriteServiceProvider(principal.getName());
    }
    @PostMapping("favorite/{serviceProviderId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<MessageWrapperDto> addFavoriteServiceProvider(Principal principal, @PathVariable Long serviceProviderId){
        return customerService.addFavoriteServiceProvider(principal.getName(), serviceProviderId);
    }

    @DeleteMapping("favorite/{serviceProviderId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<MessageWrapperDto> deleteFavoriteServiceProvider(Principal principal,@PathVariable Long serviceProviderId){
        return customerService.deleteFavoriteServiceProvider(principal.getName(), serviceProviderId);
    }


}
