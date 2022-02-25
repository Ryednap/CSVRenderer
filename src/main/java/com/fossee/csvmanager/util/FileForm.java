/**
 * @author Ujjwal Pandey
 * @version 1.2
 * @since 02-12-2022
 */


package com.fossee.csvmanager.util;


import org.springframework.stereotype.Component;


/**
 * At view level we have html option menu where user selects one of the csv file name from the list.
 * This class maps to that chosenFileName attribute.
 */

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
