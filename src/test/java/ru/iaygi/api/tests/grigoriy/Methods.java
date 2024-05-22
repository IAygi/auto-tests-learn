package ru.iaygi.api.tests.grigoriy;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;

import static io.restassured.http.ContentType.JSON;
import static ru.iaygi.api.tests.grigoriy.EndPoints.*;

public class Methods {
    @Step("Получить пользователя по ID")
    public RestExecutor getUser (){
        RestExecutor req = new RestExecutor(BASE_URL)
            .contentType(JSON);
        req.get(USER_ID);
        return req;
    }


    @Step ("Создать пользователя")
    public RestExecutor createUser (POJO pojo) {
        RestExecutor req = new RestExecutor(BASE_URL)
                .contentType(JSON)
                .body(pojo);
        req.post(CREATE_USER);
        return req;
    }
}
