package com.printease.application.dto;

import com.printease.application.model.OrderStatusLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private static final long serialVersionUID = 1L;
    private Long id;
    private AssociatedServiceDto associatedService;
    private String serviceName;
    private String comment;
    private LocalDateTime dueDate;
    private LocalDateTime createdOn;
    private String orderStatus;
    private Integer quantity;
    private Float price;
    private String fileDownloadableUrl;
    private List<OrderStatusLog> orderStatusLogList;
}
