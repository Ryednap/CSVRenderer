package com.fossee.csvmanager.util;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fossee.csvmanager.model.CsvDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonPropertyOrder({"name", "email", "phoneNumber"})
class DocumentSchema {
    String name, email, phoneNumber;

    public DocumentSchema(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}

public class CsvManager {

    /**
     * Jackson CscMapper: To read/write CSV encoded content
     */
    static final CsvMapper mapper = new CsvMapper();
    /**
     * Schema tells Jackson CSV Module how the columns should be and are named
     * in CSV file. We can auto-construct the Schema by mapping a class with
     * proper JsonPropertyOrder. Also, to mark that first row in CSV contains
     * the column name we can use schema with header.
     */
    static final CsvSchema documentSchema = mapper.schemaFor(DocumentSchema.class).withHeader();

    static public boolean isCsvFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "text/csv");
    }

    public static List<CsvDocument> parseCsvToDocument(MultipartFile file)
        throws RuntimeException {

        List<CsvDocument> documents = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            MappingIterator<DocumentSchema> iterator = mapper
                    .readerFor(DocumentSchema.class)
                    .with(documentSchema)
                    .readValues(reader);
            while (iterator.hasNext()) {
                DocumentSchema schema = iterator.next();
                documents.add(new CsvDocument(
                        schema.name,
                        schema.email,
                        schema.phoneNumber
                ));
            }
        } catch (IOException ie) {
            throw new RuntimeException("Error Parsing CSV File " + ie.getMessage());
        }

        return documents;
    }

    public static String parseDocumentToCsv(List<CsvDocument> documents)
        throws RuntimeException {
        try (StringWriter writer = new StringWriter()) {
            CsvSchema schema = mapper.schemaFor(DocumentSchema.class);
            SequenceWriter sequenceWriter = mapper.writerWithSchemaFor(DocumentSchema.class)
                    .writeValues(writer);
            for (CsvDocument document : documents) {
                sequenceWriter.write(document);
            }

            return writer.toString();

        } catch (IOException ie) {
            throw new RuntimeException("Error parsing Document to CSV File " + ie.getMessage());
        }
    }
}
