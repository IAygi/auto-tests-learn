package ru.iaygi.ui.vladimir;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PageObjects {

    @Step("Открыть главную страницу")
    public void openPage() {
        open("https://the-internet.herokuapp.com/login");
    }

    @Step("Получить на странице логин и пароль, авторизоваться под ними")
    public void authorize() {
        String login = $("#content > div > h4 > em:nth-child(1)").getText();
        String password = $("#content > div > h4 > em:nth-child(2)").getText();
        $("#username").type(login);
        $("#password").type(password);
        $("button").click();
    }

    @Step("Проверить заголовок после авторизации, выйти")
    public void checkTitle() {
        sleep(3_000);
        String title = $("#content > div > h4").getText();
        assertThat(title).isEqualTo("Welcome to the Secure Area. When you are done click logout below.");
        $("#content > div > a").click();
    }
}
