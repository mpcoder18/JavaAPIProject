package com.example.testingspecification.song;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * SongRepository handles the sql.
 */

public interface SongRepository extends JpaRepository<Song, Long> {
    /**
     * Finds songs that contain the given singer in their singer field.
     *
     * @param singer the singer to search for
     * @return the list of songs matching the singer
     */
    List<Song> findBySingerContaining(String singer);

    /**
     * Finds songs that contain the given title in their title field.
     *
     * @param title the title to search for
     * @return the list of songs matching the title
     */
    List<Song> findByTitleContaining(String title);

    /**
     * Finds songs by their release date.
     *
     * @param releaseDate the release date to search for
     * @return the list of songs matching the release date
     */
    List<Song> findByReleaseDate(LocalDate releaseDate);
}

