package ru.iaygi.api.tests;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import ru.iaygi.api.data.UserData;
import ru.iaygi.api.service.TestBaseApi;
import ru.iaygi.dto.UserDTO;

import static io.qameta.allure.SeverityLevel.NORMAL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

@Severity(NORMAL)
@Owner("iaygi")
@Tag("api_test")
@Tag("regression")
@Epic("Users")
@Feature("Работа с пользователями через API")
public class BaseApiTest extends TestBaseApi {

    private UserDTO user;

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
     * Базовая реализация
     */
    @Test
    @Tag("smoke")
    @DisplayName("Получение списка пользователей")
    @Description("Проверить корректное получение списка пользователей")
    void getAllUsersSimple() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
            .when()
                .get("/test_api/users.php")
            .then()
                .log().all()
                .assertThat().statusCode(200)
                .and().body("login", hasItem("admin"));
    }

    @Test
    @Tag("smoke")
    @DisplayName("Создание пользователя")
    @Description("Проверить корректное создание пользователя")
    void createUserSimple() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(user)
            .when()
                .post("/test_api/add_user.php")
            .then()
                .log().all()
                .assertThat().statusCode(201)
                .and().body("login", containsString(user.login()));
    }
}
