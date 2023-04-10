package com.printease.application.repository;

import com.printease.application.model.PrintType;
import org.springframework.data.repository.CrudRepository;

public interface PrintTypeRepository extends CrudRepository<PrintType,Long> {
}
