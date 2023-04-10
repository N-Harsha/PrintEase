package com.printease.application.repository;


import com.printease.application.model.PrintService;
import org.springframework.data.repository.CrudRepository;

public interface PrintServiceRepository extends CrudRepository<PrintService,Long> {
}
