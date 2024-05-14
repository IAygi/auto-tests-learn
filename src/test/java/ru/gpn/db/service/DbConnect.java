package ru.gpn.db.service;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.gpn.db.data.DbData;
import ru.gpn.dto.UsersDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DbConnect {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Step("SQL request")
    public ResultSet getRequest(String sqlRequest) {
        log.debug("DB connection with sql: {sqlRequest}");
        try {
            connection = DriverManager.getConnection(DbData.dbUrl, DbData.dbUser, DbData.dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            resultSet = statement.executeQuery(sqlRequest);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    @Step("SQL request")
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

    @Step("SQL request")
    public List createUser(ResultSet resultSet) {
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

    @Step("Close connection")
    public void connectClose() {
        log.debug("Close connection");
        try {
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        try {
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
