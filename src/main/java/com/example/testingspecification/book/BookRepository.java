package com.example.testingspecification.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * The BookRepository.
 */

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE " +
            "(:author IS NULL OR b.author = :author) AND " +
            "(:title IS NULL OR b.title = :title) AND " +
            "(:publicationDate IS NULL OR b.publicationDate = :publicationDate)")
    List<Book> filterBooks(
            @Param("author") String author,
            @Param("title") String title,
            @Param("publicationDate") LocalDate publicationDate);

}
