package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.PrintService;
import com.printease.application.repository.PrintServiceRepository;
import com.printease.application.security.dto.PrintServiceDto;
import com.printease.application.security.mapper.PrintServiceMapper;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceOfPrintService {
    private final PrintServiceRepository printServiceRepository;
    private final ExceptionMessageAccessor exceptionMessageAccessor;
    private final String SERVICE_NOT_FOUND = "service_not_found";

    public List<PrintServiceDto> getAllServices(Principal principal) {
        //add user authorization logic here.
        List<PrintService> printServices = (List<PrintService>) printServiceRepository.findAll();
        log.info("fetched {} services,by user with email {}", printServices.size(), principal.getName());
        return printServices.stream()
                .map(PrintServiceMapper.INSTANCE::convertToPrintServiceDto)
                .collect(Collectors.toList());
    }

    //this method is only needed by the service provider
    public PrintService getService(Long id) {
        PrintService printService = printServiceRepository.findById(id)
                .orElseThrow(() -> new CustomException(new ApiExceptionResponse(exceptionMessageAccessor
                        .getMessage(null, SERVICE_NOT_FOUND, id), HttpStatus.BAD_REQUEST, LocalDateTime.now())));
        log.info("fetched service with id {}", id);
        return printService;
    }

}
