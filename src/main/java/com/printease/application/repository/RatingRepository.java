package com.printease.application.repository;

import com.printease.application.model.Customer;
import com.printease.application.model.Rating;
import com.printease.application.model.ServiceProvider;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating,Long> {
    public Boolean existsByCustomerAndAndServiceProvider(Customer customer, ServiceProvider serviceProvider);
}
