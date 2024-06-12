package ru.iaygi.db.vladimir;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.iaygi.api.tests.vladimir.data.FakeData;
import ru.iaygi.db.objects.DbMethods;
import ru.iaygi.db.vladimir.service.DataBaseService;
import ru.iaygi.db.vladimir.service.DbConnect;
import ru.iaygi.dto.CustomersDTO;
import ru.iaygi.db.vladimir.DbData.Sql;

import java.sql.ResultSet;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Severity(NORMAL)
@Tag("db_test")
@Tag("smoke")
@Epic("Customers")
@Feature("Работа с пользователями через БД")
public class DbTest extends DbConnect {

    private static DataBaseService dataBaseService = new DataBaseService();
    private ResultSet resultSet;
    private List<CustomersDTO> list;
    private DbMethods dbMethods = new DbMethods();
    private static String name;
    private static String email;
    private int listSize;
    private Sql sql = new Sql();


    @BeforeAll
    public static void setUp() {
        dataBaseService.createNewTable();
        dataBaseService.addInitialData();
        name = DataBaseService.customer.name();
        email = DataBaseService.customer.email();
    }

    @BeforeEach
    void prepare() {
    }

    @AfterEach
    void disconnect() {
    }

    @AfterAll
    public static void cleanUp() {
        dataBaseService.deleteCreatedTable();
    }

    @Test
    @DisplayName("Получение списка покупателей")
    @Description("Проверить получение списка покупателей")
    void getAllCustomers() {
        int id = 1;

        step("Сделать SQL запрос на получение покупателей", () -> {
            resultSet = getRequest(sql.getAllCustomers("Customers"));
        });

        step("Получить коллекцию покупателей", () -> {
            list = dbMethods.getCustomers(resultSet);
        });

        step("Проверить покупателя", () -> {
            assertThat(list).extracting("id", "name", "email").contains(tuple(id, name, email));
        });
    }

    @Test
    @DisplayName("Добавление покупателя в список")
    @Description("Проверить добавление покупателя")
    void insertCustomer() {
        name = FakeData.name();
        email = FakeData.email();

        step("Сделать SQL запрос на получение покупателей", () -> {
            resultSet = getRequest(sql.getAllCustomers("Customers"));
        });

        step("Получить коллекцию покупателей", () -> {
            listSize = dbMethods.getCustomers(resultSet).size();
        });

        step("Сделать SQL запрос на добавление покупателя", () -> {
            insertRequest(sql.insertCustomer("Customers", listSize + 1, name, email));
        });
    }

    @Test
    @DisplayName("Редактирование покупателя")
    @Description("Проверить редактирование покупателя")
    void updateCustomer() {
        int id = 1;
        name = FakeData.name();
        email = FakeData.email();

        step("Сделать SQL запрос на получение покупателей", () -> {
            resultSet = getRequest(sql.getAllCustomers("Customers"));
        });

        step("Получить коллекцию покупателей", () -> {
            list = dbMethods.getCustomers(resultSet);
        });

        step("Сделать SQL запрос на редактирование покупателя", () -> {
            updateRequest(sql.updateCustomer("Customers", id, name, email));
        });

    }

    @Test
    @DisplayName("Удаление покупателей из списка")
    @Description("Проверить удаление покупателя")
    void deleteCustomer() {
        int id = 1;

        step("Сделать SQL запрос на удаление покупателя", () -> {
            deleteRequest(sql.deleteCustomer("Customers", id));
        });

        step("Сделать SQL запрос на получение покупателей", () -> {
            resultSet = getRequest(sql.getAllCustomers("Customers"));
        });

        step("Получить коллекцию покупателей", () -> {
            list = dbMethods.getCustomers(resultSet);
        });

        step("Проверить, что покупатель был удален", () -> {
            assertThat(list).extracting("id", "name", "email").doesNotContain(tuple(id));
        });

    }
}
