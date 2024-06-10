package com.accio.LibraryManagementSystem.Services;

import com.accio.LibraryManagementSystem.Enum.TransactionStatus;
import com.accio.LibraryManagementSystem.Models.*;
import com.accio.LibraryManagementSystem.Repository.BookRepository;
import com.accio.LibraryManagementSystem.Repository.CardRepository;
import com.accio.LibraryManagementSystem.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TransactionService
{
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private JavaMailSender javaMailSender;

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

    public String reminder() throws Exception
    {
        try
        {
            List<Transaction> transactionList = transactionRepository.findAll();
            Map<Teacher, List<Student>> delayedStudentList = new HashMap<>();
            for (Transaction transaction : transactionList)
            {
                Date returnDate = transaction.getIdealReturnDate();
                LocalDate returnLocalDate = returnDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                long daysLate = ChronoUnit.DAYS.between(returnLocalDate, currentDate);

                if (daysLate > 0)
                {
                    int fineAmount = (int) daysLate * 5;
                    transaction.setTransactionStatus(TransactionStatus.PENDING);

                    sendReminderMailToStudent(transaction.getCard().getStudent().getEmail(), transaction.getCard().getStudent().getName(), fineAmount);
                    Teacher delayedStudentTeacher = transaction.getCard().getStudent().getTeacher();
                    Student delayedStudent = transaction.getCard().getStudent();

                    List<Student> delayedStudentTeacherList=delayedStudentList.getOrDefault(delayedStudentTeacher,new ArrayList<>());
                    delayedStudentTeacherList.add(delayedStudent);
                    delayedStudentList.put(delayedStudentTeacher,delayedStudentTeacherList);
//                    delayedStudentList.computeIfAbsent(delayedStudentTeacher, k -> new ArrayList<>()).add(delayedStudent);
                }
                else if (daysLate == 0)
                {
                    sendReminderMailToStudent(transaction.getCard().getStudent().getEmail(), transaction.getCard().getStudent().getName(), 0);
                }
            }

            for (Teacher teacher : delayedStudentList.keySet())
            {
                List<Student> delayedStudents = delayedStudentList.get(teacher);
                StringBuilder studentsList = new StringBuilder();
                for (Student student : delayedStudents)
                {
                    int fineAmount = calculateFineAmount(transactionList, student);
                    studentsList.append("Student name: ").append(student.getName()).append(" Fine amount: ").append(fineAmount).append(",\n");
                }
                if (!studentsList.isEmpty())
                {
                    studentsList.delete(studentsList.length() - 2, studentsList.length());
                }
                sendReminderMailToTeacher(teacher.getEmail(), teacher.getName(), studentsList.toString());
            }
            return "Reminder mail sent to all.";
        }
        catch (MailException e)
        {
            throw new Exception("Failed to send email.", e);
        }
        catch (Exception e)
        {
            throw new Exception("An unexpected error has occurred.", e);
        }
    }

    private void sendReminderMailToStudent(String studentEmail, String studentName, int fineAmount) {
        SimpleMailMessage mailMessageStudent = new SimpleMailMessage();
        mailMessageStudent.setTo(studentEmail);
        mailMessageStudent.setFrom("springtestdummy@gmail.com");
        mailMessageStudent.setSubject("Reminder to return the books taken from the library");
        String body;
        if (fineAmount > 0)
        {
            body = "Hi " + studentName + " \n \n" + "This mail is being sent to remind you to return the library book as soon as possible. The current fine amount is: " + fineAmount + ". Make sure to return the book as soon as possible to avoid increasing the fine amount.\n \n Thank you, \n Library Department";
        }
        else
        {
            body = "Hi " + studentName + " \n \n" + "This mail is being sent to remind you to return the library book as soon as possible. Today is the last day to return the book, make sure to return the book to avoid the fine amount.\n \n Thank you, \n Library Department";
        }
        mailMessageStudent.setText(body);
        javaMailSender.send(mailMessageStudent);//autowired
    }

    private void sendReminderMailToTeacher(String teacherEmail, String teacherName, String studentsList)
    {
        SimpleMailMessage mailMessageTeacher = new SimpleMailMessage();
        mailMessageTeacher.setTo(teacherEmail);
        mailMessageTeacher.setFrom("springtestdummy@gmail.com");
        mailMessageTeacher.setSubject("Reminder to inform the students to return the books taken from the library");
        String body = "Hi " + teacherName + " \n \n" + "This mail is being sent to remind your students to return the library book as soon as possible. " +
                "The list of students is as follows:\n" + studentsList + "\n \n Thank you, \n Library Department";
        mailMessageTeacher.setText(body);
        javaMailSender.send(mailMessageTeacher);//autowired
    }

    private int calculateFineAmount(List<Transaction> transactionList, Student student)
    {
        for (Transaction transaction : transactionList)
        {
            if (transaction.getCard().getStudent().equals(student))
            {
                return transaction.getFineAmount();
            }
        }
        return 0;
    }
}
