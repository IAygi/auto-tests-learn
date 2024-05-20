package ru.iaygi.api.tests.vladimir;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;

import static io.restassured.http.ContentType.JSON;

public class RestMethod {

    @Step("Получить данные по id")
    public static RestExecutor getResourceId() {
        RestExecutor request = new RestExecutor(EndPoint.baseUrl)
                .contentType(JSON);
        request.get(EndPoint.getResourceId);

        return request;
    }
}