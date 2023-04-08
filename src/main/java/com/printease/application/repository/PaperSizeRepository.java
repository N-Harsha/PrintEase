package com.printease.application.repository;

import com.printease.application.model.PaperSize;
import org.springframework.data.repository.CrudRepository;

public interface PaperSizeRepository extends CrudRepository<PaperSize,Long> {
}
