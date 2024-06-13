package ru.iaygi.db.vladimir.service;

import io.qameta.allure.Step;
import ru.iaygi.db.vladimir.DbData.Sql;
import ru.iaygi.dto.CustomersDTO;

import static ru.iaygi.db.vladimir.DbTest.tableName;
import static ru.iaygi.api.tests.vladimir.data.FakeData.*;

public class DataBaseService extends DbConnect {
    private Sql sql = new Sql();

    public static CustomersDTO customerDTO() {
        return new CustomersDTO()
                .name(name())
                .email(email());
    }

    public static final CustomersDTO customer = customerDTO();

    @Step("Предварительное создание таблицы")
    public void createNewTable() {
        createRequest(sql.createTable(tableName));
    }

    @Step("Заполнение таблицы начальными данными")
    public void addInitialData() {
        insertRequest(sql.insertCustomer(tableName, 1, customer.name(), customer.email()));
    }

    @Step("Удаление таблицы после прохождения теста")
    public void deleteCreatedTable() {
        dropRequest(sql.deleteTable(tableName));
    }
}