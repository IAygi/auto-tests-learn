package ru.iaygi.db.vladimir.service;

import io.qameta.allure.Step;
import ru.iaygi.db.vladimir.DbData.Sql;
import ru.iaygi.db.vladimir.DbData.CustomersData;
import ru.iaygi.dto.CustomersDTO;

public class DataBaseService extends DbConnect {

    private Sql sql = new Sql();
    public static final CustomersDTO customer = CustomersData.randomCustomer();

    @Step("Предварительное создание таблицы")
    public void createNewTable() {
        createRequest(sql.createTable("Customers"));
    }

    @Step("Заполнение таблицы начальными данными")
    public void addInitialData() {
        insertRequest(sql.insertCustomer("Customers", 1, customer.name(), customer.email()));
    }

    @Step("Удаление таблицы после прохождения теста")
    public void deleteCreatedTable() {
        dropRequest(sql.deleteTable("Customers"));
    }
}
