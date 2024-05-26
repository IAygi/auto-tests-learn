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
}

