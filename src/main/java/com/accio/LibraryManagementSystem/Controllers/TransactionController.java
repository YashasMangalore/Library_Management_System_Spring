package com.accio.LibraryManagementSystem.Controllers;

import com.accio.LibraryManagementSystem.Services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController
{
    @Autowired
    private TransactionService transactionService;

    @PutMapping("/issue")
    public ResponseEntity<String> issueBook(@RequestParam("cardID")Integer cardID,
                                    @RequestParam("bookID")Integer bookID) throws Exception
    {
        try
        {
            String result=transactionService.issueBook(cardID,bookID);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/return")
    public ResponseEntity<String> returnBook(@RequestParam("cardID")Integer cardID,
                                     @RequestParam("bookID")Integer bookID)throws Exception
    {
        try
        {
            String result=transactionService.returnBook(cardID,bookID);
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/reminder")
    public ResponseEntity<String> reminderMail()throws Exception
    {
        try
        {
            String result=transactionService.reminder();
            return new ResponseEntity<>(result,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
