package com.printease.application.service;

import com.printease.application.exceptions.ApiExceptionResponse;
import com.printease.application.exceptions.CustomException;
import com.printease.application.model.*;
import com.printease.application.repository.OrderRepository;
import com.printease.application.security.dto.OrderCreationRequestDto;
import com.printease.application.security.dto.OrderDtoCustomer;
import com.printease.application.security.dto.OrderDtoServiceProvider;
import com.printease.application.security.mapper.OrderMapper;
import com.printease.application.security.service.UserService;
import com.printease.application.utils.ExceptionMessageAccessor;
import com.printease.application.utils.GeneralMessageAccessor;
import com.printease.application.utils.ProjectConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    private final CustomerService customerService;
    private final ServiceOfServiceProvider serviceOfServiceProvider;
    private final OrderStatusService orderStatusService;
    private final FileService fileService;
    private final ServiceOfAssociatedService serviceOfAssociatedService;
    private final UserService userService;

    private final ExceptionMessageAccessor exceptionMessageAccessor;
    private final GeneralMessageAccessor generalMessageAccessor;


    @Transactional
    public ResponseEntity<?> createOrder(String email, OrderCreationRequestDto orderCreationRequestDto) {
        Customer customer = customerService.findCustomerByUserEmail(email);
        log.info("fetched customer with email: {}", email);
        AssociatedService associatedService = serviceOfAssociatedService.findAssociatedServiceById(orderCreationRequestDto.getAssociatedServiceId());
        log.info("fetched associated service with id: {}", orderCreationRequestDto.getAssociatedServiceId());
        OrderStatus orderStatus = orderStatusService.getOrderStatusByStatus(ProjectConstants.PENDING_ORDER_STATUS);
        log.info("fetched order status with status: {}", ProjectConstants.PENDING_ORDER_STATUS);
        FileDB savedFile;
        try {
            savedFile = fileService.saveCompressedFileToDatabase(orderCreationRequestDto.getFile());
        } catch (Exception e) {
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null, ProjectConstants.FILE_UPLOAD_ERROR), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now()));
        }
        LocalDateTime orderCreationDateTime = LocalDateTime.now();
        LocalDateTime orderDueDate = orderCreationRequestDto.getDueDate();
        if (orderDueDate.isBefore(orderCreationDateTime)) {
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor.getMessage(null, ProjectConstants.INVALID_DUE_DATE), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
        }
        Integer quantity = orderCreationRequestDto.getQuantity();
        Float price = associatedService.getPrice() * quantity;
        Order order = Order.builder()
                .customer(customer)
                .associatedService(associatedService)
                .orderStatus(orderStatus)
                .createdOn(orderCreationDateTime)
                .dueDate(orderDueDate)
                .comment(orderCreationRequestDto.getComment())
                .quantity(quantity)
                .price(price)
                .file(savedFile)
                .build();
        orderRepository.save(order);
        log.info("Order created successfully");
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null, ProjectConstants.ORDER_CREATED_SUCCESSFULLY));
    }

    public ResponseEntity<List<?>> getAllOrders(String email) {
        User user = userService.findByEmail(email);
        log.info("fetched user with email: {}", email);
        List<?> orders;
        if (user.getUserRole().getRole().equalsIgnoreCase(ProjectConstants.ROLE_CUSTOMER)) {
            Customer customer = customerService.findCustomerByUserEmail(email);
            log.info("fetched customer with email: {}", email);
            orders = getAllOrdersOfCustomer(customer);
        } else if (user.getUserRole().getRole().equalsIgnoreCase(ProjectConstants.ROLE_SERVICE_PROVIDER)) {
            ServiceProvider serviceProvider = serviceOfServiceProvider.findServiceProviderByUserEmail(email);
            log.info("fetched service provider with email: {}", email);
            orders = getAllOrdersOfServiceProvider(serviceProvider);
        } else {
            log.error("Invalid user role");
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor
                    .getMessage(null, ProjectConstants.INVALID_USER_ROLE), HttpStatus.FORBIDDEN, LocalDateTime.now()));
        }
        return ResponseEntity.ok(orders);
    }

    private List<OrderDtoCustomer> getAllOrdersOfCustomer(Customer customer) {
        log.info("fetched all orders of customer with id: {}", customer.getId());
        return orderRepository.findAllByCustomer(customer)
                .stream().map(OrderMapper.INSTANCE::convertToOrderDtoCustomer)
                .collect(Collectors.toList());
    }

    private List<OrderDtoServiceProvider> getAllOrdersOfServiceProvider(ServiceProvider serviceProvider) {
        log.info("fetched all orders of service provider with id: {}", serviceProvider.getId());
        return orderRepository.findAllByServiceProviderId(serviceProvider.getId())
                .stream().map(OrderMapper.INSTANCE::convertToOrderDtoServiceProvider)
                .collect(Collectors.toList());
    }

    public Order getOrderByFileId(Long id) {
        log.info("fetched order with file id: {}", id);
        return orderRepository.findByFileId(id).orElseThrow(() -> new CustomException(new ApiExceptionResponse(exceptionMessageAccessor
                .getMessage(null, ProjectConstants.ORDER_NOT_FOUND), HttpStatus.NOT_FOUND, LocalDateTime.now())));
    }
}