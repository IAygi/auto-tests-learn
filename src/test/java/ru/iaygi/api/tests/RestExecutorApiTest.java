package ru.iaygi.api.tests;

import io.qameta.allure.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.iaygi.api.data.UserData;
import ru.iaygi.api.service.TestBaseApi;
import ru.iaygi.dto.UpdateUserDTO;
import ru.iaygi.dto.UserDTO;

import java.util.List;
import java.util.stream.Stream;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.iaygi.api.data.UserData.userRandom;
import static ru.iaygi.api.service.Conditions.statusCode;

@Severity(NORMAL)
@Owner("iaygi")
@Tag("api_test")
@Tag("regression")
@Epic("Users")
@Feature("Работа с пользователями через API")
public class RestExecutorApiTest extends TestBaseApi {

    private UserDTO user;
    private List<UserDTO> allUsers;
    private static final String LAT_CHARS = "abcdefghijklmnopqrstuvwxyz";

    private static Stream<Arguments> validValues() {
        return Stream.of(
                Arguments.of("3 символа", RandomStringUtils.random(3, LAT_CHARS)),
                Arguments.of("4 символа", RandomStringUtils.random(4, LAT_CHARS)),
                Arguments.of("99 символов", RandomStringUtils.random(99, LAT_CHARS)),
                Arguments.of("100 символов", RandomStringUtils.random(100, LAT_CHARS)),
                Arguments.of("50 символов", RandomStringUtils.random(50, LAT_CHARS))
        );
    }

    private static Stream<Arguments> invalidValues() {
        return Stream.of(
                Arguments.of("пустой", ""),
                Arguments.of("пробел", " "),
                Arguments.of("1 символ", RandomStringUtils.random(1, LAT_CHARS)),
                Arguments.of("2 символа", RandomStringUtils.random(2, LAT_CHARS)),
                Arguments.of("101 символ", RandomStringUtils.random(101, LAT_CHARS)),
                Arguments.of("102 символ", RandomStringUtils.random(102, LAT_CHARS)),
                Arguments.of("200 символов", RandomStringUtils.random(200, LAT_CHARS))
        );
    }

    @BeforeAll
    public static void setUp() {
        init();
    }

    @BeforeEach
    void prepare() {
        user = UserData.userRandom();
    }

    @AfterEach
    void cleanup() {
        user = null;
    }

    /**
     * Использование билдера и Allure Steps
     */
    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверить корректное создание пользователя")
    void createUser() {

        step("Создать пользователя", () -> {
            restMethods.createUser(user).shouldHave(statusCode(201));
        });

        step("Получить список пользователей", () -> {
            allUsers = restMethods.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UserDTO.class);
        });

        step("Проверить наличие созданного пользователя в списке пользователей", () -> {
            assertThat(allUsers).extracting("login", "name", "surname")
                    .contains(tuple(user.login(), user.name(), user.surname()));
        });

