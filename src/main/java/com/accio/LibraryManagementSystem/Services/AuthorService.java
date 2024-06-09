package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Models.Author;
import com.accio.LibraryManagementSystem.Models.Book;
import com.accio.LibraryManagementSystem.Repository.AuthorRepository;
import com.accio.LibraryManagementSystem.Repository.BookRepository;
import com.accio.LibraryManagementSystem.Requests.UpdateAuthorRequest;
import com.accio.LibraryManagementSystem.Responses.AuthorResponse;
import com.accio.LibraryManagementSystem.Responses.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService
{
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

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
        return optionalAuthor.get();
    }
    public AuthorResponse findAuthorByName(String authorName) throws Exception
    {
        Author author=authorRepository.findByAuthorName(authorName);
        if(author==null)
        {
            throw new Exception("Invalid author name");
        }
        return AuthorResponse.builder()
                .authorName(author.getAuthorName())
                .age(author.getAge())
                .ratings(author.getRatings())
                .noOfBooks(author.getNoOfBooks())
                .build();
    }

    public String deleteAuthor(Integer authorID, String authorName)throws Exception
    {
        try {
            Author author = authorRepository.findById(authorID)
                    .orElseThrow(() -> new Exception("Author not found with ID: " + authorID));
//            Optional<Author> authorOptional = authorRepository.findById(authorID);
//            Author author = authorOptional.get();

            if (!author.getAuthorName().equals(authorName))
            {
                throw new Exception("Author not found");
            }

            List<Book> bookList=bookRepository.findAll();
            for(Book book:bookList)
            {
                if(book.getAuthor().getAuthorName().equals(authorName))
                {
                    bookRepository.delete(book);
                }
            }

            authorRepository.delete(author);
            return "Author with author-id: " + authorID + " and name: " + authorName + " is successfully removed from the database";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public String updateAuthor(UpdateAuthorRequest authorRequest) throws Exception
    {
        try
        {
            Author author=authorRepository.findById(authorRequest.getAuthorID())
                    .orElseThrow(()->new RuntimeException("Author not found."));

            author.setAuthorName(authorRequest.getAuthorName() != null ? authorRequest.getAuthorName() : author.getAuthorName());
            author.setAge(authorRequest.getAge() != null ? authorRequest.getAge() : author.getAge());
            author.setNoOfBooks(authorRequest.getNoOfBooks() != null ? authorRequest.getNoOfBooks() : author.getNoOfBooks());
            author.setEmail(authorRequest.getEmail() != null ? authorRequest.getEmail() : author.getEmail());
            author.setRatings(authorRequest.getRatings() != null ? authorRequest.getRatings() : author.getRatings());

            authorRepository.save(author);
            return "The author "+author.getAuthorName()+" has been successfully updated.";
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public List<String> bookList(String authorName)throws Exception
    {
        try
        {
            List<Book> bookList=bookRepository.findAll();
            List<String> ans=new ArrayList<>();
            for(Book book:bookList)
            {
                if(book.getAuthor().getAuthorName().equals(authorName))
                {
                    ans.add(book.getBookName());
                }
            }
            return ans;
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    public List<BookResponse> bookListDetailed(String authorName) throws Exception
    {
        try
        {
            List<Book> bookList=bookRepository.findAll();
            List<BookResponse> ans=new ArrayList<>();
            for(Book book:bookList)
            {
                if(book.getAuthor().getAuthorName().equals(authorName))
                {
                    BookResponse bookResponse=BookResponse.builder()
                            .bookPages(book.getBookPages())
                            .bookName(book.getBookName())
                            .genre(book.getGenre())
                            .isIssued(book.getIsIssued())
                            .authorName(book.getAuthor().getAuthorName())
                            .build();

                    ans.add(bookResponse);
                }
            }
            return ans;
        }
        catch(Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }
}
