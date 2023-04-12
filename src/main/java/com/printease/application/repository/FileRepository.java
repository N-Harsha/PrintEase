package com.printease.application.repository;

import com.printease.application.model.FileDB;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<FileDB,Long> {
}
