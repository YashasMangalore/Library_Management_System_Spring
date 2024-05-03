package com.accio.LibraryManagementSystem.Repository;

import com.accio.LibraryManagementSystem.Models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String>//string uuid
{
    Optional<Transaction> findByCardCardIDAndBookBookID(Integer cardID, Integer bookID);
}
