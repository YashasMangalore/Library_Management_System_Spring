package com.accio.LibraryManagementSystem.Models;

import com.accio.LibraryManagementSystem.Enum.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionID;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp//automatically set when saving to db
    private Date issueDate;

    private Date idealReturnDate;
    private Date actualReturnDate;
    private Integer fineAmount;

    @JoinColumn
    @ManyToOne
    private LibraryCard card;

    @JoinColumn
    @ManyToOne
    private Book book;


}
