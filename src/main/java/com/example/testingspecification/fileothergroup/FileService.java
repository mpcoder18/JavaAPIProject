package com.example.testingspecification.fileothergroup;

import com.example.testingspecification.book.Book;
import com.example.testingspecification.book.BookRepository;
import com.example.testingspecification.exceptions.CSVFormatError;
import com.example.testingspecification.review.Review;
import com.example.testingspecification.review.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class contains the business logic of csv files.
 */
@Service
public class FileService {

    // Declare the instance variables at the top
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Autowired
    public FileService(BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Saves all reviews stored in a csv file.
     * @param file the file to be stored.
     * @throws IOException if reader is not started properly.
     * @throws EntityNotFoundException when a book could not be found by its bookId.
     * @throws CSVFormatError when the csv format is wrong.
     */
    public void importReviewsFromCsv(MultipartFile file) throws IOException, EntityNotFoundException, CSVFormatError {
        List<Review> reviews = parseCSV(file);
        reviews.forEach(review -> System.out.println(review.toString()));
        reviewRepository.saveAll(reviews);
    }

    /**
     * Writes at a given response all the reviews stored in the database in csv format.
     * @param response the httpResponse generated at a controller method.
     * @throws IOException whenever there is a problem with instantiating a writer.
     */
    public void exportReviewsToCSV(HttpServletResponse response) throws IOException {
        try (PrintWriter writer = response.getWriter()) {
            writer.println("id, bookId, text, rating, date, reviewerName");

            for (Review review : reviewRepository.findAll()) {
                writer.printf("%d, %d, %s, %d, %s, %s\n",
                        review.getId(),
                        review.getBook().getId(),
                        review.getText(),
                        review.getRating(),
                        review.getDate().toString(),
                        review.getReviewerName()
                );
            }
        }
    }

    /**
     * Parses the csv file and turns it into a review list.
     * @param file the file to be converted into a list.
     * @return returns the list of reviews specified by the file.
     * @throws IOException when the reader could not be initialized.
     * @throws EntityNotFoundException when a book is not found.
     * @throws CSVFormatError on bad csv format.
     */
    public List<Review> parseCSV(MultipartFile file) throws IOException, EntityNotFoundException, CSVFormatError {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<Review> reviews = new ArrayList<>();
            boolean isFirstLine = true;
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip the first line, which contains the headers
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                List<String> fields = List.of(line.split(","));
                if (fields.size() < 5) {
                    throw new CSVFormatError("Please define all fields");
                }
                long bookId;
                int rating;
                LocalDate date;
                try {
                    bookId = Long.parseLong(fields.get(0).trim());
                    rating = Integer.parseInt(fields.get(2).trim());
                    date = LocalDate.parse(fields.get(3).trim());
                } catch (NumberFormatException | DateTimeParseException e) {
                    throw new CSVFormatError("Invalid number or date format in CSV");
                }
                Book book = bookRepository
                        .findById(bookId)
                        .orElseThrow(() -> new EntityNotFoundException("Book with id " + bookId + " not found"));
                Review review = Review.builder()
                        .book(book)
                        .text(fields.get(1).trim())
                        .rating(rating)
                        .date(date)
                        .reviewerName(fields.get(4).trim())
                        .build();
                reviews.add(review);
            }
            return reviews;
        }
    }
}


