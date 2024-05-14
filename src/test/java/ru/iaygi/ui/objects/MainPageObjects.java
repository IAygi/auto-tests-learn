package ru.iaygi.ui.objects;

import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainPageObjects {

    @Step("Открыть главную страницу")
    public void openMainPage() {
        open("/");
        sleep(1_000);
    }

    @Step("Проверить заголовок страницы")
    public void checkPageTitle(String value) {
        $(".page-title").should(exactText(value)).shouldBe(exist, Duration.ofMillis(2_000));
        sleep(1_000);
    }

    @Step("Проверить наличие изображения в главной галерее")
    public void checkMainGallery() {
        $(".swiper-slide-image").should(exist, Duration.ofMillis(2_000)).shouldHave(image);
        sleep(1_000);
    }

    @Step("Проверить наличие подгалереи")
    public void checkSubGallery() {
        assertTrue($(".foogallery").scrollIntoView(true).isDisplayed());
    }
}
