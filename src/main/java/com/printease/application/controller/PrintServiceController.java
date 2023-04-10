package com.printease.application.controller;

import com.printease.application.model.PrintService;
import com.printease.application.security.dto.PrintServiceDto;
import com.printease.application.service.ServiceOfPrintService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class PrintServiceController {
    private final ServiceOfPrintService serviceOfPrintService;

    @GetMapping
    public ResponseEntity<List<PrintServiceDto>> getServices(Principal principal){
        return ResponseEntity.ok(serviceOfPrintService.getAllServices(principal));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrintService> getService(@PathVariable Long id, Principal principal) {
        return ResponseEntity.ok(serviceOfPrintService.getService(id));
    }
}
