package ru.iaygi.api.tests.vladimir;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import ru.iaygi.api.rest.RestMethods;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;

/*
  В качестве домашнего задания, предлагаю написать тест на эндпоинт:
  GET https://reqres.in/api/unknown/2 ( Single <resource> )
  с использованием класса RestExecutor и POJO объекта (можно использовать lombok).
  *
  Нужно проверить эти данные:
      "id": 2,
      "name": "fuchsia rose",
      "year": 2001,
      "color": "#C74375",
      "pantone_value": "17-2031"
  *
  Адрес ресурса: https://reqres.in
  Все классы создаём в своей директории (java/ru/iaygi/api/tests/vladimir)
 */

@Severity(NORMAL)
@Owner("")
@Tag("api_test")
@Epic("")
@Feature("")
public class VlApiTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @BeforeEach
    void prepare() {

    }

    @AfterEach
    void cleanup() {

    }

    @Test
    @DisplayName("")
    @Description("")
    void createUser() {

        step("", () -> {

        });
    }
}
