package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Enum.TransactionStatus;
import com.accio.LibraryManagementSystem.Models.Book;
import com.accio.LibraryManagementSystem.Models.LibraryCard;
import com.accio.LibraryManagementSystem.Models.Transaction;
import com.accio.LibraryManagementSystem.Repository.BookRepository;
import com.accio.LibraryManagementSystem.Repository.CardRepository;
import com.accio.LibraryManagementSystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService
{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public String issueBook(Integer cardID,Integer bookID) throws Exception
    {
        //1) get book entity from book id
        Book book=bookRepository.findById(bookID).get();

        //2) get card entity from card id
        LibraryCard card=cardRepository.findById(cardID).get();

        //3) create a txn entity
        Transaction transaction=new Transaction();

        //failure if issued
        if(book.getIsIssued())
        {
            throw new Exception("Book is already issued");
        }
        //failure if noOf books issued >3(limit)
        if(card.getNoOfBooksIssued()==3)
        {//we are not saving in db so failure setting not needed
            throw new Exception("Card book issue limit is reached");
        }
        //success
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        //set foreign key entities (card,book)
        transaction.setBook(book);
        transaction.setCard(card);
        //set isIssued=true,
        book.setIsIssued(true);
        //set Card issuedBook+1
        card.setNoOfBooksIssued(card.getNoOfBooksIssued()+1);

        transaction=transactionRepository.save(transaction);
        bookRepository.save(book);
        cardRepository.save(card);

        return "The transaction is stored with transactionID: "+transaction.getTransactionID();
    }
}
