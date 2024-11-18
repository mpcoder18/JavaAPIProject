package com.example.testingspecification.reviewcontroller;

import com.example.testingspecification.book.Book;
import com.example.testingspecification.book.BookController;
import com.example.testingspecification.book.BookService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


/**
 * TODO:
 * add all imports for the project (book bookService etc...) and write the correct package on the top of the file
 */

@WebMvcTest(BookController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class bookcontrollertest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Autowired
    ObjectMapper objectMapper;

    private Book book;

    @Autowired
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        book = new Book(1, "Author 1", LocalDate.of(2020, 1, 1), "Title 1");
    }

    @Test
    public void testCreateBook() throws Exception {
        // Mock the book service to return the book when createBook is called
        given(bookService.createBook(book)).willReturn(book);

        // Perform the POST request to create a book
        ResultActions response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );

        // Validate the response

        response.andExpect(MockMvcResultMatchers.jsonPath("$.author", equalTo(book.getAuthor())));
        response.andExpect(MockMvcResultMatchers.jsonPath("$.title", equalTo(book.getTitle())));
    }




    @Test
    public void testGetBookById() throws Exception {
        // uses mock bean
        given(bookService.getBookbyId(1L)).willReturn(book);

        ResultActions response = mockMvc.perform(get("/api/books/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(book)));
    }

    @Test
    public void testUpdateBook() throws Exception {
        doNothing().when(bookService).updateBook(1L, book);

        ResultActions response = mockMvc.perform(put("/api/books/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book))
        );

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        ResultActions response = mockMvc.perform(delete("/api/books/delete/1"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllBooks() throws Exception {
        given(bookService.getAllBooks()).willReturn(Collections.singletonList(book));
        ResultActions response = mockMvc.perform(get("/api/books"));
        response.andExpect(MockMvcResultMatchers.status().isOk());
        response.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(List.of(book))));
    }
}







