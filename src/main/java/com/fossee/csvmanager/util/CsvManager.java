/**
 * @author Ujjwal Pandey
 * @version 1.2
 * @since 02-12-2022
 */

package com.fossee.csvmanager.util;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fossee.csvmanager.model.CsvDocument;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Each Row in the CSV File belongs to particular schema with named columns. The Jackson CSV module uses this schema
 * to break the comma seperated values into specific column. We can notify the column Order by annotating with '@JSONPropertyOrder'
 * and providing list of column names with same order and name as in CSV file. This is important !!! as incorrect order or name will
 * raise exception during parsing.
 */
@JsonPropertyOrder({ "name", "email", "phoneNumber" })
class DocumentSchema {
    public String name, email, phoneNumber;
    public DocumentSchema(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public DocumentSchema() {
    }

    @Override
    public String toString() {
        return "DocumentSchema{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}





/**
 *  The Main Manager class that manages the parsing and conversion of CSV Files using JACKSON CSV Module.
 * @see <a href="https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv>Jackson FasterXML</a>
 */
public class CsvManager {

    /**
     * Jackson CscMapper: To read/write CSV encoded content
     */
    static final CsvMapper mapper = new CsvMapper();
    /**
     * Schema tells Jackson CSV Module how the columns should be and are named
     * in CSV file. We can auto-construct the Schema by mapping a class with
     * proper JsonPropertyOrder. Also, to mark that first row in CSV contains
     * the column name we use an empty schema with header.
     *
     * <br><br>
     * Example:
     *  <pre>
     *      name, age, gender, phoneNumber     ----> Header
     *      ujjwal, 12, M, 23234434535         ----> Row1
     *      ramesh, 14, M, 34345454565         ----> Row2
     *      ramita, 22, F, 23243453534         ----> Row3
     *  </pre>
     */
    static final CsvSchema documentSchema = CsvSchema.emptySchema().withHeader();


    /**
     * This method tests the CSV file provided in Parameter to determine whether it's
     * content type if of "text/csv"
     *
     * @param file Multipart file to be tested for csv extension
     * @return true if the provided file is CSV file else false
     */
    static public boolean isCsvFile(MultipartFile file) {
        return Objects.equals(file.getContentType(), "text/csv");
    }


    /**
     * This method reads and parses the content of CSV file received from HTML form as MultiPart file.
     * The parsed content is stored as {@link CsvDocument} and stored in List which is returned to the caller of this method.
     * For parsing the method uses Jackson CSV mapper {@link #mapper} which reads the content of file(stream) against schema defined
     * by {@link #documentSchema} to parse headers and each row of data by {@link DocumentSchema}.
     *
     * @param file MultiPart form file to parsed
     * @return List of parsed {@link CsvDocument}
     * @throws RuntimeException This exception is thrown when IoException occurs while reading parsed data.
     */
    public static List<CsvDocument> parseCsvToDocument(MultipartFile file)
        throws RuntimeException {

        // Check to verify the integrity of file type
        if (!isCsvFile(file)) {
            throw new RuntimeException("The Described file is not CSV File");
        }

        // list of document to returned
        List<CsvDocument> documents = new ArrayList<>();


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            /*
                Here we use the CSVMapper to make sure we read CSV contents, then we get Object reader with
                header defined for class DocumentSchema and to parse the first row that contains header we use this object reader
                with #documentSchmea and read values from the BufferedReader stream #reader. Each value from the reader is mapped against
                DocumentSchema class fields.
             */
            MappingIterator<DocumentSchema> iterator = mapper
                    .readerFor(DocumentSchema.class)
                    .with(documentSchema)
                    .readValues(reader);

            /*
                No we iterate over each value and create new CSV document from the fields of those values.
                If due to any error parsing fails we throw Custom RunTimeException.
             */
            while (iterator.hasNextValue()) {
                DocumentSchema documentSchema = iterator.nextValue();
                documents.add(new CsvDocument(
                        documentSchema.name,
                        documentSchema.email,
                        documentSchema.phoneNumber
                ));
            }
        } catch (IOException ie) {
            throw new RuntimeException("Error Parsing CSV File " + ie.getMessage());
        }

        // debug
        System.out.println("Parsed Document: \n" + documents);
        return documents;
    }

}
