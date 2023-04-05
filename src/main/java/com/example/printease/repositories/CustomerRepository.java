package com.example.printease.repositories;

import com.example.printease.modals.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer,Long> {
}