        step("Удалить пользователя по login", () -> {
            restMethods.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @Test
    @Tag("smoke")
    @DisplayName("Получение списка пользователей")
    @Description("Проверить корректное получение списка пользователей")
    void getAllUsers() {

        step("Получить список пользователей", () -> {
            var res = restMethods.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UserDTO.class);

            step("Проверить наличие созданного пользователя в списке пользователей", () -> {
                assertThat(res).extracting("login", "name", "surname")
                        .contains(tuple("admin", "admin", "admin"));
            });
        });
    }

    @Test
    @Tag("smoke")
    @DisplayName("Получение пользователя по login")
    @Description("Проверить корректное получение пользователя по login")
    void getUser() {

        step("Получить пользователя admin", () -> {
            var res = restMethods.getUser("admin").shouldHave(statusCode(200)).getResponseAs(UserDTO.class);
            assertAll(
                    () -> {
                        assertThat(res.login()).isEqualTo("admin");
                    },
                    () -> {
                        assertThat(res.name()).isEqualTo("admin");
                    },
                    () -> {
                        assertThat(res.surname()).isEqualTo("admin");
                    }
            );
        });
    }

    @Test
    @DisplayName("Получение несуществующего пользователя по login")
    @Description("Проверить, что при получении несуществующего пользователя по login ответ 404")
    void getNotRealUser() {
        String login = userRandom().login() + "-not-real";

        step("Получить пользователя по login", () -> {
            restMethods.getUser(login).shouldHave(statusCode(404));
        });
    }

    @Test
    @DisplayName("Обновление имени пользователя по login")
    @Description("Проверить корректное обновление имени пользователя по login")
    void updateUserNameByLogin() {
        String name = userRandom().name();

        step("Создать пользователя", () -> {
            restMethods.createUser(user);
        });

        step("Обновить имя пользователя по login", () -> {
            UpdateUserDTO updateUserDTO = new UpdateUserDTO()
                    .login(user.login())
                    .column("name")
                    .value(name);
            restMethods.updateUser(updateUserDTO).shouldHave(statusCode(200)).getResponseAs(UserDTO.class);
        });

        step("Проверить наличие изменённого пользователя в списке пользователей", () -> {
            var res = restMethods.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UserDTO.class);
            assertThat(res).extracting("login", "name")
                    .contains(tuple(user.login(), name));
        });

        step("Удалить пользователя по login", () -> {
            restMethods.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @Test
    @DisplayName("Обновление фамилии пользователя по login")
    @Description("Проверить корректное бновление фамилии пользователя по login")
    void updateUserSurnameByLogin() {
        String surname = userRandom().surname();

        step("Создать пользователя", () -> {
            restMethods.createUser(user);
        });

        step("Обновить фамилию пользователя по login", () -> {
            UpdateUserDTO updateUserDTO = new UpdateUserDTO()
                    .login(user.login())
                    .column("surname")
                    .value(surname);
            restMethods.updateUser(updateUserDTO).shouldHave(statusCode(200)).getResponseAs(UserDTO.class);
        });

        step("Проверить наличие изменённого пользователя в списке пользователей", () -> {
            var res = restMethods.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UserDTO.class);
            assertThat(res).extracting("login", "surname")
                    .contains(tuple(user.login(), surname));
        });

        step("Удалить пользователя по login", () -> {
            restMethods.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @Test
    @DisplayName("Удаление пользователя по login")
    @Description("Проверить корректное удаление пользователя по login")
    void deleteNotRealUserByLogin() {

        step("Создать пользователя", () -> {
            restMethods.createUser(user);
            var res = restMethods.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UserDTO.class);
            assertThat(res).extracting("login", "name", "surname")
                    .contains(tuple(user.login(), user.name(), user.surname()));
        });

        step("Удалить пользователя по login", () -> {
            restMethods.deleteUser(user.login()).shouldHave(statusCode(204));
        });

        step("Проверить отсутствие удалённого пользователя в списке пользователей", () -> {
            var res = restMethods.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UserDTO.class);
            assertThat(res).extracting("login", "name")
                    .doesNotContain(tuple(user.login(), user.name()));
        });
    }

    @Test
    @DisplayName("Удаление несуществующего пользователя по login")
    @Description("Проверить, что при удалении несуществующего пользователя по login ответ 404")
    void deleteUserByLogin() {
        String login = userRandom().login() + "-not-real";

        step("Удалить пользователя по login", () -> {
            restMethods.deleteUser(login).shouldHave(statusCode(404));
        });
    }

    /**
     * Параметризованные тесты
     */
    @ParameterizedTest(name = "Создание пользователя с валидным логином: {0}")
    @MethodSource("validValues")
    @DisplayName("ParameterizedTest: ")
    @Description("Проверить, что пользователь создаётся с валидным логином")
    void createUserWithValidLogin(String key, String value) {

        step("Создать пользователя", () -> {
            restMethods.createUser(user.login(value)).shouldHave(statusCode(201));
        });

        step("Проверить наличие созданного пользователя в списке пользователей", () -> {
            var res = restMethods.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UserDTO.class);
            assertThat(res).extracting("login", "name", "surname")
                    .contains(tuple(user.login(), user.name(), user.surname()));
        });

        step("Удалить пользователя по login", () -> {
            restMethods.deleteUser(user.login()).shouldHave(statusCode(204));
        });
    }

    @ParameterizedTest(name = "Создание пользователя с невалидным логином: {0}")
    @MethodSource("invalidValues")
    @DisplayName("ParameterizedTest: ")
    @Description("Проверить, что пользователь не создаётся с невалидным логином")
    void createUserWithInvalidLogin(String key, String value) {

        step("Создать пользователя", () -> {
            restMethods.createUser(user.login(value)).shouldHave(statusCode(400));
        });

        step("Проверить отсутствие пользователя в списке пользователей", () -> {
            var res = restMethods.getAllUsers().shouldHave(statusCode(200)).getResponseAsList(UserDTO.class);
            assertThat(res).extracting("login", "name")
                    .doesNotContain(tuple(user.login(), value));
        });
    }
}
