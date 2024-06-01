package ru.iaygi.api.tests.vladimir.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.iaygi.api.tests.vladimir.RestMethod;
import ru.iaygi.api.tests.vladimir.dto.*;

import java.util.*;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
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
    private BookStoreDTO user;
    private List<BookStoreDTO> booksList;
    private String isbn;
    private String userId;
    private String token;
    private BookStoreDTO bookInfo;

    @BeforeAll
    public static void setUp() {
    }

    @BeforeEach
    void prepare() {
        user = randomUser();
    }

    @AfterEach
    void cleanup() {
    }

    @Test
    @DisplayName("Добавление книги в профиль пользователя")
    @Description("Провести полный цикл создания пользователя и редактирования списка книг в профиле")
    void addBookToProfile() {

        step("Создать пользователя и получить его идентификатор", () -> {
            var getUserId = RestMethod.createUser(user).shouldHave(statusCode(201)).getResponseAs(BookStoreDTO.class);
            userId = getUserId.userID;
        });

        step("Произвести логин", () -> {
            RestMethod.authorize(user).shouldHave(statusCode(200));
        });

        step("Получить токен", () -> {
            var getToken = RestMethod.generateToken(user).shouldHave(statusCode(200)).getResponseAs(BookStoreDTO.class);
            token = getToken.token;
        });

        step("Выбрать книгу из списка доступных книг", () -> {
            booksList = RestMethod.getBooksList().shouldHave(statusCode(200)).getResponseAsJson()
                    .getList("books", BookStoreDTO.class);
            isbn = booksList.get(0).isbn();
            bookInfo = booksList.get(0);
        });

        step("Добавить книгу в профиль пользователя", () -> {
            RestMethod.addBook(userId, isbn, token).shouldHave(statusCode(201)).getResponseAs(BookStoreDTO.class);
        });

        step("Проверить, что книга добавилась в профиль пользователя", () -> {
            var userBookList = RestMethod.getUserBooks(userId, token).shouldHave(statusCode(200))
                    .getResponseAsJson().getList("books", BookStoreDTO.class);
            assertAll(
                    () -> assertThat(userBookList.get(0)).extracting("isbn")
                            .isEqualTo(isbn),
                    () -> assertThat(userBookList.get(0)).extracting("title")
                            .isEqualTo(bookInfo.title()),
                    () -> assertThat(userBookList.get(0)).extracting("subTitle")
                            .isEqualTo(bookInfo.subTitle()),
                    () -> assertThat(userBookList.get(0)).extracting("author")
                            .isEqualTo(bookInfo.author())
            );
        });

        step("Удалить книгу из профиля", () -> {
            RestMethod.deleteBook(isbn, userId, token).shouldHave(statusCode(204));
        });

        step("Проверить, что книга удалилась из профиля пользователя", () -> {
            var checkDeletedBooks = RestMethod.getUserBooks(userId, token).shouldHave(statusCode(200))
                    .getResponseAs(BookStoreDTO.class);
            assertAll(
                    () -> assertThat(checkDeletedBooks.userId()).isEqualTo(userId),
                    () -> assertThat(checkDeletedBooks.books()).doesNotContain(isbn)
            );
        });

        step("Удалить пользователя", () -> {
            RestMethod.deleteUser(userId, token).shouldHave(statusCode(204));
        });
    }
}
