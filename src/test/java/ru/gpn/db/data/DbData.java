package ru.gpn.db.data;

public class DbData {
    public static final String dbUrl = System.getProperty("db_url", "jdbc:mysql://");
    public static final String dbUser = System.getProperty("db_user", "");
    public static final String dbPassword = System.getProperty("db_password", "");
}
