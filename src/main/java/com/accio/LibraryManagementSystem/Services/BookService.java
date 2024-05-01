package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Models.Author;
import com.accio.LibraryManagementSystem.Models.Book;
import com.accio.LibraryManagementSystem.Repository.AuthorRepository;
import com.accio.LibraryManagementSystem.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService
{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public String addBook(Book book)
    {
        book=bookRepository.save(book);
        return "Book has been saved with bookID: "+book.getBookID();
    }
    public String associateBookAndAuthor(Integer bookID,Integer authorID)throws Exception
    {
        Optional<Book> optionalBook=bookRepository.findById(authorID);
        if(optionalBook.isEmpty())
        {
            throw new Exception("Entered bookID is invalid");
        }

        Optional<Author> optionalAuthor=authorRepository.findById(authorID);
        if(optionalAuthor.isEmpty())
        {
            throw new Exception("Entered authorID is invalid");
        }

        Book book=optionalBook.get();
        Author author=optionalAuthor.get();

        book.setAuthor(author);
        author.setNoOfBooks(author.getNoOfBooks()+1);
        //changed in both so save in both
        bookRepository.save(book);
        authorRepository.save(author);

        return "Book and author has been associated";
    }
}
