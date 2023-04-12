package com.printease.application.repository;

import com.printease.application.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderStatusRepository extends CrudRepository<OrderStatus,Long> {
    public Optional<OrderStatus> findByStatus(String status);
}
