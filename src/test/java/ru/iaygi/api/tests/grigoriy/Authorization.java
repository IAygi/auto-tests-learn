package ru.iaygi.api.tests.grigoriy;

import ru.iaygi.api.service.RestExecutor;

import static io.restassured.http.ContentType.JSON;
import static ru.iaygi.api.tests.grigoriy.EndPoints.BASE_URL_BOOK_STORE;
import static ru.iaygi.api.tests.grigoriy.EndPoints.TOKEN;
import static ru.iaygi.api.tests.grigoriy.GrApiTest.newUser;

public class Authorization {

    private static String token;

    public static String createToken() {
        RestExecutor request = new RestExecutor(BASE_URL_BOOK_STORE)
                .contentType(JSON)
                .body(newUser);

        request.post(TOKEN);
        token = "Bearer " + request.getBodyByPath("token");
        return token;
    }

}
