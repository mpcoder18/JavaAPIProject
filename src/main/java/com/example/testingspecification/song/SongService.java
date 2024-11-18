package com.example.testingspecification.song;

import com.example.testingspecification.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * Service class for managing Song entities.
 */
@Slf4j
@Service
public class SongService {
    
    @Autowired
    private SongRepository songRepository;

    /**
     * Creates a new song.
     *
     * @param song the song to create
     * @return the created song
     */
    public Song createSong(Song song) {
        log.info("Creating song");
        return songRepository.save(song);
    }

    /**
     * Retrieves a song by its ID.
     *
     * @param id the ID of the song
     * @return the song, or null if not found
     */
    public Song getSongById(Long id) {
        log.info("Getting song by id");
        return songRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing song.
     *
     * @param id          the ID of the song to update
     * @param updatedSong the updated song data
     * @return the updated song, or null if not found
     */
    public Song updateSong(Long id, Song updatedSong) {
        Song song = getSongById(id);
        if (song != null) {
            log.info("Updating song by id");
            song.setSinger(updatedSong.getSinger());
            song.setReleaseDate(updatedSong.getReleaseDate());
            song.setTitle(updatedSong.getTitle());
            //            song.setAlbum(updatedSong.getAlbum());  // Updated to set the full Album object
            return songRepository.save(song);
        }
        return null;
    }

    /**
     * Deletes a song by its ID.
     *
     * @param id the ID of the song to delete
     */
    public void deleteSong(Long id) {
        log.info("Deleting song by id");
        songRepository.deleteById(id);
    }

    /**
     * Retrieves all songs.
     *
     * @return the list of all songs
     */
    public List<Song> getAll() {
        log.info("Getting all songs");
        return songRepository.findAll();
    }

    /**
     * Imports a bulk list of songs.
     *
     * @param songs the list of songs to import
     * @return the list of imported songs
     */
    public List<Song> importBulk(List<Song> songs) {
        log.info("Importing songs in bulk");
        return songRepository.saveAll(songs);
    }

    /**
     * Filters songs based on singer, title, or release date.
     *
     * @param singer      the singer to filter by
     * @param title       the title to filter by
     * @param releaseDate the release date to filter by
     * @return the list of songs matching the filter criteria
     */
    public List<Song> filter(String singer, String title, LocalDate releaseDate) {
        if (singer != null) {
            log.info("Filtering songs by singer");
            return songRepository.findBySingerContaining(singer);
        } else if (title != null) {
            log.info("Filtering songs by title");
            return songRepository.findByTitleContaining(title);
        } else if (releaseDate != null) {
            log.info("Filtering songs by release date");
            return songRepository.findByReleaseDate(releaseDate);
        }
        return getAll();
    }

    /**
     * Calculates the average rating for a song.
     *
     * @param id the ID of the song
     * @return the average rating, or 0.0 if no ratings exist
     */
    public double getAvgRating(Long id) {
        log.info("Calculating average rating");
        Song song = getSongById(id);
        if (song != null && song.getReviews() != null) {
            return song.getReviews().stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
        }
        return 0.0;
    }
}

