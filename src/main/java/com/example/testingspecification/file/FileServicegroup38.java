package com.example.testingspecification.file;

import com.example.testingspecification.book.Book;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.testingspecification.book.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The FileServicegroup38 class.
 *
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class FileServicegroup38 {

    private final BookRepository bookRepository;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    /**
     * Export books to CSV.
     * When a GET request is called this method will get triggered.
     * It will fetch book data from the database and return it in CSV.
     *
     * @return byte[] containing CSV data.
     */
    public byte[] exportBooksToCSV() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(byteArrayOutputStream))) {
            String[] header = {"ID", "Author", "Publication Date", "Title"};
            writer.writeNext(header);
            List<Book> books = bookRepository.findAll();
            for (Book book : books) {
                String publicationDate = book.getPublicationDate() != null ?
                        book.getPublicationDate().toString() : "Unknown Date";
                String[] data = {
                        String.valueOf(book.getId()),
                        book.getAuthor(),
                        publicationDate,
                        book.getTitle()
                };
                writer.writeNext(data);
            }
        }
        log.info("Books successfully exported to CSV.");
        return byteArrayOutputStream.toByteArray();  // Return byte array of CSV content
    }

    /**
     * Import books from CSV file.
     * It is triggered by a POST request.
     * It will read the file and process and convert the data into Book objects.
     *
     * @param inputStream the input stream containing CSV data.
     */
    public void importBooksFromCSV(InputStream inputStream) throws IOException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] nextLine;
            reader.readNext(); // Skip the header
            List<Book> books = new ArrayList<>();
            while ((nextLine = reader.readNext()) != null) {
                Book book = new Book();
                book.setAuthor(nextLine[1]);

                // Handle null or empty date values
                if (nextLine[2] != null && !nextLine[2].isEmpty()) {
                    book.setPublicationDate(LocalDate.parse(nextLine[2]));
                } else {
                    book.setPublicationDate(null);  // Set null if date is empty or invalid
                }

                book.setTitle(nextLine[3]);
                books.add(book);
            }
            bookRepository.saveAll(books);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        log.info("Books successfully imported from CSV.");
    }

    /**
     * Export books to JSON.
     * When a GET request is called this method will get triggered.
     * It will fetch book data from the database and return it in JSON.
     *
     * @return String containing JSON data.
     */
    public String exportBooksToJSON() throws IOException {
        List<Book> books = bookRepository.findAll();
        return objectMapper.writeValueAsString(books);  // Return JSON as String
    }

    /**
     * Import books from JSON.
     * It is triggered by a POST request.
     * It will read the file and process and convert the data into Book objects
     *
     * @param inputStream the input stream containing JSON data.
     */
    public void importBooksFromJSON(InputStream inputStream) throws IOException {
        List<Book> books = objectMapper.readValue(inputStream, new TypeReference<List<Book>>() {});
        bookRepository.saveAll(books);
        log.info("Books successfully imported from JSON.");
    }
}

