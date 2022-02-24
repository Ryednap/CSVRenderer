/**
 * @author Ujjwal Pandey
 * @version 1.2
 * @since 02-10-2022
 */

package com.fossee.csvmanager.model;

import javax.persistence.*;


/**
 * Each CSV File contains rows of data. Each row of that file is document in this web-application. That document is represented
 * by this DataStructure and stored in MYSQL database as "documents" table.
 * <br><br>
 * This is a basic dataStructure with basic fields, getters and setters no logic
 */

@Entity(name="Documents")
@Table(name="documents")
public class CsvDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name")
    private final String name;
    @Column(name = "email")
    private final String email;
    @Column(name = "phoneNumber", columnDefinition = "VARCHAR(12)")
    private final String phoneNumber;

    public CsvDocument(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    public CsvDocument() {
        this.name = "";
        this.email = "";
        this.phoneNumber = "";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CsvDocument{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
