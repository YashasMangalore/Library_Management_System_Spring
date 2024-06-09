package com.accio.LibraryManagementSystem.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity//This is a description/reflection of a table  //structure of table
@Table//create the table of below
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student //table properties
{
    @Id//primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentID;
    @Column(unique = true,length = 100)
    private String email;
    @JoinColumn
    @ManyToOne
    private Teacher teacher;

    private String name;
    private Integer age;
    private String branch;
    private String address;
}
