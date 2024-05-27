package ru.iaygi.api.tests.grigoriy;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;

import static io.restassured.http.ContentType.JSON;
import static ru.iaygi.api.tests.grigoriy.EndPoints.*;

public class Methods {
    @Step("Получить ресур по id")
    public RestExecutor getResourceWithId() {
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON);
        request.get(RESOURCE);
        return request;
    }

    @Step("Создать пользователя")
    public RestExecutor createUser(CreateUserDto createUserDto) {
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON)
                .body(createUserDto);
        request.post(CREATE_USER);
        return request;
    }

    @Step("Получить пользователя по id")
    public RestExecutor getUserWithId(Integer id) {
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON);
        request.get(GET_USER + id);
        return request;
    }

    @Step("Обновить пользователя по id")
    public RestExecutor updateUser (CreateUserDto createUserDto) {
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON)
                .body(createUserDto);
        request.put(UPDATE_USER);
        return request;
    }
}
