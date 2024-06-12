package ru.iaygi.db.vladimir.DbData;

import ru.iaygi.dto.CustomersDTO;

import static ru.iaygi.api.tests.vladimir.data.FakeData.*;

public class CustomersData {

    public static CustomersDTO randomCustomer() {
        return new CustomersDTO()
                .name(name())
                .email(email());
    }
}
