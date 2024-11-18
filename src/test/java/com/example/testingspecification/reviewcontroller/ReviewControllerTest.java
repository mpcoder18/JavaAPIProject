package com.example.testingspecification.reviewcontroller;

import com.example.testingspecification.book.Book;
import com.example.testingspecification.review.Review;
import com.example.testingspecification.review.ReviewController;
import com.example.testingspecification.review.ReviewDTO;
import com.example.testingspecification.review.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ReviewController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReviewService reviewService;

    @Autowired
    ObjectMapper objectMapper;

    private Review review;
    private ReviewDTO reviewDTO;

    @BeforeEach
    public void setUp() {
        Book book = new Book();
        book.setAuthor("author");
        book.setTitle("title");
        book.setId(2);

        review = Review.builder()
                .reviewerName("Jane Doe")
                .date(LocalDate.now())
                .rating(4)
                .book(book)
                .text("Amazing read")
                .build();

        reviewDTO = ReviewDTO.builder()
                .reviewerName(review.getReviewerName())
                .rating(review.getRating())
                .date(review.getDate())
                .bookId(review.getBook().getId())
                .text(review.getText())
                .build();
    }

    @Test
    public void testGetAllReviews() throws Exception {
        given(reviewService.getAllReviews()).willReturn(Collections.singletonList(review));

        ResultActions response = mockMvc.perform(get("/api/reviews"));
        response.andDo(MockMvcResultHandlers.print());
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(review))));
    }

    @Test
    public void testCreateReview() throws Exception {
        Review newReview = new Review(1L, null,"Amazing read" ,4, LocalDate.now(), "Jane Doe");

        given(reviewService.saveReview(reviewDTO)).willReturn(newReview);

        ResultActions response = mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDTO))
        );

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetReviewById() throws Exception {
        given(reviewService.getById(1L)).willReturn(review);

        ResultActions response = mockMvc.perform(get("/api/reviews/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(review)));
    }

    @Test
    public void testDeleteReview() throws Exception {
        given(reviewService.deleteReview(1L)).willReturn(review);

        ResultActions response = mockMvc.perform(delete("/api/reviews/delete/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testUpdateReview() throws Exception {
        Review updatedReview = new Review(1L, null,"Updated Review" ,3 ,LocalDate.now() , "Updated Reviewer");
        given(reviewService.updateReview(1L, reviewDTO)).willReturn(updatedReview);

        ResultActions response = mockMvc.perform(put("/api/reviews/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewDTO))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAverageRating() throws Exception {
        given(reviewService.calculateAverageRating(1L)).willReturn(4.5);

        ResultActions response = mockMvc.perform(get("/api/reviews/book/1/average-rating"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.content().string(equalTo("4.5")));
    }

    @Test
    public void testGetReviewsByBookId() throws Exception {
        given(reviewService.getReviewsByBookId(1L)).willReturn(Collections.singletonList(review));

        ResultActions response = mockMvc.perform(get("/api/reviews/book/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(review))));
        response.andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
    }
}

