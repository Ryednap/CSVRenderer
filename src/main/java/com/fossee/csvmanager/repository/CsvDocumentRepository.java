package com.fossee.csvmanager.repository;

import com.fossee.csvmanager.model.CsvDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvDocumentRepository extends JpaRepository<CsvDocument, Long> {
}
