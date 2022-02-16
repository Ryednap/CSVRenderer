package com.fossee.csvmanager.controller;

import com.fossee.csvmanager.model.CsvDocument;
import com.fossee.csvmanager.util.CsvManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class CSVController {

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String getPageView (Model model) {
        CsvDocument csvDocument = new CsvDocument();
        model.addAttribute("csvDocument", csvDocument);
        return "index";
    }
    @RequestMapping (value = "/index", method=RequestMethod.POST)
    public ResponseEntity<String> uploadFile (@RequestParam("file") MultipartFile file) {
        if (!CsvManager.isCsvFile(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("index");
    }
}
