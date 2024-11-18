package com.example.testingspecification.review;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

/**
 * Used whenever a user want to perform a request to our system, such that
 * no Book is specified in a post request and no id either.
 */
@Data
@Builder
public class ReviewDTO {
   private String text;
   private int rating;
   private long bookId;
   private LocalDate date;
   private String reviewerName;
}
