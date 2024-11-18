package com.example.testingspecification.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Class responsible for handling api requests for reviews.
 */
@Slf4j
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
        log.info("ReviewController created");
    }

    @GetMapping
    public List<Review> getReviews() {
        log.info("Getting all reviews");
        return reviewService.getAllReviews();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review createReview(@RequestBody ReviewDTO review) {
        log.info("Creating review");
        return reviewService.saveReview(review);
    }

    @PostMapping("/batch")
    public void addReviews(@RequestBody List<ReviewDTO> reviews) {
        log.info("Adding reviews in batch");
        reviewService.saveReviews(reviews);
    }

    @GetMapping("/{reviewId}")
    public Review getReviewById(@PathVariable Long reviewId) {
        log.info("Getting review by id");
        return reviewService.getById(reviewId);
    }

    @DeleteMapping("/delete/{reviewId}")
    public void deleteReviewById(@PathVariable Long reviewId) {
        log.info("Deleting review by id");
        reviewService.deleteReview(reviewId);
    }

    @PutMapping("/update/{reviewId}")
    public Review updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO review) {
        log.info("Updating review by id");
        return reviewService.updateReview(reviewId, review);
    }

    @GetMapping("/book/{bookId}/average-rating")
    public Double getAverageRating(@PathVariable Long bookId) {
        log.info("Getting average rating");
        return reviewService.calculateAverageRating(bookId);
    }

    @GetMapping("/book/{bookId}")
    public List<Review> getReviewsByBookId(@PathVariable Long bookId) {
        log.info("Getting reviews by id");
        return reviewService.getReviewsByBookId(bookId);
    }
}

