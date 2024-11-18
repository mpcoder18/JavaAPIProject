package com.example.testingspecification.review;

import com.example.testingspecification.book.Book;
import com.example.testingspecification.book.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class that handles the business logic for managing review entities.
 */
@Service
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    /**
     * ReviewService Constructor.
     * @param reviewRepository the linked review repository.
     * @param bookRepository the linked book repository.
     */
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        log.info("ReviewService created");
    }

    /**
     * Saves a new review in the database by creating a Review entity from the provided DTO.
     *
     * @param reviewDTO the review data transfer object.
     * @return the saved Review entity.
     * @throws EntityNotFoundException if the referenced book does not exist.
     */
    public Review saveReview(ReviewDTO reviewDTO) throws EntityNotFoundException {
        Review reviewEntity = createReviewFromDTO(reviewDTO);
        log.info("Saving review");
        return reviewRepository.save(reviewEntity);
    }

    /**
     * Calculates the average rating of a book based on its reviews.
     *
     * @param bookId the ID of the book.
     * @return the average rating, or NaN if there are no reviews.
     * @throws EntityNotFoundException if the book does not exist.
     */
    public Double calculateAverageRating(Long bookId) throws EntityNotFoundException {
        List<Review> reviews = getReviewsByBookId(bookId);
        log.info("Calculating average rating");
        return reviews.stream().mapToDouble(Review::getRating).average().orElse(Double.NaN);
    }

    /**
     * Updates an existing review with the provided updated review data.
     *
     * @param reviewId      the ID of the review to be updated.
     * @param updatedReview the updated review data transfer object.
     * @return the updated Review entity.
     * @throws EntityNotFoundException if the review or the book does not exist.
     */
    public Review updateReview(Long reviewId, ReviewDTO updatedReview) throws EntityNotFoundException {
        Review existingReview = reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));

        Book updatedBook = bookRepository
                .findById(updatedReview.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + updatedReview.getBookId()));

        existingReview.setReviewerName(updatedReview.getReviewerName());
        existingReview.setRating(updatedReview.getRating());
        existingReview.setDate(updatedReview.getDate());
        existingReview.setText(updatedReview.getText());
        existingReview.setBook(updatedBook);
        reviewRepository.save(existingReview);
        log.info("Updating review");
        return existingReview;
    }

    /**
     * Deletes a review from the database by its ID.
     *
     * @param reviewId the ID of the review to be deleted.
     * @return the deleted Review entity.
     * @throws EntityNotFoundException if the review does not exist.
     */
    public Review deleteReview(Long reviewId) throws EntityNotFoundException {
        Review reviewToDelete = getById(reviewId);
        reviewRepository.delete(reviewToDelete);
        log.info("Deleting review");
        return reviewToDelete;
    }

    /**
     * Retrieves all reviews associated with a specific book by its ID.
     *
     * @param bookId the ID of the book.
     * @return a list of reviews for the book.
     * @throws EntityNotFoundException if the book does not exist.
     */
    public List<Review> getReviewsByBookId(Long bookId) throws EntityNotFoundException {
        Book book = bookRepository
                .findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));
        log.info("Getting reviews by book id");
        return reviewRepository.findByBook(book);
    }

    /**
     * Saves a batch of reviews by creating Review entities from the provided DTOs.
     *
     * @param reviews a list of review data transfer objects.
     */
    public void saveReviews(List<ReviewDTO> reviews) {
        for (ReviewDTO review : reviews) {
            Review reviewEntity = createReviewFromDTO(review);
            reviewRepository.save(reviewEntity);
        }
        log.info("Saving reviews in batch");
    }

    /**
     * Retrieves all reviews stored in the database.
     *
     * @return a list of all reviews.
     */
    public List<Review> getAllReviews() {
        log.info("Getting all reviews");
        return reviewRepository.findAll();
    }

    /**
     * Retrieves a specific review by its ID.
     *
     * @param reviewId the ID of the review.
     * @return the Review entity.
     * @throws EntityNotFoundException if the review does not exist.
     */
    public Review getById(Long reviewId) throws EntityNotFoundException {
        log.info("Getting review by id");
        return reviewRepository
                .findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review not found with id: " + reviewId));
    }

    /**
     * Creates a Review entity from a ReviewDTO.
     *
     * @param reviewDTO the review data transfer object.
     * @return the created Review entity.
     * @throws EntityNotFoundException if the referenced book does not exist.
     */
    private Review createReviewFromDTO(ReviewDTO reviewDTO) throws EntityNotFoundException {
        Book referencedBook = bookRepository
                .findById(reviewDTO.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + reviewDTO.getBookId()));

        log.info("Creating review from DTO");
        return Review.builder()
                .reviewerName(reviewDTO.getReviewerName())
                .date(reviewDTO.getDate())
                .book(referencedBook)
                .text(reviewDTO.getText())
                .rating(reviewDTO.getRating())
                .build();
    }
}



