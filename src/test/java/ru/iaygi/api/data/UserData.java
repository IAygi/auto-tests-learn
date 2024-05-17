package ru.iaygi.api.data;

import lombok.NoArgsConstructor;
import ru.iaygi.dto.UserDTO;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserData extends FakeData {

    public static UserDTO userRandom() {
        return new UserDTO()
                .login(login())
                .name(firstName())
                .surname(lastName())
                .city(cityName())
                .age(String.valueOf(number()));
    }

    public static UserDTO createUser() {
        return new UserDTO()
                .login(userRandom().login())
                .name(userRandom().name())
                .surname(userRandom().surname())
                .city(userRandom().city())
                .age(userRandom().age());
    }
}