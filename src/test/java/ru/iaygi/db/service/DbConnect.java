package ru.iaygi.db.service;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import ru.iaygi.db.data.DbData;
import ru.iaygi.dto.UsersDTO;

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
        log.info("DB connection with sql");
        try {
            connection = DriverManager.getConnection(DbData.psqlUrl, DbData.dbUser, DbData.dbPassword);
            log.info("Successfully connected to the database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            statement = connection.createStatement();
            log.info("Statement created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            resultSet = statement.executeQuery(sqlRequest);
            log.info("JDBC query executed successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    @Step("SQL request")
    public List getAllUsers(ResultSet resultSet) {
        log.debug("GET all users");
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
        log.debug("Create user");
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
