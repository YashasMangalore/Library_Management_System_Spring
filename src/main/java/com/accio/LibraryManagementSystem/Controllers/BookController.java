package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Book;
import com.accio.LibraryManagementSystem.Requests.UpdateBookRequest;
import com.accio.LibraryManagementSystem.Responses.AuthorResponse;
import com.accio.LibraryManagementSystem.Responses.NewsResponse;
import com.accio.LibraryManagementSystem.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/book")
public class BookController
{
    @Autowired
    private BookService bookService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public String addBook(@RequestBody Book book)
    {
        return bookService.addBook(book);
    }

    @PutMapping("/associate-book-author")
    public ResponseEntity<String> associateBookAndAuthor(@RequestParam("bookID")Integer bookID,
                                         @RequestParam("authorID")Integer authorID)throws Exception
    {
        try
        {
            String response=bookService.associateBookAndAuthor(bookID,authorID);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateBook(@RequestParam Integer bookId, @RequestBody UpdateBookRequest bookRequest) throws Exception
    {
        try
        {
            String response=bookService.updateBook(bookId,bookRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-author-title")
    public ResponseEntity<AuthorResponse> findAuthorByTitle(@RequestParam String title) throws Exception
    {
        try
        {
            AuthorResponse authorResponse=bookService.findAuthorByTitle(title);
            return new ResponseEntity<>(authorResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/news")
    public NewsResponse getNews(@RequestParam("Country") String country, @RequestParam("apiKey") String apiKey)
    {
        return bookService.getNews(country,apiKey);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> deleteBook(@RequestParam Integer bookId,@RequestParam String title)throws Exception
    {
        try
        {
            String response=bookService.deleteBook(bookId,title);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}