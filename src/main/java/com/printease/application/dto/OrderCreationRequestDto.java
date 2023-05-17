package com.printease.application.dto;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreationRequestDto {
    private static final long serialVersionUID = 1L;
    private Long associatedServiceId;
    private String comment;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Due date cannot be empty")
    private LocalDateTime dueDate;
    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;
    private MultipartFile file;
}
