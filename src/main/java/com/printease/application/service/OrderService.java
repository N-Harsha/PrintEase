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

    public ResponseEntity<?> getOrderByIdResponseEntity(String email, Long id) {
        User user = userService.findByEmail(email);
        log.info("fetched user with email: {}", email);
        Order order = getOrderById(id);
        log.info("fetched order with id: {}", id);
        if (checkIfOrderIsAssociatedWithUser(order, email)) {
            throw new CustomException(
                    new ApiExceptionResponse(
                            exceptionMessageAccessor.getMessage(null,
                                    ProjectConstants.USER_NOT_AUTHORIZED_FOR_STATUS_UPDATE),
                            HttpStatus.FORBIDDEN, LocalDateTime.now()));
        }
        if (user.getUserRole().getRole().equalsIgnoreCase(ProjectConstants.ROLE_CUSTOMER)) {
            return ResponseEntity.ok(OrderMapper.INSTANCE.convertToOrderDtoCustomer(order));
        } else if (user.getUserRole().getRole().equalsIgnoreCase(ProjectConstants.ROLE_SERVICE_PROVIDER)) {
            return ResponseEntity.ok(OrderMapper.INSTANCE.convertToOrderDtoServiceProvider(order));
        } else {
            log.error("Invalid user role");
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor
                    .getMessage(null, ProjectConstants.INVALID_USER_ROLE), HttpStatus.FORBIDDEN, LocalDateTime.now()));
        }
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


    public boolean checkIfOrderIsAssociatedWithUser(Order order, String email) {
        return !order.getCustomer().getEmail().equalsIgnoreCase(email) && !order.getAssociatedService().getServiceProvider().getEmail().equalsIgnoreCase(email);
    }

    @Transactional
    public ResponseEntity<?> cancelOrder(String email, Long orderId, String comment) {
        User user = userService.findByEmail(email);
        log.info("fetched user with email: {}", email);
        Order order = getOrderById(orderId);
        log.info("fetched order with id: {}", orderId);
        if (checkIfOrderIsAssociatedWithUser(order, email)) {
            throw new CustomException(
                    new ApiExceptionResponse(
                            exceptionMessageAccessor.getMessage(null,
                                    ProjectConstants.USER_NOT_AUTHORIZED_FOR_STATUS_UPDATE),
                            HttpStatus.FORBIDDEN, LocalDateTime.now()
                    )
            );
        }
        if (user.getUserRole().getRole().equalsIgnoreCase(ProjectConstants.ROLE_CUSTOMER)) {
            if (order.getOrderStatus().getStatus().equals(ProjectConstants.PENDING_ORDER_STATUS) ||
                    order.getOrderStatus().getStatus().equals(ProjectConstants.ACCEPTED_ORDER_STATUS)) {
                order.setOrderStatus(orderStatusService.getOrderStatusByStatus(ProjectConstants.CANCELLED_ORDER_STATUS));
                order.getOrderStatusLogList().add(OrderStatusLog.builder()
                        .updatedBy(user.getUserRole().getRole())
                        .orderStatus(order.getOrderStatus())
                        .updatedOn(LocalDateTime.now())
                        .comment(comment)
                        .build());
                log.info("Order cancelled successfully");
                return ResponseEntity.ok(generalMessageAccessor.getMessage(null, ProjectConstants.ORDER_STATUS_UPDATED, order.getOrderStatus().getStatus()));
            } else {
                log.error("Order cannot be cancelled");
                throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor
                        .getMessage(null, ProjectConstants.ORDER_CANNOT_BE_CANCELLED, order.getOrderStatus().getStatus()), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
            }

        } else if (user.getUserRole().getRole().equalsIgnoreCase(ProjectConstants.ROLE_SERVICE_PROVIDER)) {

            if (order.getOrderStatus().getStatus().equals(ProjectConstants.PENDING_ORDER_STATUS)) {
                order.setOrderStatus(orderStatusService.getOrderStatusByStatus(ProjectConstants.REJECTED_ORDER_STATUS));
                order.getOrderStatusLogList().add(OrderStatusLog.builder()
                        .updatedBy(user.getUserRole().getRole())
                        .orderStatus(order.getOrderStatus())
                        .comment(comment)
                        .updatedOn(LocalDateTime.now())
                        .build());
                log.info("Order rejected successfully");
                return ResponseEntity.ok(generalMessageAccessor.getMessage(null, ProjectConstants.ORDER_STATUS_UPDATED, order.getOrderStatus().getStatus()));
            } else {
                if (order.getOrderStatus().getStatus().equals(ProjectConstants.COMPLETED_ORDER_STATUS)) {
                    log.error("Order cannot be cancelled");
                    throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor
                            .getMessage(null, ProjectConstants.ORDER_CANNOT_BE_CANCELLED, order.getOrderStatus()), HttpStatus.BAD_REQUEST, LocalDateTime.now()));
                }
                order.setOrderStatus(orderStatusService.getOrderStatusByStatus(ProjectConstants.CANCELLED_ORDER_STATUS));
                order.getOrderStatusLogList().add(OrderStatusLog.builder()
                        .updatedBy(user.getUserRole().getRole())
                        .orderStatus(order.getOrderStatus())
                        .comment(comment)
                        .updatedOn(LocalDateTime.now())
                        .build());
                log.info("Order cancelled successfully");
                return ResponseEntity.ok(generalMessageAccessor
                        .getMessage(null, ProjectConstants.ORDER_STATUS_UPDATED, order.getOrderStatus().getStatus()));
            }

        } else {
            log.error("Invalid user role");
            throw new CustomException(new ApiExceptionResponse(exceptionMessageAccessor
                    .getMessage(null, ProjectConstants.INVALID_USER_ROLE), HttpStatus.FORBIDDEN, LocalDateTime.now()));
        }
    }

    private Order getOrderById(Long orderId) {
        log.info("fetched order with id: {}", orderId);
        return orderRepository.findById(orderId).orElseThrow(() -> new CustomException(new ApiExceptionResponse(exceptionMessageAccessor
                .getMessage(null, ProjectConstants.ORDER_NOT_FOUND), HttpStatus.BAD_REQUEST, LocalDateTime.now())));
    }

    @Transactional
    public ResponseEntity<?> promoteOrder(String email, Long orderId, String comment) {
        Order order = getOrderById(orderId);
        log.info("fetched order with id: {}", orderId);
        if (!order.getAssociatedService().getServiceProvider().getEmail().equalsIgnoreCase(email)) {
            throw new CustomException(
                    new ApiExceptionResponse(
                            exceptionMessageAccessor.getMessage(null,
                                    ProjectConstants.USER_NOT_AUTHORIZED_FOR_STATUS_UPDATE),
                            HttpStatus.FORBIDDEN, LocalDateTime.now()
                    )
            );
        }
        User user = userService.findByEmail(email);
        OrderStatus nextOrderStatus = getNextOrderStatus(order);
        log.info("fetched next order status: {}", nextOrderStatus.getStatus());
        order.setOrderStatus(nextOrderStatus);
        order.getOrderStatusLogList().add(OrderStatusLog.builder()
                .updatedBy(user.getUserRole().getRole())
                .orderStatus(order.getOrderStatus())
                .comment(comment)
                .updatedOn(LocalDateTime.now())
                .build());
        log.info("Order promoted successfully");
        return ResponseEntity.ok(generalMessageAccessor.getMessage(null,
                ProjectConstants.ORDER_STATUS_UPDATED, order.getOrderStatus().getStatus(), order.getOrderStatus().getStatus()));
    }

    private OrderStatus getNextOrderStatus(Order order) {
        List<String> orderStatusArray = ProjectConstants.ORDER_STATUS_LIST;
        int index = orderStatusArray.indexOf(order.getOrderStatus().getStatus());
        log.info("index of current order status: {}", index);
        if (index == -1) {
            throw new CustomException(
                    new ApiExceptionResponse(
                            exceptionMessageAccessor.getMessage(null,
                                    ProjectConstants.ORDER_STATUS_CANNOT_BE_UPDATED),
                            HttpStatus.BAD_REQUEST, LocalDateTime.now()
                    )
            );
        }
        if (index == orderStatusArray.size() - 1) {
            throw new CustomException(
                    new ApiExceptionResponse(
                            exceptionMessageAccessor.getMessage(null,
                                    ProjectConstants.ORDER_STATUS_IS_ALREADY_COMPLETED),
                            HttpStatus.BAD_REQUEST, LocalDateTime.now()
                    )
            );
        }
        log.info("fetched next order status: {}", orderStatusArray.get(index + 1));
        return orderStatusService.getOrderStatusByStatus(orderStatusArray.get(index + 1));
    }
}