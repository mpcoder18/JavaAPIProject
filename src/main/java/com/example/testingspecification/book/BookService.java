package com.example.testingspecification.book;

import com.example.testingspecification.review.Review;
import com.example.testingspecification.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The BookService class.
 * It creates a book repository.
 * It contains methods for creating, updating, getting and deleting books.
 */

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BookService {
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    /**
     * Book create book is used to make a book.
     * @param book the book.
     * @return return a book.
     */
    public Book createBook(Book book) {
        log.info("Creating a new book: {}", book);  // Logs at INFO level
        return bookRepository.save(book);
    }

    /**
     * getBookbyId is used to get a book from its id.
     * @param Id Id.
     * @return returns a book.
     */
    public Book getBookbyId(long Id){
        Optional<Book> book = bookRepository.findById(Id);
        return book.orElse(null);
    }

    /**
     * Void updateBook is used to update the info of a book inside the database.
     * The new data of the book will be saved inside the repository.
     *
     * @param id is the updated books' id.
     * @param updatedBook is the book that is updated.
     */
    public void updateBook(long id, Book updatedBook) {
        Book book = getBookbyId(id);
        if (book != null) {
            log.info("Updating book with ID: {}", id);
            // because you do not want to have a book that updates
            log.info("firstly deleting all Reviews that existed within the Book");
            List<Review> reviews = reviewRepository.findByBook(book);
            // First, delete the associated reviews
            for(Review review : reviews) {
                reviewRepository.delete(review);
            }
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPublicationDate(updatedBook.getPublicationDate());
            bookRepository.save(book);
        } else {
            log.warn("Book with ID {} not found. Update operation skipped.", id);  // Logs at WARN level
        }
    }

    /**
     * Void deleteBook is used to delete a book from the database.
     * It will delete the book form the repository.
     *
     * @param id is the id of the book that needs to be deleted.
     */

    public void deleteBook(Long id) {
        Book book = getBookbyId(id);
        if (book != null) {
            log.info("Deleting reviews for book with ID: {}", id);
            List<Review> reviews = reviewRepository.findByBook(book);
            // First, delete the associated reviews
            for(Review review : reviews) {
                reviewRepository.delete(review);
            }
            log.info("Deleting book with ID: {}", id);
            bookRepository.delete(book);
        } else {
            log.error("Failed to delete book. Book with ID {} not found.", id);
        }
    }

    /**
     * gives you a list of all the books in the database.
     * @return returns a list of books.
     */
    public List<Book> getAllBooks() {
        log.info("Fetching all books");
        return bookRepository.findAll();
    }

    /**
     * gives you a list of filtered books based on author, title and publicationdate.
     *
     * @param author the author of the book.
     * @param title the title of the book.
     * @param publicationDate the publication date of the book
     * @return it returns a list of filtered books.
     */
    public List<Book> filterBooks(String author, String title, LocalDate publicationDate) {
        log.info("Filtering books by author: {}, title: {}, publicationDate: {}",
                author, title, publicationDate);
        return bookRepository.filterBooks(author, title, publicationDate);
    }

}
