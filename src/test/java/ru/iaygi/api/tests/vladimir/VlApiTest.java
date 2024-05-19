package ru.iaygi.api.tests.vladimir;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iaygi.api.service.Conditions.statusCode;


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
@Owner("vpakhomo")
@Tag("api_test")
@Epic("Resource")
@Feature("Работа с данными через API")
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
    @DisplayName("Получение данных")
    @Description("Проверить получение данных")
    void getResourceId() {

        step("Получить данные", () -> {
            var res = RestMethod.getResourceId().shouldHave(statusCode(200)).getResponseAs("data",ResourceDTO.class);

            step("Проверить данные", () -> {
                assertThat(res).extracting("id", "name", "year", "color", "pantone_value")
                        .contains(2,"fuchsia rose" , 2001, "#C74375", "17-2031");
            });

        /* Первоначальная реализация второго шага

         step("Проверить данные", () -> {
                assertAll(
                        () -> {
                            assertThat(res.id()).isEqualTo(2);
                        },
                        () -> {
                            assertThat(res.name()).isEqualTo("fuchsia rose");
                        },
                        () -> {
                            assertThat(res.year()).isEqualTo(2001);
                        },
                         () -> {
                            assertThat(res.color()).isEqualTo("#C74375");
                        },
                        () -> {
                            assertThat(res.pantone_value()).isEqualTo("17-2031");
                        }
                );
            });*/
        });
    }
}

