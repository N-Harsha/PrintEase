package com.printease.application.dto;

import com.printease.application.model.AssociatedService;
import com.printease.application.model.Customer;
import com.printease.application.model.PrintService;
import com.printease.application.model.ServiceProvider;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto {
    private Long id;
    private Float rating;
    private String comment;
    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
