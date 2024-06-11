package com.accio.LibraryManagementSystem.Responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor//in data
@NoArgsConstructor
public class NewsResponse
{
    List<ArticleResponse> articles;
}
