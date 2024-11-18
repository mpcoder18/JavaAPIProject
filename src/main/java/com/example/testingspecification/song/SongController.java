package com.example.testingspecification.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing songs.
 */
@Slf4j
@RestController
@RequestMapping("/api/songs")
public class SongController {
    @Autowired
    private SongService songService;

    /**
     * Creates a new song and saves it to the database.
     *
     * @param song the song to be created
     * @return the created song
     */
    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        log.info("Creating song");
        return ResponseEntity.ok(songService.createSong(song));
    }

    /**
     * Retrieves a song by its ID.
     *
     * @param id the ID of the song to retrieve
     * @return the song, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        Song song = songService.getSongById(id);
        if (song != null) {
            log.info("Getting song by id");
            return ResponseEntity.ok(song);
        } else {
            log.info("Song not found to get");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all songs from the system.
     *
     * @return the list of all songs
     */
    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs() {
        log.info("Getting all songs");
        return ResponseEntity.ok(songService.getAll());
    }

    /**
     * Filters songs based on optional parameters: singer, title, and releaseDate.
     *
     * @param singer      the singer to filter by (optional)
     * @param title       the title to filter by (optional)
     * @param releaseDate the release date to filter by (optional)
     * @return the list of filtered songs
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Song>> filterSongs(
            @RequestParam(required = false) String singer,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDate) {
        log.info("Filtering songs");
        return ResponseEntity.ok(songService.filter(singer, title, releaseDate));
    }

    /**
     * Updates an existing song based on its ID.
     *
     * @param id          the ID of the song to update
     * @param updatedSong the updated song data
     * @return the updated song, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song updatedSong) {
        Song song = songService.updateSong(id, updatedSong);
        if (song != null) {
            log.info("Updating song");
            return ResponseEntity.ok(song);
        } else {
            log.info("Song not found to update");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a song by its ID.
     *
     * @param id the ID of the song to delete
     * @return 204 No Content if the song is deleted, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id) {
        Song song = songService.getSongById(id);
        if (song != null) {
            log.info("Deleting song");
            songService.deleteSong(id);
            return ResponseEntity.noContent().build();
        } else {
            log.info("Song not found to delete");
            return ResponseEntity.notFound().build();
        }
    }

}
