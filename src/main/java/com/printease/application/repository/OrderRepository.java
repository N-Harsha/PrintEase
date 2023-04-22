package com.printease.application.repository;

import com.printease.application.model.Customer;
import com.printease.application.model.Order;
import com.printease.application.model.ServiceProvider;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByCustomerOrderByCreatedOnDesc(Customer customer);

    @Query("SELECT o from Order o WHERE o.associatedService.serviceProvider.id = ?1 order by o.createdOn desc ")
    List<Order> findAllByServiceProviderId(Long serviceProviderId);

    @Query("SELECT o FROM Order o WHERE o.file.id = ?1")
    Optional<Order> findByFileId(Long id);
}
