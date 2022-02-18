package com.fossee.csvmanager.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "csvFile")
public class CsvFile {
    @Id
    @Column(name = "file_name")
    private final String name;

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
