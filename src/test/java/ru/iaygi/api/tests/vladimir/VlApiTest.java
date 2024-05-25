package ru.iaygi.api.tests.vladimir;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.iaygi.api.tests.vladimir.data.UserData;
import ru.iaygi.api.tests.vladimir.dto.ResourceDTO;
import ru.iaygi.api.tests.vladimir.dto.UpdateUserViaPutDTO;
import ru.iaygi.api.tests.vladimir.dto.UpdateUserViaPatchDTO;
import ru.iaygi.api.tests.vladimir.dto.UserDTO;

import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.iaygi.api.service.Conditions.statusCode;
import static ru.iaygi.api.tests.vladimir.data.UserData.users;

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

    private UpdateUserViaPatchDTO user;
    private LocalDate date;

    private static Stream<Arguments> idValues() {
        return Stream.of(
                Arguments.of("1", users.get(1)),
                Arguments.of("2", users.get(2)),
                Arguments.of("3", users.get(3)),
                Arguments.of("4", users.get(4)),
                Arguments.of("5", users.get(5)),
                Arguments.of("6", users.get(6))
        );
    }

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @BeforeEach
    void prepare() {
        user = UserData.userRandom();
        date = new LocalDate();
    }

    @AfterEach
    void cleanup() {

    }

    @Test
    @DisplayName("Получение данных")
    @Description("Проверить получение данных")
    void getResourceId() {

        step("Получить данные", () -> {
            var res = RestMethod.getResourceId().shouldHave(statusCode(200))
                    .getResponseAs("data", ResourceDTO.class);

            step("Проверить данные", () -> {
                assertThat(res).extracting("id", "name", "year", "color", "pantone_value")
                        .contains(2, "fuchsia rose", 2001, "#C74375", "17-2031");
            });
        });
    }

    @Test
    @DisplayName("Обновление пользователя")
    @Description("Обновить пользователя методом PUT")
    void updateUserViaPut() {

        step("Обновить пользователя", () -> {
            UpdateUserViaPutDTO updateUserViaPutDTO = new UpdateUserViaPutDTO()
                    .name("morpheus")
                    .job("zion resident");
            var res = RestMethod.updateUserViaPut(updateUserViaPutDTO).shouldHave(statusCode(200))
                    .getResponseAs(UpdateUserViaPutDTO.class);

            step("Проверить обновленные данные и дату обновления", () -> {
                assertThat(res).extracting("name", "job")
                        .contains("morpheus", "zion resident");
                assertThat(res.updatedAt()).contains(date.toString());
            });
        });
    }

    @ParameterizedTest(name = "Получение пользователя по id: {0}")
    @MethodSource("idValues")
    @DisplayName("ParameterizedTest: ")
    @Description("Проверить получение пользователя по id")
    void getUserId(String userId, String userName) {

        step("Получить пользователя по id", () -> {
            RestMethod.getUserId(userId).shouldHave(statusCode(200));
        });

        step("Проверить id и first_name у полученного пользователя", () -> {
            var result = RestMethod.getUserId(userId).getResponseAs("data", UserDTO.class);
            assertThat(result).extracting("id", "first_name")
                    .contains(parseInt(userId), userName);
        });
    }

    @Test
    @DisplayName("Обновление пользователя с использованием javafaker")
    @Description("Обновить пользователя методом PATCH")
    void updateUserViaPatch() {

        UpdateUserViaPatchDTO updateUser = new UpdateUserViaPatchDTO()
                .name(user.name())
                .job(user.job());

        var result = RestMethod.updateUserViaPatch(updateUser)
                .getResponseAs(UpdateUserViaPatchDTO.class);

        step("Обновить пользователя", () -> {
            result.shouldHave(statusCode(200));
        });

        step("Проверить обновленные данные и дату обновления", () -> {
            assertThat(result).extracting("name", "job")
                    .contains(user.name(), user.job());
            assertThat(result.updatedAt()).contains(date.toString());
        });
    }
}

