package ru.iaygi.db.objects;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.iaygi.dto.CustomersDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DbMethods {

    @Step("DB: getCustomers")
    public List getCustomers(ResultSet resultSet) {
        log.debug("GET all customers");
        List<CustomersDTO> listObjects;

        try {
            listObjects = new ArrayList<>();
            while (resultSet.next()) {
                CustomersDTO customers = new CustomersDTO();
                customers.id(resultSet.getInt("id"));
                customers.name(resultSet.getString("name"));
                customers.email(resultSet.getString("email"));
                listObjects.add(customers);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listObjects;
    }
}
