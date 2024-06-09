package com.accio.LibraryManagementSystem.Responses;

import com.accio.LibraryManagementSystem.Enum.Genre;
import com.accio.LibraryManagementSystem.Models.Author;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse
{
    private String authorName;
    private String bookName;
    private Genre genre;
    private Integer bookPages;
    private Boolean isIssued;
}
