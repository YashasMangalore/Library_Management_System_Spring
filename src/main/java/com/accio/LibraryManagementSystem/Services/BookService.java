package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Models.Author;
import com.accio.LibraryManagementSystem.Models.Book;
import com.accio.LibraryManagementSystem.Repository.AuthorRepository;
import com.accio.LibraryManagementSystem.Repository.BookRepository;
import com.accio.LibraryManagementSystem.Requests.UpdateBookRequest;
import com.accio.LibraryManagementSystem.Responses.AuthorResponse;
import com.accio.LibraryManagementSystem.Responses.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class BookService
{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private RestTemplate restTemplate;

    public String addBook(Book book)
    {
        book=bookRepository.save(book);
        return "Book has been saved with bookID: "+book.getBookID();
    }

    public String associateBookAndAuthor(Integer bookID,Integer authorID)throws Exception
    {
        try
        {
            Optional<Book> optionalBook = bookRepository.findById(bookID);
            if (optionalBook.isEmpty())
            {
                throw new Exception("Entered bookID is invalid");
            }
            Optional<Author> optionalAuthor = authorRepository.findById(authorID);
            if (optionalAuthor.isEmpty())
            {
                throw new Exception("Entered authorID is invalid");
            }

            Book book = optionalBook.get();
            Author author = optionalAuthor.get();

            book.setAuthor(author);
            author.setNoOfBooks(author.getNoOfBooks() + 1);
            //changed in both so save in both
            bookRepository.save(book);
            authorRepository.save(author);

            return "Book and author has been associated";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public String updateBook(Integer bookId, UpdateBookRequest bookRequest)throws Exception
    {
        try
        {
            Book book=bookRepository.findById(bookId)
                    .orElseThrow(()->new RuntimeException("Entered bookID is invalid"));
            book.setBookName(bookRequest.getBookName()!=null? bookRequest.getBookName() : book.getBookName());
            book.setBookPages(bookRequest.getBookPages()!=null?bookRequest.getBookPages():book.getBookPages());
            book.setIsIssued(bookRequest.getIsIssued()!=null?bookRequest.getIsIssued() : book.getIsIssued());

            bookRepository.save(book);
            return "The attributes for book "+book.getBookName()+" has been successfully updated.";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public String deleteBook(Integer bookId,String title) throws Exception
    {
        try
        {
            Book book=bookRepository.findById(bookId)
                    .orElseThrow(()->new RuntimeException("Entered bookID is invalid"));
            if(!book.getBookName().equals(title))
            {
                throw new Exception("Book not found");
            }
            Author author=authorRepository.findById(book.getAuthor().getAuthorID())
                    .orElseThrow(()->new RuntimeException("Author not found of this book"));
            author.setNoOfBooks(author.getNoOfBooks()-1);
            authorRepository.save(author);
            bookRepository.save(book);
            return "The book has been successfully removed";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public AuthorResponse findAuthorByTitle(String title) throws Exception
    {
        Book book=bookRepository.findByBookName(title);
        if(book==null)
        {
            throw new Exception("Invalid title name");
        }
        return AuthorResponse.builder()
                .authorName(book.getAuthor().getAuthorName())
                .age(book.getAuthor().getAge())
                .ratings(book.getAuthor().getRatings())
                .noOfBooks(book.getAuthor().getNoOfBooks())
                .build();
    }

    public NewsResponse getNews(String country, String apiKey)
    {
        String url=prepareUrl(country,apiKey);
        return restTemplate.getForObject(url,NewsResponse.class);
    }

    private String prepareUrl(String country, String apiKey)
    {
        return "https://newsapi.org/v2/top-headlines?country="+country+"&apiKey="+apiKey;
    }
}
