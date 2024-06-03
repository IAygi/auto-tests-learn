package ru.iaygi.api.tests.vladimir.data;

import ru.iaygi.api.tests.vladimir.dto.BookStoreDTO;
import ru.iaygi.api.tests.vladimir.dto.UpdateUserViaPatchDTO;

import java.util.HashMap;
import java.util.Map;

import static ru.iaygi.api.tests.vladimir.data.FakeData.*;

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

    public static UpdateUserViaPatchDTO userRandom() {
        return new UpdateUserViaPatchDTO()
                .name(name())
                .job(job());
    }

    public static BookStoreDTO randomUser() {
        return new BookStoreDTO()
                .userName(userName())
                .password("%zvVY&2!");
    }
}
