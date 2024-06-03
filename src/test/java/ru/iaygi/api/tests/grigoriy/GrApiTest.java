package ru.iaygi.api.tests.grigoriy;

import io.qameta.allure.*;
import io.restassured.RestAssured;
import lombok.Getter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.iaygi.api.tests.grigoriy.dto.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
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
@Owner("Galyamskiy")
@Tag("api_test")
@Epic("Учусь автотестировать")
@Feature("API тесты")
public class GrApiTest extends Methods {

    private BooksDto listOfBooks;
    private List<String> listOfIsbn;
    private UserBookStoreDto userBookStore;
    private CreateUserDto user;

    @Getter
    static UserBookStoreDto newUser = UserDataGr.randomUserBookStore();

    private CreateUserDto result;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @BeforeEach
    void prepare() {}

    @AfterEach
    void cleanup() {}

    @Test
    @DisplayName("Получить ресурс по id")
    @Description("Проверить, что по id возвращается нужный ресурс")
    void getResourceId() {

        step("Получить ресурс по id", () -> {
            var resource = getResourceWithId().shouldHave(statusCode(200)).getResponseAs("data", ResourceDto.class);
            assertAll(
                    () -> assertThat(resource.id()).isEqualTo(2),
                    () -> assertThat(resource.name()).isEqualTo("fuchsia rose"),
                    () -> assertThat(resource.year()).isEqualTo(2001),
                    () -> assertThat(resource.color()).isEqualTo("#C74375"),
                    () -> assertThat(resource.pantone_value()).isEqualTo("17-2031"));
        });
    }

    @Test
    @DisplayName("Создание пользователя")
    @Description("Проверить создание пользователя")
    void createUser() {

        step("Создать пользователя", () -> {
            CreateUserDto createUserDto = new CreateUserDto().name("morpheus").job("leader");
            var result = createUser(createUserDto).shouldHave(statusCode(201)).getResponseAs(CreateUserDto.class);
            assertThat(result).extracting("name", "job", "createdAt").contains("morpheus", "leader");
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
            var result = getUser(argument).shouldHave(statusCode(200)).getBodyByPath("data.first_name");
            assertThat(result).contains(firstName.get(argument));
        });
    }

    @Test
    @DisplayName("Обновить пользователя")
    @Description("Проверить обновление пользователя")
    void updateUser() {
        user = UserDataGr.randomUser();

        step("Обновить пользователя", () -> {
            result = updateUser(user).shouldHave(statusCode(200)).getResponseAs(CreateUserDto.class);
        });

        step("Проверить что пользователь обновился", () -> {
            assertThat(result).extracting("name", "job").contains(user.name(), user.job());
        });
    }

    @Test
    @DisplayName("Создание пользователя и добавление ему в каталог книги")
    @Description("Проверить корректное создание пользователя и добавление ему книги в профиль")
    void CreateUserInBookStoreAndAddBook() {

        step("Создать нового пользователя", () -> {
            userBookStore = createUserBookStore(newUser).shouldHave(statusCode(201)).getResponseAs(UserBookStoreDto.class);
        });

        step("Получить общий список книг", () -> {
            listOfBooks = getAllBooks().shouldHave(statusCode(200)).getResponseAs(BooksDto.class);
        });

        step("Получить список isbn из общего списка книг", () -> {
            listOfIsbn = listOfBooks.books().stream().map(BooksItemDto::isbn).collect(Collectors.toList());
        });

        step("Добавить книгу в список пользователя", () -> {
            List<CollectionOfIsbnsItemDto> collectionOfIsbnsItemDto =
                    Collections.singletonList(new CollectionOfIsbnsItemDto().isbn(listOfIsbn.get(0)));

            AddUserBookDto addUserBookDto =
                    new AddUserBookDto().userId(userBookStore.userID()).collectionOfIsbns(collectionOfIsbnsItemDto);

            addBookToUser(addUserBookDto).shouldHave(statusCode(201)).getResponseAs(CollectionOfIsbnsItemDto.class);
        });

        step("Проверить наличие книги в профиле пользователя", () -> {
            var result = getUserAccount(userBookStore.userID())
                    .shouldHave(statusCode(200))
                    .getResponseAs(BooksDto.class);

            assertThat(result.books().get(0))
                    .extracting("isbn", "title", "subTitle", "author")
                    .contains(
                            listOfBooks.books().get(0).isbn(),
                            listOfBooks.books().get(0).title(),
                            listOfBooks.books().get(0).subTitle(),
                            listOfBooks.books().get(0).author());
        });
    }
}