package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Book;
import com.accio.LibraryManagementSystem.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController
{
    @Autowired
    private BookService bookService;

    @PostMapping("add")
    public String addBook(@RequestBody Book book)
    {
        return bookService.addBook(book);
    }

    @PutMapping("associateBookAndAuthor")
    public ResponseEntity associateBookAndAuthor(@RequestParam("bookID")Integer bookID,
                                         @RequestParam("authorID")Integer authorID)
    {
        try{
            String response=bookService.associateBookAndAuthor(bookID,authorID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
