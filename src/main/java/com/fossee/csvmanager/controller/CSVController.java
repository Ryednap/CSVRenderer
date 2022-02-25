/**
 * @author Ujjwal Pandey
 * @version 1.2
 * @since 02-10-2022
 */

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

/**
 * Controller Layer that handles the Client request for form submission and viewing
 */
@Controller
public class CSVController {
    private final CsvService csvService;

    @Autowired
    public CSVController(CsvService csvService) {
        this.csvService = csvService;
    }


    /**
     *
     * This method serves the home page fo the web-application at /index endpoint
     *
     * @param model view Model
     * @return indexPage which gets mapped at View level to html page of the same name
     */

    @RequestMapping(value="/index", method= RequestMethod.GET)
    public String getHomePage (Model model) {
        model.addAttribute("form", new FileForm());
        model.addAttribute("files",  csvService.getFileNames());
        return "index";
    }

    /**
     * This method listens for form submission at the /index endpoint.
     * The form in html page is expected to pass a MultiPart File as request parameter
     * <br><br>
     * If the file received is not csv File the method redirects to error page
     *
     * @param file MultiPart csv file from html form
     * @param attributes redirection attributes
     * @return redirected url to index page or customError page if error occurs
     */
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


    /**
     * In index HTML page we have option feature that allows client to select one of the csv file names
     * from the list provided. This method listens for that form submission and find the CSV documents
     * with the mentioned {@link FileForm#getChosenFileName()}} and then passes it as model object to the
     * view Render page where the document list is rendered.
     * @see FileForm
     * @see com.fossee.csvmanager.model.CsvDocument
     *
     * @param form {@link FileForm}
     * @param model view model
     * @return viewRender page
     */
    @RequestMapping(value = "/view", method=RequestMethod.POST)
    public String getViewPage(@ModelAttribute("form") FileForm form,
                                    Model model) {

        model.addAttribute("documents", csvService.getCsvDocument(form.getChosenFileName()));
        return "viewRender";
    }


    /**
     *
     * This method is endpoint to parse the provided error message and status code to be displayed
     * on ErrorPage
     *
     * @param message error message as request param
     * @param status HTTP status code of the error as request param
     * @return errorPage
     */
    @RequestMapping(value = "/customError")
    public ModelAndView handleError(@RequestParam("message") String message ,
                                    @RequestParam("status") String status) {

        ModelAndView errorPage = new ModelAndView("errorPage");
        errorPage.addObject("message", message);
        errorPage.addObject("status", status);
        return errorPage;
    }
}
