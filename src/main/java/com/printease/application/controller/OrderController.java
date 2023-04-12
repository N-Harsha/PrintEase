package com.printease.application.controller;


import com.printease.application.security.dto.OrderCreationRequestDto;
import com.printease.application.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> createOrder(Principal principal,
                                         @ModelAttribute OrderCreationRequestDto orderCreationRequestDto){
        return orderService.createOrder(principal.getName(), orderCreationRequestDto);
    }

}
