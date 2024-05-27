package ru.iaygi.api.tests.grigoriy;

import ru.iaygi.dto.UserDTO;

public class UserDataGr extends FakeDataGr {

    public static CreateUserDto randomUser() {
        return new CreateUserDto()
                .name(name())
                .job(job());
    }
}
