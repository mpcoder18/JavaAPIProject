//package com.example.testingspecification.reviewcontroller;
//
//import com.example.testingspecification.review.Review;
//import com.example.testingspecification.review.ReviewController;
//import com.example.testingspecification.review.ReviewRepository;
//import com.example.testingspecification.review.ReviewService;
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.List;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@RestController
//public class ReviewContrTest {
//    @Mock
//    private ReviewService reviewService;
//
//    @Autowired
//    ReviewRepository reviewRepository;
//
//    @InjectMocks
//    private ReviewController reviewController;
//
//    @BeforeEach
//    public void setUp() {
//        // Initialize mocks and inject them
//        MockitoAnnotations.openMocks(this);
//
//        // Setup RestAssuredMockMvc with the reviewController
//        RestAssuredMockMvc.standaloneSetup(reviewController);
//
//    }
//
//    @Test
//    public void testGetAllReviews() {
//        // Setup mock data
//        Review review = new Review(1L, null, "Great book", 5, LocalDate.now(), "John Doe");
//        when(reviewService.getAllReviews()).thenReturn(List.of(review));
//
//        // Perform GET request and verify
//        given()
//                .when()
//                .get("/api/reviews")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .body("[0].text", equalTo("Great book"))
//                .body("[0].rating", equalTo(5))
//                .body("[0].reviewerName", equalTo("John Doe"));
//    }
//
//    @Test
//    public void testCreateReview() {
//        // Perform POST request
//        Review newReview = new Review(1L, null,"Amazing read" ,4, LocalDate.now(), "Jane Doe");
//
//        given()
//                .contentType("application/json")
//                .body(newReview)
//                .when()
//                .post("/api/reviews")
//                .then()
//                .statusCode(HttpStatus.OK.value());
//    }
//
//    @Test
//    public void testGetReviewById() {
//        // Setup mock response
//        Review review = new Review(1L, null , "Awesome", 5, LocalDate.now(), "Reviewer A");
//        when(reviewService.getById(1L)).thenReturn(review);
//
//        // Perform GET request
//        given()
//                .when()
//                .get("/api/reviews/1")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .body("text", equalTo("Awesome"))
//                .body("rating", equalTo(5))
//                .body("reviewerName", equalTo("Reviewer A"));
//    }
//
//    @Test
//    public void testDeleteReview() {
//        // Perform DELETE request
//        given()
//                .when()
//                .delete("/api/reviews/delete/1")
//                .then()
//                .statusCode(HttpStatus.OK.value());
//    }
//
//    @Test
//    public void testUpdateReview() {
//        // Setup mock data for the update
//        Review updatedReview = new Review(1L, null,"Updated Review" ,3 ,LocalDate.now() , "Updated Reviewer");
//
//        // Perform PUT request
//        given()
//                .contentType("application/json")
//                .body(updatedReview)
//                .when()
//                .put("/api/reviews/update/1")
//                .then()
//                .statusCode(HttpStatus.OK.value());
//    }
//
//    @Test
//    public void testGetAverageRating() {
//        // Setup mock data
//        when(reviewService.calculateAverageRating(1L)).thenReturn(4.5);
//
//        // Perform GET request
//        given()
//                .when()
//                .get("/api/reviews/book/1/average-rating")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .body(equalTo("4.5"));
//    }
//
//    @Test
//    public void testGetReviewsByBookId() {
//        // Setup mock data
//        Review review = new Review(1L, null ,"Fantastic book" ,5 , LocalDate.now(), "Reader B");
//        when(reviewService.getReviewsByBookId(1L)).thenReturn(Collections.singletonList(review));
//
//        // Perform GET request
//        given()
//                .when()
//                .get("/api/reviews/book/1")
//                .then()
//                .statusCode(HttpStatus.OK.value())
//                .body("[0].text", equalTo("Fantastic book"))
//                .body("[0].rating", equalTo(5))
//                .body("[0].reviewerName", equalTo("Reader B"));
//    }
//}
