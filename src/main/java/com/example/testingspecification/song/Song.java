package com.example.testingspecification.song;

import com.example.testingspecification.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity representing a Song.
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // test
    private String singer;
    private LocalDate releaseDate;
    private String title;
    //    @ManyToOne
    //    @JoinColumn(name = "album_id")
    //    private Album album;
    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

}
