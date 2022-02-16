package com.fossee.csvmanager.service;

import com.fossee.csvmanager.model.CsvDocument;
import com.fossee.csvmanager.repository.CsvRepository;
import com.fossee.csvmanager.util.CsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CsvService {

    @Autowired CsvRepository csvRepository;

    public void saveDocument(MultipartFile file) throws RuntimeException{
        List<CsvDocument> documentList = CsvManager.parseCsvToDocument(file);
        csvRepository.saveAllAndFlush(documentList);
    }


    public List<String> getDocumentIds() {
        return csvRepository.findAll().
                stream().
                map((CsvDocument document) -> {
            return document.getId().toString();
        }).collect(Collectors.toList());
    }

}
