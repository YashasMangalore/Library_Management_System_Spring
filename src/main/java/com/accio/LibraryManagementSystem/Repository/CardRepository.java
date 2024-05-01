package com.accio.LibraryManagementSystem.Repository;

import com.accio.LibraryManagementSystem.Models.LibraryCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<LibraryCard,Integer>
{

}
