package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Models.Author;
import com.accio.LibraryManagementSystem.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("author")
public class AuthorController
{
    @Autowired
    private AuthorService authorService;

    @PostMapping("add")
    public String addAuthor(@RequestBody Author author)
    {
        return authorService.addAuthor(author);
    }

    @GetMapping("findAuthorByID")
    public ResponseEntity findAuthorByID(@RequestParam("id")Integer authorID)
    {
        try
        {
            Author authorResponse=authorService.findAuthorByID(authorID);
            return new ResponseEntity<>(authorResponse, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
