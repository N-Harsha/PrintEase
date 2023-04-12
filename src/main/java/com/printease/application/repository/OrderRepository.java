package com.printease.application.repository;

import com.printease.application.model.Order;
import org.springframework.data.repository.CrudRepository;
public interface OrderRepository extends CrudRepository<Order,Long> {

}
