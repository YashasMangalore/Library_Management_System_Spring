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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
            throw new Exception("Book issue limit for this card is reached");
        }
        //success
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);

        // Set return date (15 days from issue date)
        LocalDate returnLocalDate = LocalDate.now().plusDays(15);
        Date returnDate = Date.from(returnLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        transaction.setIdealReturnDate(returnDate);
        transaction.setActualReturnDate(returnDate);
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

    public String returnBook(Integer cardID,Integer bookID) throws Exception
    {
        Transaction transaction = transactionRepository.findByCardCardIDAndBookBookID(cardID, bookID)
                .orElseThrow(() -> new Exception("Transaction not found"));

        // Check if the book was issued
        if (!transaction.getBook().getIsIssued()) {
            throw new Exception("Book is not issued");
        }

        // Retrieve the return date from the transaction
        Date returnDate = transaction.getIdealReturnDate();

        // Calculate the number of days the book is returned after the return date
        LocalDate returnLocalDate = returnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        long daysLate = ChronoUnit.DAYS.between(returnLocalDate, currentDate);

        // Calculate the fine amount
        int fineAmount = 0;
        if (daysLate > 0)
        {
            fineAmount = (int) daysLate * 5; // Each day late adds +5 as fine amount
        }

        //success
        transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        // Update the return date in the transaction entity
        transaction.setActualReturnDate(new Date());
        // Update the transaction status and fine amount
        transaction.setFineAmount(fineAmount);
        // Update book entity
        Book book = transaction.getBook();
        book.setIsIssued(false);
        // Update card entity
        LibraryCard card = transaction.getCard();
        card.setNoOfBooksIssued(card.getNoOfBooksIssued() - 1);
        // Save transaction, book, and card entities
        transaction = transactionRepository.save(transaction);
        bookRepository.save(book);
        cardRepository.save(card);

        if(fineAmount>0)
        {
            return "The transaction is stored with transactionID: "+transaction.getTransactionID()+" and the fine amount in Rupees is :"+fineAmount;
        }
        return "The transaction is stored with transactionID: "+transaction.getTransactionID();
    }
}
