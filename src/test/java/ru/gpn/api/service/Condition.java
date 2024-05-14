package ru.gpn.api.service;

import io.restassured.response.Response;

public interface Condition {
    void check(Response response);
}