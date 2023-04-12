package com.printease.application.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Customer customer;
    @OneToOne
    private AssociatedService associatedService;
    @OneToOne
    private OrderStatus orderStatus;
    @OneToOne
    private FileDB file;
    private LocalDateTime createdOn;
    private LocalDateTime dueDate;
    private String comment;
    private Integer quantity;
    private Float price;
}
