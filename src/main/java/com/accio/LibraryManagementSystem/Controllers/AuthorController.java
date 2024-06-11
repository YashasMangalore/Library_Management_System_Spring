package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Author;
import com.accio.LibraryManagementSystem.Requests.UpdateAuthorRequest;
import com.accio.LibraryManagementSystem.Responses.AuthorResponse;
import com.accio.LibraryManagementSystem.Responses.BookResponse;
import com.accio.LibraryManagementSystem.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
public class AuthorController
{
    @Autowired
    private AuthorService authorService;

    @PostMapping("/add")
    public String addAuthor(@RequestBody Author author)
    {
        return authorService.addAuthor(author);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAuthor(@RequestBody UpdateAuthorRequest authorRequest) throws Exception
    {
        try
        {
            String response=authorService.updateAuthor(authorRequest);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-author-id")
    public ResponseEntity<Author> findAuthorByID(@RequestParam("id")Integer authorID) throws Exception
    {
        try
        {
            Author authorResponse=authorService.findAuthorByID(authorID);
            return new ResponseEntity<>(authorResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find-author-name")
    public ResponseEntity<AuthorResponse> findAuthorByName(@RequestParam String authorName) throws Exception
    {
        try
        {
            AuthorResponse authorResponse=authorService.findAuthorByName(authorName);
            return new ResponseEntity<>(authorResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<List<String>> bookList(@RequestParam String authorName) throws Exception
    {
        try
        {
            List<String> response=authorService.bookList(authorName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/books-details")
    public ResponseEntity<List<BookResponse>> bookListDetailed(@RequestParam String authorName) throws Exception
    {
        try
        {
            List<BookResponse> response=authorService.bookListDetailed(authorName);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/remove")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAuthor(@RequestParam Integer authorID,@RequestParam String authorName) throws Exception
    {
        try
        {
            String response=authorService.deleteAuthor(authorID,authorName);
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
