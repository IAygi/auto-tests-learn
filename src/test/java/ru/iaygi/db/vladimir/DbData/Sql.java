package ru.iaygi.db.vladimir.DbData;

public class Sql {
    public String getAllCustomers(String tableName) {
        return String.format("SELECT * FROM %s", tableName);
    }

    public String insertCustomer(String tableName, int id, String name, String email) {
        return String.format("INSERT INTO %s", tableName)
                + String.format(" (id, \"name\", email) VALUES (%d, '%s', '%s')", id, name, email);
    }

    public String updateCustomer(String tableName, int id, String name, String email) {
        return String.format("UPDATE %s", tableName)
                + String.format(" SET (id, \"name\", email) = (%d, '%s', '%s')", id, name, email)
                + String.format(" WHERE id = %d", id);
    }

    public String deleteCustomer(String tableName, int id) {
        return String.format("DELETE FROM %s", tableName) + String.format(" WHERE id = %d", id);
    }

    public String createTable(String tableName) {
        return String.format("CREATE TABLE %s", tableName) + String.format(" (id int, name varchar, email varchar)");
    }

    public String deleteTable(String tableName) {
        return String.format("DROP TABLE %s", tableName);
    }
}