/**
 * @author Ujjwal Pandey
 * @version 1.2
 * @since 02-10-2022
 */

package com.fossee.csvmanager.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This Data-Structure each individual CSV File. Each CSV File contains a list of CSVDocuments {@link CsvDocument}.
 * Hence, there is OneToMany relationship between each CSV File and corresponding documents.
 * @see CsvDocument
 */

@Entity
@Table(name = "csvFile")
public class CsvFile {

    /**
     * Name of the CSV File. Primary Key
     */
    @Id
    @Column(name = "file_name")
    private final String name;

    /**
     * Each CSV file contains list of csv documents and there is one-to-many relationship as one csv file may contain
     * multiple csv documents.
     */
    @OneToMany
    @Column(name = "csv_documents")
    List<CsvDocument> documentList;

    public CsvFile(String name, List<CsvDocument> documentList) {
        this.name = name;
        this.documentList = documentList;
    }
    public CsvFile() {
        this.name = "";
        this.documentList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<CsvDocument> getDocumentList() {
        return documentList;
    }

    public void setDocumentList(List<CsvDocument> documentList) {
        this.documentList = documentList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CsvFile csvFile = (CsvFile) o;
        return getName().equals(csvFile.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "CsvFile{" +
                "name='" + name + '\'' +
                ", documentList=" + documentList +
                '}';
    }
}
