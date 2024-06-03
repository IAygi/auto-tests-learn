package ru.iaygi.api.tests.grigoriy;

import ru.iaygi.api.tests.grigoriy.dto.CreateUserDto;
import ru.iaygi.api.tests.grigoriy.dto.UserBookStoreDto;

public class UserDataGr extends FakeDataGr {

    public static CreateUserDto randomUser() {
        return new CreateUserDto().name(name()).job(job());
    }

    public static UserBookStoreDto randomUserBookStore() {
        return new UserBookStoreDto().userName(username()).password(password());
    }
}
