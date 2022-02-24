package com.fossee.csvmanager.controller;

import com.fossee.csvmanager.service.CsvService;
import com.fossee.csvmanager.util.FileForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CSVController {
    private final CsvService csvService;

    @Autowired
    public CSVController(CsvService csvService) {
        this.csvService = csvService;
    }


    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String getHomePage (Model model) {
        model.addAttribute("form", new FileForm());
        model.addAttribute("files",  csvService.getFileNames());
        return "index";
    }

    @RequestMapping(value = "/index", method=RequestMethod.POST)
    public RedirectView uploadFile (@RequestParam("file") MultipartFile file,
                                    RedirectAttributes attributes) {
        if (!csvService.isCsvFile(file)) {
            attributes.addAttribute("message", "Uploaded File is not CSV File");
            attributes.addAttribute("status", HttpStatus.BAD_REQUEST);
            return new RedirectView("/customError");
        }
        csvService.saveDocument(file);
        return new RedirectView("/index");
    }


    @RequestMapping(value = "/view", method=RequestMethod.POST)
    public String getViewPage(@ModelAttribute("form") FileForm form,
                                    Model model) {

        model.addAttribute("documents", csvService.getCsvDocument(form.getChosenFileName()));
        return "viewRender";
    }

    @RequestMapping(value = "/customError")
    public ModelAndView handleError(@RequestParam("message") String message ,
                                    @RequestParam("status") String status) {

        ModelAndView errorPage = new ModelAndView("errorPage");
        errorPage.addObject("message", message);
        errorPage.addObject("status", status);
        return errorPage;
    }
}
