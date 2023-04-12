package com.printease.application.controller;


import com.printease.application.security.dto.OrderCreationRequestDto;
import com.printease.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_SERVICE_PROVIDER')")
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createOrder(Principal principal,
                                         @ModelAttribute OrderCreationRequestDto orderCreationRequestDto){
        return orderService.createOrder(principal.getName(), orderCreationRequestDto);
    }

}
