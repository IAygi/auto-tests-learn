package ru.iaygi.db.data;

public class Sql {

    public static final String GET_CUSTOMERS = "SELECT * FROM customers";

    public String insertCustomer(int id, String name, String email) {
        return  String.format("INSERT INTO public.customers (id, \"name\", email) VALUES(%d, '%s', '%s')", id, name, email);
    }
}
