package ru.iaygi.api.tests.vladimir.data;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    public static Map<Integer, String> users = new HashMap<>() {
        {
            put(1, "George");
            put(2, "Janet");
            put(3, "Emma");
            put(4, "Eve");
            put(5, "Charles");
            put(6, "Tracey");
        }
    };
}
