package com.printease.application.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/orders")
public class OrderController {
    @GetMapping
    public ResponseEntity<?> getAllOrders(){
        return ResponseEntity.ok().build();
    }
}
