package ru.iaygi.ui.grigoriy;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PageObjects {

    @Step("Открыть страницу авторизации")
    public void openAuthorizationPage() {
        open("https://the-internet.herokuapp.com/login");
    }

    @Step("Залогиниться под пользователем tomsmith")
    public void loginViaUser() {
        $("#username").setValue($("h4 em:nth-child(1)").getText());
        $("#password").setValue($("h4 em:nth-child(2)").getText()).pressEnter();
    }

    @Step("Проверить заголовок")
    public void checkHeader(String header) {
        $("h4").should(exactText(header));
    }
}
