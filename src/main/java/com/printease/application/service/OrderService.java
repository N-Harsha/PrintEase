package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.*;
import com.printease.application.repository.OrderRepository;
import com.printease.application.security.dto.OrderCreationRequestDto;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    private final CustomerService customerService;
    private final OrderStatusService orderStatusService;
    private final FileService fileService;

    private final ServiceOfAssociatedService serviceOfAssociatedService;
    private final ExceptionMessageAccessor exceptionMessageAccessor;
    private final GeneralMessageAccessor generalMessageAccessor;


    @Transactional
    public ResponseEntity<?> createOrder(String email, OrderCreationRequestDto orderCreationRequestDto){
        Customer customer = customerService.findCustomerByUserEmail(email);
        log.info("fetched customer with email: {}",email);
        AssociatedService associatedService = serviceOfAssociatedService.findAssociatedServiceById(orderCreationRequestDto.getAssociatedServiceId());
        log.info("fetched associated service with id: {}",orderCreationRequestDto.getAssociatedServiceId());
        OrderStatus orderStatus = orderStatusService.getOrderStatusByStatus(ProjectConstants.PENDING_ORDER_STATUS);
        log.info("fetched order status with status: {}",ProjectConstants.PENDING_ORDER_STATUS);
        FileDB savedFile;
        try {
            savedFile = fileService.saveCompressedFileToDatabase(orderCreationRequestDto.getFile());
        }
        catch (Exception e){
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,ProjectConstants.FILE_UPLOAD_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()));
        }
        LocalDateTime orderCreatedOn = LocalDateTime.now();
        LocalDateTime orderDueDate = orderCreationRequestDto.getDueDate();
        if(orderDueDate.isBefore(orderCreatedOn)){
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null,ProjectConstants.INVALID_DUE_DATE), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        Integer quantity = orderCreationRequestDto.getQuantity();
        Float price = associatedService.getPrice()*quantity;

        Order order = Order.builder()
                .customer(customer)
                .associatedService(associatedService)
                .orderStatus(orderStatus)
                .orderCreatedOn(orderCreatedOn)
                .dueDate(orderDueDate)
                .comment(orderCreationRequestDto.getComment())
                .quantity(quantity)
                .price(price)
                .file(savedFile)
                .build();

        orderRepository.save(order);
        log.info("Order created successfully");
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null,ProjectConstants.ORDER_CREATED_SUCCESSFULLY));
    }
}