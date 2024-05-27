package ru.iaygi.api.tests.grigoriy;

import com.github.javafaker.Faker;

public class FakeDataGr {

    private static final Faker faker = new Faker();

    public static String fakerResult(String result) {
        return result.replaceAll("[.^=\"':,]", "-");
    }

    public static String name() {
        return fakerResult(faker.name().firstName());
    }

    public static String job() {
        return fakerResult(faker.job().title());
    }
}