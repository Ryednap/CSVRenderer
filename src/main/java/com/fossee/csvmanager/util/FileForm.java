package com.fossee.csvmanager.util;


import org.springframework.stereotype.Component;


@Component
public class FileForm {
    private String chosenFileName;

    public FileForm(String chosenFileName) {
        this.chosenFileName = chosenFileName;
    }
    public FileForm() {
        this.chosenFileName = "";
    }

    public String getChosenFileName() {
        return chosenFileName;
    }

    public void setChosenFileName(String chosenFileName) {
        this.chosenFileName = chosenFileName;
    }

    @Override
    public String toString() {
        return "FileForm{" +
                "chosenFileName='" + chosenFileName + '\'' +
                '}';
    }
}
