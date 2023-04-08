package com.printease.application.repository;

import com.printease.application.model.PaperType;
import org.springframework.data.repository.CrudRepository;

public interface PaperTypeRepository extends CrudRepository<PaperType,Long> {
}
