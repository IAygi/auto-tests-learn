package ru.iaygi.api.rest;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;
import ru.iaygi.dto.UpdateUserDTO;
import ru.iaygi.dto.UserDTO;
import ru.iaygi.common.EndPoints;

import static io.restassured.http.ContentType.JSON;

public class RestMethods {

    @Step("Получить всех пользователей")
    public RestExecutor getAllUsers() {
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON);
        request.get(EndPoints.getAllUsers);

        return request;
    }

    @Step("Получить пользователя '{login}'")
    public RestExecutor getUser(String login) {
        UserDTO UsersDTO = new UserDTO().login(login);
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON)
                .body(UsersDTO);
        request.post(EndPoints.getUser);

        return request;
    }

    @Step("Обновить пользователя")
    public RestExecutor updateUser(UpdateUserDTO updateUserDTO) {
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON)
                .body(updateUserDTO);
        request.put(EndPoints.updateUser);

        return request;
    }

    @Step("Создать пользователя")
    public RestExecutor createUser(UserDTO usersDTO) {
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON)
                .body(usersDTO);
        request.post(EndPoints.createUser);

        return request;
    }

    @Step("Удалить пользователя '{login}'")
    public RestExecutor deleteUser(String login) {
        UserDTO UsersDTO = new UserDTO().login(login);
        RestExecutor request = new RestExecutor(EndPoints.baseUrl)
                .contentType(JSON)
                .body(UsersDTO);
        request.delete(EndPoints.deleteUser);

        return request;
    }
}
