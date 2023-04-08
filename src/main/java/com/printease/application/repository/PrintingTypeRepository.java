package com.printease.application.repository;

import com.printease.application.model.PrintingType;
import org.springframework.data.repository.CrudRepository;

public interface PrintingTypeRepository extends CrudRepository<PrintingType,Long> {
}
