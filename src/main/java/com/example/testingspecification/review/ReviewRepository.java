package com.example.testingspecification.review;

import com.example.testingspecification.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * ReviewRepository handles all the sql.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByBook(Book book);

    void deleteByBookId(Long bookId);
}

