package com.example.testingspecification.book;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

/**
 * The BookController class.
 * The BookController creates a bookService.
 * It creates books, gets their id, updates them, deletes them and gets all of them, through the bookService.
 */

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    @PostMapping("/books")
    public Book createBook(@RequestBody Book book){
        return bookService.createBook(book);
    }

    @GetMapping("/books/{Id}")
    public Book getBookById(@PathVariable long Id){
        return bookService.getBookbyId(Id);
    }

    @PutMapping("/books/update/{Id}")
    public void updateBook(@PathVariable long Id, @RequestBody Book updatedBook){
        bookService.updateBook(Id, updatedBook);
    }

    @DeleteMapping("/books/delete/{Id}")
    public void deleteBook(@PathVariable long Id){
        bookService.deleteBook(Id);
    }

    @GetMapping("/books")
    public  List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/books/filter")
    public List<Book> filterBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publicationDate) {
        return bookService.filterBooks(author, title, publicationDate);
    }
}
