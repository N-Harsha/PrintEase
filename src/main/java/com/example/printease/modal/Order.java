package com.example.printease.modal;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  private ServiceProvider serviceProvider;
  @ManyToOne
  private Customer customer;
  @ManyToOne
  private OrderStatus orderStatus;
  @OneToMany
  private List<AssociatedService> associatedServices;
  private LocalDateTime orderDate;
}
