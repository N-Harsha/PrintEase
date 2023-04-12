package com.printease.application.repository;

import com.printease.application.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;

public interface OrderStatusRepository extends CrudRepository<OrderStatus,Long> {

}
