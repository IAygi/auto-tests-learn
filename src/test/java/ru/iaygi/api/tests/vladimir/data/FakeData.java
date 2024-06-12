package ru.iaygi.api.tests.vladimir.data;

import com.github.javafaker.Faker;

public class FakeData {

    private static final Faker faker = new Faker();

    public static String name() {
        return faker.name().name();
    }

    public static String job() {
        return faker.job().title();
    }

    public static String userName() {
        return faker.name().firstName();
    }

    public static String email() {
        return faker.internet().emailAddress();
    }
}

