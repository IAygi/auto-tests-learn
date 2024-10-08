package ru.iaygi.ui.objects;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LeagueObject {

    public void openMainPage() {
        open("https://digitalleague.ru");
    }

    public void careerMenuHover() {
        $$(".topMenu__itemWrapper--lU1op > a").get(2).hover();
    }

    public void clickToItem() {
        $$(".topMenu__dropdown--xeJnl > a[href=\"/career\"]").get(0).click();
    }

    public void checkHeader() {
        $("h2").should(exactText("Работа в Лиге — это возможности!")).shouldBe(visible);
        Allure.attachment("Проверка заголовка", "Расхождений нет");
    }
}
