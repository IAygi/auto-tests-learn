package ru.iaygi.api.tests.grigoriy;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static io.qameta.allure.internal.shadowed.jackson.databind.util.ClassUtil.name;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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
  Все классы создаём в своей директории (java/ru/iaygi/api/tests/grigoriy)
 */

@Severity(NORMAL)
@Owner("")
@Tag("api_test")
@Epic("")
@Feature("")
public class GrApiTest extends Methods {
    Methods methods = new Methods();



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
    @DisplayName("Получение пользователя по id")
    @Description("Проверить получение пользователя по id")
    void getUserId() {

        step("Получить пользователя по id", () -> {
            var user =methods.getUser().shouldHave(statusCode(200)).getResponseAs("data",UserDto.class);
            assertAll(
                    ()-> assertThat(user.id()).isEqualTo(2),
                    ()-> assertThat(user.name()).isEqualTo("fuchsia rose"),
                    ()-> assertThat(user.year()).isEqualTo(2001),
                    ()-> assertThat(user.color()).isEqualTo("#C74375"),
                    ()-> assertThat(user.pantone_value()).isEqualTo("17-2031")
            );
        });
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверить создание пользователя")
    void createUser () {


        step("Создать пользователя", ()->{
            CreateUserDto createUserDto = new CreateUserDto()
                    .name("morpheus")
                    .job("leader");
            var res = methods.createUser(createUserDto).shouldHave(statusCode(201)).getResponseAs(CreateUserDto.class);
                });
    }
}
