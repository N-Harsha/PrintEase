package com.printease.application.controller;

import com.printease.application.model.ServiceProvider;
import com.printease.application.security.dto.MessageWrapperDto;
import com.printease.application.security.dto.ServiceProviderDto;
import com.printease.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/customers/")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @GetMapping
    public ResponseEntity<List<ServiceProviderDto>> getFavoriteServiceProvider(Principal principal){
        return customerService.getFavoriteServiceProvider(principal.getName());
    }
    @PostMapping("favorite/{serviceProviderId}")
    public ResponseEntity<MessageWrapperDto> addFavoriteServiceProvider(Principal principal, @PathVariable Long serviceProviderId){
        return customerService.addFavoriteServiceProvider(principal.getName(), serviceProviderId);
    }

    @DeleteMapping("favorite/{serviceProviderId}")
    public ResponseEntity<MessageWrapperDto> deleteFavoriteServiceProvider(Principal principal,@PathVariable Long serviceProviderId){
        return customerService.deleteFavoriteServiceProvider(principal.getName(), serviceProviderId);
    }


}
