package com.fossee.csvmanager.repository;


import com.fossee.csvmanager.model.CsvFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvFileRepository extends JpaRepository<CsvFile, String> {
}
