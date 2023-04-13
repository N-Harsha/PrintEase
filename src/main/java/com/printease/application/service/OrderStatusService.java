package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.OrderStatus;
import com.printease.application.repository.OrderStatusRepository;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderStatusService {
    private static final String ORDER_STATUS_NOT_FOUND = "order_status_not_found";
    private final OrderStatusRepository orderStatusRepository;
    ExceptionMessageAccessor exceptionMessageAccessor;
    OrderStatus getOrderStatusByStatus(String status){
        return orderStatusRepository.findByStatus(status).orElseThrow(()->
                new CustomException(new ApiExceptionResponse(
                        exceptionMessageAccessor.getMessage(null,
                                ORDER_STATUS_NOT_FOUND,ProjectConstants.PENDING_ORDER_STATUS), HttpStatus.NOT_FOUND, LocalDateTime.now())));

    }
}
