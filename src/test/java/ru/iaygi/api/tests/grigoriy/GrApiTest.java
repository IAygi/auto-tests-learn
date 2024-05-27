package ru.iaygi.api.tests.grigoriy;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iaygi.dto.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.entry;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
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
    @DisplayName("Получить ресурс по id")
    @Description("Проверить, что по id возвращается нужный ресурс")
    void getUserId() {

        step("Получить ресурс по id", () -> {
            var resource = methods.getResourceWithId().shouldHave(statusCode(200)).getResponseAs("data", ResourceDto.class);
            assertAll(
                    () -> assertThat(resource.id()).isEqualTo(2),
                    () -> assertThat(resource.name()).isEqualTo("fuchsia rose"),
                    () -> assertThat(resource.year()).isEqualTo(2001),
                    () -> assertThat(resource.color()).isEqualTo("#C74375"),
                    () -> assertThat(resource.pantone_value()).isEqualTo("17-2031")
            );
        });
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверить создание пользователя")
    void createUser() {


        step("Создать пользователя", () -> {
            CreateUserDto createUserDto = new CreateUserDto()
                    .name("morpheus")
                    .job("leader");
            var result = methods.createUser(createUserDto).shouldHave(statusCode(201)).getResponseAs(CreateUserDto.class);
            assertThat(result).extracting("name", "job", "createdAt")
                    .contains("morpheus", "leader");
        });
    }

    @ParameterizedTest(name = "Получение пользователя по id: {0}")
    @ValueSource(ints = {1, 2, 3, 4, 5, 6})
    @DisplayName("ParameterizedTest: ")
    @Description("Проверить, что id соответствует first_name")
    void getUserWithId(int argument) {
        Map<Integer, String> firstName = new HashMap<>();
        firstName.put(1, "George");
        firstName.put(2, "Janet");
        firstName.put(3, "Emma");
        firstName.put(4, "Eve");
        firstName.put(5, "Charles");
        firstName.put(6, "Tracey");

        step("Проверить соотвестветствие id и first_name", () -> {
            var result = methods.getUserWithId(argument).shouldHave(statusCode(200)).getBodyByPath("data.first_name");
            assertThat(result).contains(firstName.get(argument));
        });
    }

    @Test
    @DisplayName("Обновить пользователя")
    @Description("Проверить обновление пользователя")
    void updateUser (){

    }


}