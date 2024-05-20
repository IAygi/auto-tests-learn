package ru.iaygi.api.rest;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;
import ru.iaygi.dto.UpdateUserDTO;
import ru.iaygi.dto.UserDTO;

import static io.restassured.http.ContentType.JSON;
import static ru.iaygi.common.EndPoints.*;

public class RestMethods {

    @Step("Получить всех пользователей")
    public RestExecutor getAllUsers() {
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON);
        request.get(GET_ALL_USERS);

        return request;
    }

    @Step("Получить пользователя '{login}'")
    public RestExecutor getUser(String login) {
        UserDTO UsersDTO = new UserDTO().login(login);
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON)
                .body(UsersDTO);
        request.post(GET_USER);

        return request;
    }

    @Step("Обновить пользователя")
    public RestExecutor updateUser(UpdateUserDTO updateUserDTO) {
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON)
                .body(updateUserDTO);
        request.put(UPDATE_USER);

        return request;
    }

    @Step("Создать пользователя")
    public RestExecutor createUser(UserDTO usersDTO) {
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON)
                .body(usersDTO);
        request.post(CREATE_USER);

        return request;
    }

    @Step("Удалить пользователя '{login}'")
    public RestExecutor deleteUser(String login) {
        UserDTO UsersDTO = new UserDTO().login(login);
        RestExecutor request = new RestExecutor(BASE_URL)
                .contentType(JSON)
                .body(UsersDTO);
        request.delete(DELETE_USER);

        return request;
    }
}
