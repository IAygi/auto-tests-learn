package ru.iaygi.db.data;

public class DbData {
    public static final String psqlUrl = System.getProperty("db_url", "jdbc:postgresql://localhost:5432/postgres");
    public static final String dbUser = System.getProperty("db_user", "postgres");
    public static final String dbPassword = System.getProperty("db_password", "your_password");
}
