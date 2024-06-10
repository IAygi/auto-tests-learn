package ru.iaygi.api.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class FakeData {

    private static final Faker faker = new Faker(new Locale("ru-RU"));

    public static String login() {
        return fakerResult(faker.name().username());
    }

    public static String firstName() {
        return fakerResult(faker.name().firstName());
    }

    public static String lastName() {
        return fakerResult(faker.name().lastName());
    }

    public static String fullName() {
        return fakerResult(faker.name().fullName());
    }

    public static String cityName() {
        return fakerResult(faker.address().city());
    }

    public static String email() {
        return faker.internet().emailAddress(String.valueOf(new Locale("en-GB")));
    }

    public static int number() {
        return faker.number().numberBetween(18, 60);
    }

    public static int numbers(int length) {
        return Integer.parseInt(faker.number().digits(5));
    }

    public static String fakerResult(String result) {
        return result.replaceAll("[.^=\"':,]", "-");
    }
}
