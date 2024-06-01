package ru.iaygi.api.tests.vladimir.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;

@Data
@Accessors(fluent = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookStoreDTO {
    private String isbn;
    private String title;
    private String subTitle;
    private String author;
    private String userId;
    public String userID;
    private String userName;
    private String password;
    public String token;
    private ArrayList<Object> books;
    public ArrayList<Object> collectionOfIsbns;
}

