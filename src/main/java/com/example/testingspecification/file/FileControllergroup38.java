package com.example.testingspecification.file;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

/**
 * The FileControllergroup38 class.
 * The FileControllergroup38 creates a fileService.
 */

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api/files")
public class FileControllergroup38 {
    private final FileServicegroup38 fileService;

    /**
     * ResponseEntity is used to export to CSV.
     *
     * @return the header with CSV content.
     */
    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportBooksToCSV() throws IOException {
        byte[] csvData = fileService.exportBooksToCSV();  // Simplified to directly get byte array
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvData);
    }

    /**
     * Import books from CSV file.
     *
     * @param file the CSV file to import
     * @return success message
     */
    @PostMapping("/import/csv")
    public ResponseEntity<String> importBooksFromCSV(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.importBooksFromCSV(file.getInputStream());
        return ResponseEntity.ok("Books imported successfully from CSV.");
    }

    /**
     * Export books to JSON.
     *
     * @return the header with JSON content.
     */
    @GetMapping("/export/json")
    public ResponseEntity<String> exportBooksToJSON() throws IOException {
        String jsonData = fileService.exportBooksToJSON();  // Now returns a String
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=books.json")
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonData);
    }

    /**
     * Import books from JSON file.
     *
     * @param file the JSON file to import
     * @return success message
     */
    @PostMapping("/import/json")
    public ResponseEntity<String> importBooksFromJSON(@RequestParam("file") MultipartFile file) throws IOException {
        fileService.importBooksFromJSON(file.getInputStream());
        return ResponseEntity.ok("Books imported successfully from JSON.");
    }
}

