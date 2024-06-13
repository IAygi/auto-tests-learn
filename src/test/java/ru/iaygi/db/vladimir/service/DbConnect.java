package ru.iaygi.db.vladimir.service;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;

import static ru.iaygi.db.vladimir.DbData.DbData.*;

@Slf4j
public class DbConnect {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    @Step("SQL select")
    public ResultSet getRequest(String sqlRequest) {
        prepareToRequest();

        try {
            resultSet = statement.executeQuery(sqlRequest);
            log.info("JDBC query executed successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    @Step("SQL insert")
    public void insertRequest(String sqlRequest) {
        prepareToRequest();

        try {
            statement.execute(sqlRequest);
            log.info("JDBC query executed successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("SQL update")
    public void updateRequest(String sqlRequest) {
        prepareToRequest();

        try {
            statement.execute(sqlRequest);
            log.info("JDBC query executed successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("SQL delete")
    public void deleteRequest(String sqlRequest) {
        prepareToRequest();

        try {
            statement.execute(sqlRequest);
            log.info("JDBC query executed successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("SQL create")
    public void createRequest(String sqlRequest) {
        prepareToRequest();

        try {
            statement.execute(sqlRequest);
            log.info("JDBC query executed successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("SQL drop")
    public void dropRequest(String sqlRequest) {
        prepareToRequest();

        try {
            statement.execute(sqlRequest);
            log.info("JDBC query executed successfully");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Step("Create connection & statement")
    public void prepareToRequest() {
        log.info("DB connection with sql");
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            log.info("Successfully connected to the database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        log.info("Create statement");
        try {
            statement = connection.createStatement();
            log.info("Statement created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Step("Close connection")
    public void connectClose() {
        log.debug("Close connection");
        try {
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}