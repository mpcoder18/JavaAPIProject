package com.example.testingspecification.fileothergroup;

import com.example.testingspecification.exceptions.CSVFormatError;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * Controller, covering the api endpoints for csv handling.
 */
@Controller
@RequestMapping("/api/reviews/csv")
public class FileController {

    // Declare the instance variable at the top
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Exports csv.
     * @param response checks if the response of the localhost is ok.
     * @throws IOException throws exception if it doesn't.
     */
    @GetMapping("/all")
    public void exportCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=review.csv");
        fileService.exportReviewsToCSV(response);
    }

    /**
     * Imports csv.
     * @param file file should be delivered.
     * @throws IOException exception.
     * @throws CSVFormatError another exception.
     * @throws EntityNotFoundException another exception.
     */
    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public void importCsv(@RequestParam MultipartFile file)
            throws IOException, CSVFormatError, EntityNotFoundException {
        fileService.importReviewsFromCsv(file);
    }
}

