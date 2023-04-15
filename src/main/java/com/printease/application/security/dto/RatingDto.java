package com.printease.application.security.dto;

import com.printease.application.model.AssociatedService;
import com.printease.application.model.Customer;
import com.printease.application.model.PrintService;
import com.printease.application.model.ServiceProvider;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto {
    private Long id;
    private Long rating;
    private String comment;
    private String customerName;
}
