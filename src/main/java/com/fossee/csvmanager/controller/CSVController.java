package com.fossee.csvmanager.controller;

import com.fossee.csvmanager.model.CsvDocument;
import com.fossee.csvmanager.service.CsvService;
import com.fossee.csvmanager.util.CsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;

@Controller
public class CSVController {
    private final CsvService csvService;

    @Autowired
    public CSVController(CsvService csvService) {
        this.csvService = csvService;
    }


    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String getPageView (Model model) {
        CsvDocument csvDocument = new CsvDocument();
        List<String> fileNames = csvService.getFileNames();
        model.addAttribute("csvDocument", csvDocument);
        model.addAttribute("files", fileNames);
        return "index";
    }
    @RequestMapping (value = "/index", method=RequestMethod.POST)
    public ResponseEntity<String> uploadFile (@RequestParam("file") MultipartFile file) {
        if (!CsvManager.isCsvFile(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        csvService.saveDocument(file);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("index");
    }
}
