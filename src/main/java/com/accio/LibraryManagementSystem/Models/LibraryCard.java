package com.accio.LibraryManagementSystem.Models;

import com.accio.LibraryManagementSystem.Enum.CardStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LibraryCard
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardID;
    private Integer noOfBooksIssued;

    @Enumerated(value = EnumType.STRING)//telling spring it's a enum
    private CardStatus cardStatus;

    //This has to be written in child class
    @JoinColumn//add another column
    @OneToOne//mapping between entities
    private Student student;//with which Entity you are connected
}

