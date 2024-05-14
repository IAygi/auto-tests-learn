package ru.iaygi.api.service;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import ru.iaygi.api.rest.RestMethods;

import static ru.iaygi.common.EndPoints.baseUrl;

public class TestBaseApi {

    public static RestMethods restMethods;

    @Step("Предварительная настройка")
    public static void init() {
        RestAssured.baseURI = baseUrl;
        restMethods = new RestMethods();
    }
}
