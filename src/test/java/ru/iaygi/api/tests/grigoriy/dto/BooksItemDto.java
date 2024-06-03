package ru.iaygi.api.tests.grigoriy.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BooksItemDto {
    private String website;
    private Integer pages;
    private String subTitle;
    private String author;
    private String isbn;
    private String publisher;
    private String description;
    private String title;
    private String publishDate;
}
