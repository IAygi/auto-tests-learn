package ru.iaygi.api.tests.vladimir;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;
import ru.iaygi.api.tests.vladimir.dto.BookStoreDTO;
import ru.iaygi.api.tests.vladimir.dto.UpdateUserViaPutDTO;
import ru.iaygi.api.tests.vladimir.dto.UpdateUserViaPatchDTO;

import java.util.*;

import static io.restassured.http.ContentType.JSON;

public class RestMethod {

    @Step("Получить данные по id")
    public static RestExecutor getResourceId() {
        RestExecutor request = new RestExecutor(EndPoint.baseUrl)
                .contentType(JSON);
        request.get(EndPoint.getResourceId);

        return request;
    }

    @Step("Обновить пользователя методом PUT")
    public static RestExecutor updateUserViaPut(UpdateUserViaPutDTO updateUserViaPutDTO) {
        RestExecutor request = new RestExecutor(EndPoint.baseUrl)
                .contentType(JSON)
                .body(updateUserViaPutDTO);
        request.put(EndPoint.updateUserViaPut);

        return request;
    }

    @Step("Получить пользователя по id")
    public static RestExecutor getUserId(String id) {
        RestExecutor request = new RestExecutor(EndPoint.baseUrl)
                .contentType(JSON);
        request.get(EndPoint.getUserId + id);

        return request;
    }

    @Step("Обновить пользователя методом PATCH")
    public static RestExecutor updateUserViaPatch(UpdateUserViaPatchDTO updateUserViaPatchDTO) {
        RestExecutor request = new RestExecutor(EndPoint.baseUrl)
                .contentType(JSON)
                .body(updateUserViaPatchDTO);
        request.patch(EndPoint.updateUserViaPatch);

        return request;
    }

    @Step("Создать пользователя")
    public static RestExecutor createUser(BookStoreDTO bookStoreDTO) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(bookStoreDTO);
        request.post(EndPoint.createUser);

        return request;
    }

    @Step("Получить токен")
    public static RestExecutor generateToken(BookStoreDTO bookStoreDTO) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(bookStoreDTO);
        request.post(EndPoint.generateToken);

        return request;
    }

    @Step("Произвести логин")
    public static RestExecutor authorize(BookStoreDTO bookStoreDTO) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(bookStoreDTO);
        request.post(EndPoint.authorize);

        return request;
    }

    @Step("Получить список книг")
    public static RestExecutor getBooksList() {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON);
        request.get(EndPoint.getBooksList);

        return request;
    }

    @Step("Добавить книгу пользователю")
    public static RestExecutor addBook(String userId, String isbn, String token) {
        Map<String, String> isbnList = new HashMap<>() {
            {
                put("isbn", isbn);
            }
        };

        ArrayList<Object> collectionOfIsbns = new ArrayList<>();
        collectionOfIsbns.add(0, isbnList);

        BookStoreDTO bookStoreDTO = new BookStoreDTO()
                .userId(userId)
                .collectionOfIsbns(collectionOfIsbns);

        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(bookStoreDTO)
                .header("Authorization",
                        "Bearer " + token);
        request.post(EndPoint.addBook);

        return request;
    }

    @Step("Получить список книг по пользователю")
    public static RestExecutor getUserBooks(String userId, String token) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .basePath("/Account/v1/User/{UUID}")
                .pathParam("UUID", userId)
                .header("Authorization", "Bearer " + token);
        request.get();

        return request;
    }

    @Step("Удалить книгу")
    public static RestExecutor deleteBook(String isbn, String userId, String token) {
        BookStoreDTO bookStoreDTO = new BookStoreDTO()
                .isbn(isbn)
                .userId(userId);

        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(bookStoreDTO)
                .header("Authorization", "Bearer " + token);
        request.delete(EndPoint.deleteBook);

        return request;
    }

    @Step("Удалить пользователя")
    public static RestExecutor deleteUser(String userId, String token) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .basePath("/Account/v1/User/{UUID}")
                .pathParam("UUID", userId)
                .header("Authorization", "Bearer " + token);
        request.delete();

        return request;
    }
}