package com.accio.LibraryManagementSystem.Repository;

import com.accio.LibraryManagementSystem.Models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer>
{
    Book findByBookName(String bookName);
}
