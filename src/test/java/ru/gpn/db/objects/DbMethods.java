package ru.gpn.db.objects;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.gpn.dto.UsersDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DbMethods {

    @Step("DB: getAllUsers")
    public List getAllUsers(ResultSet resultSet) {
        log.debug("GET all users: {resultSet}");
        List<UsersDTO> listObjects;

        try {
            listObjects = new ArrayList<>();
            while (resultSet.next()) {
                UsersDTO users = new UsersDTO();
                users.id(resultSet.getInt("id"));
                users.login(resultSet.getString("login"));
                users.name(resultSet.getString("name"));
                users.surname(resultSet.getString("surname"));
                users.age(resultSet.getString("age"));
                users.city(resultSet.getString("city"));
                listObjects.add(users);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listObjects;
    }

    @Step("DB: getUser")
    public List getUser(ResultSet resultSet) {
        log.debug("GET user: {resultSet}");
        List<UsersDTO> listObjects;

        try {
            listObjects = new ArrayList<>();
            while (resultSet.next()) {
                UsersDTO users = new UsersDTO();
                users.id(resultSet.getInt("id"));
                users.login(resultSet.getString("login"));
                users.name(resultSet.getString("name"));
                users.surname(resultSet.getString("surname"));
                users.age(resultSet.getString("age"));
                users.city(resultSet.getString("city"));
                listObjects.add(users);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listObjects;
    }

    @Step("DB: postUser")
    public List postUser(ResultSet resultSet) {
        log.debug("POST user: {resultSet}");
        List<UsersDTO> listObjects;

        try {
            listObjects = new ArrayList<>();
            while (resultSet.next()) {
                UsersDTO users = new UsersDTO();
                users.id(resultSet.getInt("id"));
                users.login(resultSet.getString("login"));
                users.name(resultSet.getString("name"));
                users.surname(resultSet.getString("surname"));
                users.age(resultSet.getString("age"));
                users.city(resultSet.getString("city"));
                listObjects.add(users);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listObjects;
    }
}
