package ru.gpn.db.tests;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
import ru.gpn.db.data.Sql;
import ru.gpn.db.objects.DbMethods;
import ru.gpn.db.service.DbConnect;
import ru.gpn.dto.UsersDTO;

import java.sql.ResultSet;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Severity(CRITICAL)
@Tag("db_test")
@Tag("smouke")
@Epic("Users")
@Feature("Работа с пользователями через БД")
public class DbTests extends DbConnect {

    Sql sql = new Sql();
    ResultSet resultSet;
    DbMethods dbMethods = new DbMethods();
    List<UsersDTO> list;

    @BeforeEach
    void prepare() {

    }

    @AfterEach
    void cleanup() {
        connectClose();
    }


    @Test
    @DisplayName("Получение списка пользователей из БД")
    @Description("Проверить корректное получение списка пользователей из БД")
    void getAllUsers() {
        step("Сделать SQL запрос на получение пользователей", () -> {
            resultSet = getRequest(sql.getAllUsers);
        });

        step("Получить коллекцию пользователей", () -> {
            list = dbMethods.getAllUsers(resultSet);
        });

        step("Убедиться, что в списке есть базовй пользователь", () -> {
            assertThat(list).extracting("login", "name", "surname")
                    .contains(tuple("admin", "admin", "admin"));
        });
    }
}
