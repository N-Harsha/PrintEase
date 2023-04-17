package com.printease.application.controller;


import com.printease.application.security.dto.OrderCreationRequestDto;
import com.printease.application.security.dto.StatusUpdateDto;
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
    public ResponseEntity<?> getAllOrders(Principal principal){
        return orderService.getAllOrders(principal.getName());
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_SERVICE_PROVIDER')")
    public ResponseEntity<?> getOrderById(Principal principal, @PathVariable Long orderId){
        return orderService.getOrderByIdResponseEntity(principal.getName(), orderId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createOrder(Principal principal,
                                         @ModelAttribute OrderCreationRequestDto orderCreationRequestDto){
        return orderService.createOrder(principal.getName(), orderCreationRequestDto);
    }

    @PutMapping("/{orderId}/cancel")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER','ROLE_SERVICE_PROVIDER')")
    public ResponseEntity<?> cancelOrder(Principal principal,
                                         @PathVariable Long orderId,
                                         @RequestBody StatusUpdateDto statusUpdateDto){
        return orderService.cancelOrder(principal.getName(), orderId,statusUpdateDto.getComment());
    }
    @PutMapping("/{orderId}/promote")
    @PreAuthorize("hasRole('ROLE_SERVICE_PROVIDER')")
    public ResponseEntity<?> promoteOrder(Principal principal,
                                          @PathVariable Long orderId,
                                          @RequestBody StatusUpdateDto statusUpdateDto){
        return orderService.promoteOrder(principal.getName(), orderId,statusUpdateDto.getComment());
    }


}
