package com.fossee.csvmanager.service;

import com.fossee.csvmanager.model.CsvDocument;
import com.fossee.csvmanager.model.CsvFile;
import com.fossee.csvmanager.repository.CsvDocumentRepository;
import com.fossee.csvmanager.repository.CsvFileRepository;
import com.fossee.csvmanager.util.CsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvService {

    private final CsvDocumentRepository csvDocumentRepository;
    private final CsvFileRepository csvFileRepository;

    @Autowired
    public CsvService(CsvDocumentRepository csvDocumentRepository,
                      CsvFileRepository csvFileRepository) {
        this.csvDocumentRepository = csvDocumentRepository;
        this.csvFileRepository = csvFileRepository;
    }

    public void saveDocument(MultipartFile file) throws RuntimeException{
        System.out.println("Got File " + file.getContentType());
        List<CsvDocument> documentList = CsvManager.parseCsvToDocument(file);
        csvDocumentRepository.saveAllAndFlush(documentList);
        CsvFile csvFile = new CsvFile(file.getOriginalFilename(), documentList);
        csvFileRepository.save(csvFile);
    }


    public List<String> getFileNames() {
        return csvFileRepository.findAll()
                .stream().map(CsvFile::getName)
                .collect(Collectors.toList());
    }

    public List<CsvDocument> getCsvDocument(String fileName) {
        List<List<CsvDocument>> documentList = csvFileRepository
                .findById(fileName).stream()
                .map(CsvFile::getDocumentList)
                .collect(Collectors.toList());
        return documentList.get(0);
    }
}
