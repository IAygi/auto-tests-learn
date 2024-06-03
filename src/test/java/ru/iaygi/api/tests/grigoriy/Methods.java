package ru.iaygi.api.tests.grigoriy;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;
import ru.iaygi.api.tests.grigoriy.dto.AddUserBookDto;
import ru.iaygi.api.tests.grigoriy.dto.CreateUserDto;
import ru.iaygi.api.tests.grigoriy.dto.UserBookStoreDto;

import static io.restassured.http.ContentType.JSON;
import static ru.iaygi.api.tests.grigoriy.Authorization.createToken;
import static ru.iaygi.api.tests.grigoriy.EndPoints.*;

public class Methods {

    @Step("Получить ресур по id")
    public RestExecutor getResourceWithId() {
        RestExecutor request = new RestExecutor(BASE_URL).contentType(JSON);
        request.get(RESOURCE);

        return request;
    }

    @Step("Создать пользователя")
    public RestExecutor createUser(CreateUserDto createUserDto) {
        RestExecutor request = new RestExecutor(BASE_URL).contentType(JSON).body(createUserDto);
        request.post(CREATE_USER);

        return request;
    }

    @Step("Получить пользователя по id")
    public RestExecutor getUser(Integer id) {
        RestExecutor request = new RestExecutor(BASE_URL).contentType(JSON);
        request.get(GET_USER + id);

        return request;
    }

    @Step("Обновить пользователя по id")
    public RestExecutor updateUser(CreateUserDto createUserDto) {
        RestExecutor request = new RestExecutor(BASE_URL).contentType(JSON).body(createUserDto);
        request.put(UPDATE_USER);

        return request;
    }

    @Step("Создать нового пользователя в книжном магазине")
    public RestExecutor createUserBookStore(UserBookStoreDto userBookStoreDto) {
        RestExecutor request =
                new RestExecutor(BASE_URL_BOOK_STORE).contentType(JSON).body(userBookStoreDto);
        request.post(CREATE_USER_BOOK_STORE);

        return request;
    }

    @Step("Получить список книг")
    public RestExecutor getAllBooks() {
        RestExecutor request = new RestExecutor(BASE_URL_BOOK_STORE).contentType(JSON);
        request.get(BOOKS);

        return request;
    }

    @Step("Добавить книгу пользователю")
    public RestExecutor addBookToUser(AddUserBookDto addUserBookDto) {
        RestExecutor request = new RestExecutor(BASE_URL_BOOK_STORE)
                .contentType(JSON)
                .header("Authorization", createToken())
                .body(addUserBookDto);
        request.post(BOOKS);

        return request;
    }

    @Step("Получить акаунт пользователя по id")
    public RestExecutor getUserAccount(String uuid) {
        RestExecutor request = new RestExecutor(BASE_URL_BOOK_STORE)
                .header("Authorization", createToken())
                .pathParam("UUID", uuid)
                .contentType(JSON);
        request.get(USER_ACCOUNT);

        return request;
    }
}
