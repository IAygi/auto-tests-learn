package ru.iaygi.api.tests.grigoriy;

import io.qameta.allure.Step;
import ru.iaygi.api.service.RestExecutor;

import static ru.iaygi.api.tests.grigoriy.EndPoints.BASE_URL;
import static ru.iaygi.api.tests.grigoriy.EndPoints.USER_ID;

public class Methods {
    @Step("Получить пользователя по ID")
    public RestExecutor getUser (){
        RestExecutor req = new RestExecutor(EndPoints.BASE_URL);
        req.get(EndPoints.USER_ID);
        return req;
    }
}
