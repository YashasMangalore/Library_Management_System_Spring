package com.accio.LibraryManagementSystem.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//in data
@NoArgsConstructor
public class ArticleResponse
{
    String title;
    String author;
    String url;
}
