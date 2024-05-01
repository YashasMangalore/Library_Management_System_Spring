package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Models.Author;
import com.accio.LibraryManagementSystem.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService
{
    @Autowired
    private AuthorRepository authorRepository;

    public String addAuthor(Author author)
    {
        authorRepository.save(author);
        return "Author has been saved with authorID: "+author.getAuthorID();
    }
    public Author findAuthorByID(Integer authorID) throws Exception
    {
        Optional<Author> optionalAuthor=authorRepository.findById(authorID);
        if(optionalAuthor.isEmpty())
        {
            throw new Exception("Invalid AuthorID");
        }
        Author author=optionalAuthor.get();
        return author;
    }
}
