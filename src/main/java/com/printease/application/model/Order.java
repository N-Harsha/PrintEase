package com.printease.application.model;

import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(cascade = CascadeType.ALL)
    List<OrderStatusLog> orderStatusLogList;
    private LocalDateTime createdOn;
    private LocalDateTime dueDate;
    private String comment;
    private Integer quantity;
    private Float price;
}
