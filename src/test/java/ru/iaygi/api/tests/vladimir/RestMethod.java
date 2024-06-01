package ru.iaygi.api.tests.vladimir;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;
import ru.iaygi.api.tests.vladimir.dto.AccountDTO;
import ru.iaygi.api.tests.vladimir.dto.BookStoreDTO;
import ru.iaygi.api.tests.vladimir.dto.UpdateUserViaPutDTO;
import ru.iaygi.api.tests.vladimir.dto.UpdateUserViaPatchDTO;
import ru.iaygi.dto.UserDTO;

import java.lang.reflect.Array;
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
    public static RestExecutor createUser(AccountDTO accountDTO) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(accountDTO);
        request.post(EndPoint.createUser);

        return request;
    }

    @Step("Получить токен")
    public static RestExecutor generateToken(AccountDTO accountDTO) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(accountDTO);
        request.post(EndPoint.generateToken);

        return request;
    }

    @Step("Произвести логин")
    public static RestExecutor authorize(AccountDTO accountDTO) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(accountDTO);
        request.post(EndPoint.authorize);

        return request;
    }
/* -???
    @Step("Получить id пользователя")
    public static RestExecutor getUserUUID() {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON);
        request.get(EndPoint.getUserUUId);

        return request;
    }
*/
    @Step("Получить список книг")
    public static RestExecutor getBooksList() {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON);
        request.get(EndPoint.getBooksList);

        return request;
    }

    @Step("Добавить книгу пользователю")
    public static RestExecutor addBook(String userId,String isbn) {
        BookStoreDTO.CollectionOfIsbns[] collectionsOfIsbns = new BookStoreDTO.CollectionOfIsbns[1];
        collectionsOfIsbns[0] = new BookStoreDTO.CollectionOfIsbns("isbn", isbn);
        BookStoreDTO bookStoreDTO = new BookStoreDTO()
                .userId(userId)
                .collectionOfIsbns(collectionOfIsbns);
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON)
                .body(bookStoreDTO);
        request.post(EndPoint.addBook);

        return request;
    }

    @Step("Получить список книг по пользователю")
    public static RestExecutor getUserBooks(String userId) {
        RestExecutor request = new RestExecutor(EndPoint.Url)
                .contentType(JSON);
        request.get(EndPoint.getUserBooks);

        return request;
    }
}