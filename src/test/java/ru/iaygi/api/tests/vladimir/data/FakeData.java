package ru.iaygi.api.tests.vladimir.data;

import com.github.javafaker.Faker;

public class FakeData {

    private static final Faker faker = new Faker();

    public static String name() {
        return faker.name().nameWithMiddle();
    }

    public static String job() {
        return faker.job().title();
    }
    public static String userName() {
        return faker.name().firstName();
    }
    public static String password() {
        return faker.internet().password(8,9,true,
                true,true);
    }
    //Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'),
    // one lowercase ('a'-'z'), one special character and Password must be eight characters or longer
}

