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
import static org.junit.jupiter.api.Assertions.assertAll;
import static ru.iaygi.db.vladimir.service.DataBaseService.customer;

@Severity(NORMAL)
@Tag("db_test")
@Tag("smoke")
@Epic("Customers")
@Feature("Работа с пользователями через БД")
public class DbTest extends DbConnect {

    public static final String tableName = "public.customers";
    private static final DataBaseService dataBaseService = new DataBaseService();
    private static String name;
    private static String email;
    private ResultSet resultSet;
    private List<CustomersDTO> list;
    private DbMethods dbMethods = new DbMethods();
    private Sql sql = new Sql();

    @BeforeAll
    public static void setUp() {
        name = customer.name();
        email = customer.email();
    }

    @BeforeEach
    void prepare() {
        dataBaseService.createNewTable();
        dataBaseService.addInitialData();
    }

    @AfterEach
    void cleanup() {
        dataBaseService.deleteCreatedTable();
        connectClose();
    }

    @Test
    @DisplayName("Получение списка покупателей")
    @Description("Проверить покупателя в списке покупателей")
    void getAllCustomers() {
        int id = 1;

        step("Сделать SQL запрос на получение покупателей", () -> {
            resultSet = getRequest(sql.getAllCustomers(tableName));
        });

        step("Получить коллекцию покупателей и проверить покупателя", () -> {
            list = dbMethods.getCustomers(resultSet);
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
            resultSet = getRequest(sql.getAllCustomers(tableName));
        });

        step("Получить коллекцию покупателей", () -> {
            list = dbMethods.getCustomers(resultSet);
        });

        step("Сделать SQL запрос на добавление покупателя", () -> {
            insertRequest(sql.insertCustomer(tableName, list.size() + 1, name, email));
        });

        step("Сделать SQL запрос на получение покупателей", () -> {
            resultSet = getRequest(sql.getAllCustomers(tableName));
        });

        step("Получить коллекцию покупателей и проверить добавление покупателя", () -> {
            list = dbMethods.getCustomers(resultSet);
            assertThat(list).extracting("id", "name", "email")
                    .contains(tuple(list.get(1).id(), name, email));
        });
    }

    @Test
    @DisplayName("Редактирование покупателя")
    @Description("Проверить редактирование покупателя")
    void updateCustomer() {
        int id = 1;
        name = FakeData.name();
        email = FakeData.email();

        step("Сделать SQL запрос на редактирование покупателя", () -> {
            updateRequest(sql.updateCustomer(tableName, id, name, email));
        });

        step("Сделать SQL запрос на получение покупателей", () -> {
            resultSet = getRequest(sql.getAllCustomers(tableName));
        });

        step("Получить коллекцию покупателей и проверить, что данные о покупателе обновились", () -> {
            list = dbMethods.getCustomers(resultSet);
            assertAll(
                    () -> assertThat(list.get(0)).extracting("id").isEqualTo(id),
                    () -> assertThat(list.get(0)).extracting("name").isEqualTo(name),
                    () -> assertThat(list.get(0)).extracting("email").isEqualTo(email)
            );
        });
    }

    @Test
    @DisplayName("Удаление покупателей из списка")
    @Description("Проверить удаление покупателя")
    void deleteCustomer() {
        int id = 1;

        step("Сделать SQL запрос на удаление покупателя", () -> {
            deleteRequest(sql.deleteCustomer(tableName, id));
        });

        step("Сделать SQL запрос на получение покупателей", () -> {
            resultSet = getRequest(sql.getAllCustomers(tableName));
        });

        step("Получить коллекцию покупателей", () -> {
            list = dbMethods.getCustomers(resultSet);
            assertThat(list).extracting("id", "name", "email").doesNotContain(tuple(id, name, email));
        });
    }
}