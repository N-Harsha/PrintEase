package com.printease.application.repository;

import com.printease.application.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface AssociatedServiceRepository extends CrudRepository<AssociatedService,Long> {
    List<AssociatedService> findAllByServiceProvider(ServiceProvider serviceProvider);
    List<AssociatedService> findAllByServiceProviderAndService(ServiceProvider serviceProvider, PrintService service);
    Boolean existsByIdAndServiceProvider(Long id, ServiceProvider serviceProvider);

}
