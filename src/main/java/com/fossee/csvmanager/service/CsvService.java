/**
 * @author Ujjwal Pandey
 * @version 1.2
 * @since 02-10-2022
 */

package com.fossee.csvmanager.service;

import com.fossee.csvmanager.model.CsvDocument;
import com.fossee.csvmanager.model.CsvFile;
import com.fossee.csvmanager.repository.CsvDocumentRepository;
import com.fossee.csvmanager.repository.CsvFileRepository;
import com.fossee.csvmanager.util.CsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Layer Class to perform business logic for managing csv file and form data received from Controller layer
 */
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

    public boolean isCsvFile(MultipartFile file) {
        return CsvManager.isCsvFile(file);
    }

    /**
     * This method saves the csv file recived from controller. The file is first parsed as List of {@link CsvDocument} by
     * {@link CsvManager} and then we save the content of the documentList to {"document"} table using {@link #csvDocumentRepository} and
     * the name of the file, and it's respective list of documents using {@link #csvFileRepository}
     * @see CsvManager
     *
     * @param file Multipart file containing CSV data from HTML form
     * @throws RuntimeException {@link CsvManager} throws this exception and this method rethrows that
     */
    public void saveDocument(MultipartFile file) throws RuntimeException{
        // debug
        System.out.println("Got File " + file.getContentType());

        // get document list from csvManager
        List<CsvDocument> documentList = CsvManager.parseCsvToDocument(file);

        // save all the individual csvDocument of the list and flush the content
        csvDocumentRepository.saveAll(documentList);

        // create and save the CsvFile with respective file name, and it's document list as content
        CsvFile csvFile = new CsvFile(file.getOriginalFilename(), documentList);
        csvFileRepository.save(csvFile);
    }


    /**
     * This method gets the list of names of all csv file present in database ("csvFile") whose entity representation is
     * {@link CsvFile}.
     *
     * @return list of filenames stored in {@link CsvFile} Data Structure
     * @see CsvFile
     */
    public List<String> getFileNames() {
        return csvFileRepository.findAll()
                .stream().map(CsvFile::getName)
                .collect(Collectors.toList());
    }


    /**
     * This method gets the content of CSV file stored in database by #fileName. Each {@link CsvFile} in the database
     * contains list of {@link CsvDocument} which defines each row of the CSV file and this method exactly retrieves those documents
     *
     * @param fileName the name of the file whose document list needs to be retrieved
     * @return List of {@link CsvDocument} representing a complete CSV file
     */
    public List<CsvDocument> getCsvDocument(String fileName) {

        // find all the documents having id as filename and then extract only documentList of that file and then transform it to list
        List<List<CsvDocument>> documentList = csvFileRepository
                .findById(fileName).stream()
                .map(CsvFile::getDocumentList)
                .collect(Collectors.toList());
        return documentList.get(0);
    }
}
