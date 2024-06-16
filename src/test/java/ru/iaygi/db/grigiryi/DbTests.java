package ru.iaygi.db.grigiryi;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.TRIVIAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static ru.iaygi.db.grigiryi.Sql.GET_PRODUCT_TYPE;
import static ru.iaygi.db.grigiryi.Sql.GET_SALES;

@Severity(TRIVIAL)
@Tag("db")
@Epic("Users")
@Feature("Рбота с БД")
public class DbTests extends ConnectDb {
    private ResultSet resultSet;
    private List<ProductDto> list;
    private MethodsDb methodsDb = new MethodsDb();
    private Sql sql = new Sql();


    @BeforeEach
    void prerare() {}

    @AfterEach
    void cleanup() {
        connectClose();
    }

    @Test
    @DisplayName("Обновление информации о продукте из БД")
    @Description("Проверить корректное обновление")
    void UpdateProduct() {
        int id = 1;
        String name = "Очное занятие";

        step("Обновить информацию о типе продукта", () -> {
            updateRequest(sql.updateProduct(name));
        });

        step("Получить список типов продуктов", () -> {
            resultSet = getRequest(GET_PRODUCT_TYPE);
        });

        step("Получить коллекцию типов продуктов", () -> {
            list = methodsDb.getProductType(resultSet);
        });

        step("Проверить что данные обновились", () -> {
            assertThat(list).extracting("id", "type_name").contains(tuple(id, name));
        });
    }
    @Test
    @DisplayName("Удаление информации из бд")
    @Description("Проверить корректное удаление")
    void DeleteProduct() {
    int productId = 1;
    int orderId = 1;

        step("Удалить запись из таблицы sales", ()-> {
            deleteRequest(sql.deleteSales(productId));
        });

        step("Получить список из sales", () -> {
            resultSet = getRequest(GET_SALES);
        });

        step("Получить коллекцию sales", () -> {
            list = methodsDb.getSales(resultSet);
        });

        step("Проверить что запись удалилась", ()-> {
           assertThat(list).extracting("product_id", "order_id").doesNotContain(tuple(productId, orderId));
    });
}
}
