//package com.example.testingspecification.reviewcontroller;
//
//import com.example.testingspecification.album.Album;
//import com.example.testingspecification.album.AlbumController;
//import com.example.testingspecification.album.AlbumService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.doNothing;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
///**
// * AlbumControllerTest class to test AlbumController methods.
// */
//@WebMvcTest(AlbumController.class)
//@ExtendWith(MockitoExtension.class)
//@AutoConfigureMockMvc(addFilters = false)
//public class albumControllerTest {
//
//
//
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AlbumService albumService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    private Album album;
//
//    @BeforeEach
//    public void setUp() {
//        album = new Album();
//        album.setId(1L);
//        album.setTitle("Album Title");
//        album.setArtist("Artist Name");
//        album.setGenre("Genre Name");
//    }
//
//    @Test
//    public void testCreateAlbum() throws Exception {
//        // Mock the album service to return the album when save is called
//        given(albumService.save(album)).willReturn(album);
//
//        // Perform the POST request to create an album
//        ResultActions response = mockMvc.perform(post("/api/albums")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(album))
//        );
//
//        // Validate the response
//        response.andExpect(MockMvcResultMatchers.status().isOk());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.title", equalTo(album.getTitle())));
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.artist", equalTo(album.getArtist())));
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.genre", equalTo(album.getGenre())));
//    }
//
//    @Test
//    public void testGetAlbumById() throws Exception {
//        // Mock the album service to return the album when findById is called
//        given(albumService.findById(1L)).willReturn(Optional.of(album));
//
//        // Perform the GET request to retrieve the album
//        ResultActions response = mockMvc.perform(get("/api/albums/1"));
//
//        // Validate the response
//        response.andExpect(MockMvcResultMatchers.status().isOk());
//        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(album)));
//    }
//
//    @Test
//    public void testUpdateAlbum() throws Exception {
//        // Mock the album service to return the updated album when updateAlbum is called
//        Album updatedAlbum = new Album();
//        updatedAlbum.setId(1L);
//        updatedAlbum.setTitle("Updated Title");
//        updatedAlbum.setArtist("Updated Artist");
//        updatedAlbum.setGenre("Updated Genre");
//
//        given(albumService.updateAlbum(1L, updatedAlbum)).willReturn(updatedAlbum);
//
//        // Perform the PUT request to update the album
//        ResultActions response = mockMvc.perform(put("/api/albums/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(updatedAlbum))
//        );
//
//        // Validate the response
//        response.andExpect(MockMvcResultMatchers.status().isOk());
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.title", equalTo(updatedAlbum.getTitle())));
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.artist", equalTo(updatedAlbum.getArtist())));
//        response.andExpect(MockMvcResultMatchers.jsonPath("$.genre", equalTo(updatedAlbum.getGenre())));
//    }
//
//    @Test
//    public void testDeleteAlbum() throws Exception {
//        // Mock the album service to do nothing when delete is called
//        doNothing().when(albumService).delete(1L);
//
//        // Perform the DELETE request to delete the album
//        ResultActions response = mockMvc.perform(delete("/api/albums/1"));
//
//        // Validate the response
//        response.andExpect(MockMvcResultMatchers.status().isOk());
//    }
//
//    @Test
//    public void testGetAllAlbums() throws Exception {
//        // Mock the album service to return a list of albums
//        given(albumService.findAll()).willReturn(Collections.singletonList(album));
//
//        // Perform the GET request to retrieve all albums
//        ResultActions response = mockMvc.perform(get("/api/albums"));
//
//        // Validate the response
//        response.andExpect(MockMvcResultMatchers.status().isOk());
//        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(album))));
//    }
//
//    @Test
//    public void testGetAverageRating() throws Exception {
//        // Mock the album service to return an average rating
//        double averageRating = 4.5;
//        given(albumService.calculateAverageRating(1L)).willReturn(averageRating);
//
//        // Perform the GET request to retrieve the average rating
//        ResultActions response = mockMvc.perform(get("/api/albums/average-rating/1"));
//
//        // Validate the response
//        response.andExpect(MockMvcResultMatchers.status().isOk());
//        response.andExpect(MockMvcResultMatchers.content().string(Double.toString(averageRating)));
//    }
//
//}
