package ru.iaygi.api.tests.vladimir.tests;

import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.*;
import ru.iaygi.api.tests.vladimir.RestMethod;
import ru.iaygi.api.tests.vladimir.dto.*;

import java.util.*;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static java.lang.Integer.parseInt;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.iaygi.api.service.Conditions.statusCode;
import static ru.iaygi.api.tests.vladimir.data.UserData.randomUser;

@Severity(NORMAL)
@Owner("vpakhomo")
@Tag("api_test")
@Epic("BookStore")
@Feature("Работа с пользователями и книгами")
public class FinalApiTest {

    private AccountDTO user;
    private BookStoreDTO addedBook;
    private List<BookStoreDTO> booksList;
    private AccountDTO token;
    private String getBookIsbn;

    @BeforeAll
    public static void setUp() {
    }

    @BeforeEach
    void prepare() {
        //user = randomUser();
    }

    @AfterEach
    void cleanup() {
    }

    @Test
    @DisplayName("Добавление книги в профиль пользователя")
    @Description("Проверить, что книга добавилась в профиль")
    void addBookToProfile() {

        user = new AccountDTO()
               .userName("Lonnie")
               .password("8i@lFxai");


       // step("Создать пользователя", () -> {
         //   RestMethod.createUser(user).shouldHave(statusCode(201));
        //});

        step("Получить токен", () -> {
            token = RestMethod.generateToken(user).shouldHave(statusCode(200)).getResponseAs(AccountDTO.class);
        });

        step("Произвести логин", () -> {
            RestMethod.authorize(user)
                    .header("Authorization",
                            token.toString());
        });

        step("Получить список доступных книг", () -> {
            booksList = RestMethod.getBooksList().shouldHave(statusCode(200))
                    .getResponseAsJson().getList("books", BookStoreDTO.class);
        });

        step("Добавить книгу в список пользователя", () -> {
            getBookIsbn = booksList.get(0).isbn();
            addedBook = RestMethod.addBook("ba096992-2314-4b42-941a-616d4ef4e2a2",getBookIsbn)
                    .shouldHave(statusCode(200)).getResponseAs(BookStoreDTO.class);

        });

        step("Проверить, что книга добавилась в профиль пользователя", () -> {

            var usersBooks = RestMethod.getUserBooks(user.userId()).shouldHave(statusCode(200))
                    .getResponseAs(BookStoreDTO.class);

            assertAll(
                    () -> assertThat(usersBooks.isbn()).isEqualTo(addedBook.isbn()),
                    () -> assertThat(usersBooks.title()).isEqualTo(addedBook.title()),
                    () -> assertThat(usersBooks.subTitle()).isEqualTo(addedBook.subTitle()),
                    () -> assertThat(usersBooks.author()).isEqualTo(addedBook.author())
            );
        });
        /*
        Optional

        step("Удалить книгу из профиля", () -> {
        });

        step("Проверить, что книга удалилась из профиля пользователя", () -> {
        });
         */
    }
}
