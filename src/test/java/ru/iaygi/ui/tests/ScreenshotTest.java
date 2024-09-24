package ru.iaygi.ui.tests;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import ru.iaygi.ui.objects.ScreenshotObject;
import ru.iaygi.ui.service.TestBaseUi;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static ru.iaygi.ui.data.TestData.enableSelenoid;

@Owner("iaygi")
@Severity(CRITICAL)
@Tag("regression")
@Epic("WebSite")
@Feature("Основная функциональность")
public class ScreenshotTest extends TestBaseUi {

    private ScreenshotObject screenshotObject = new ScreenshotObject();
    private static final boolean USE_SELENOID = enableSelenoid;
    public static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        initDriver(USE_SELENOID);
    }

    @AfterAll
    public static void tearDown() {
        closeDriver(USE_SELENOID);
    }

    @Test
    void checkScreenshot() {
        step("Открыть страницу", () -> {
            open("https://allurereport.org/docs/");
        });
        step("Проверить скриншот заголовка", () -> {
            screenshotObject.checkScreenshot($("#allure-report-documentation"), "simpleTestImage", false);
        });
    }

    @Test
    void checkExcludeScreenshot() {
        step("Открыть страницу", () -> {
            open("https://allurereport.org/docs/restassured/");
        });

        step("Проверить шапку статьи", () -> {
            screenshotObject.checkScreenshotWithExclude("excludeTestImage", false);
        });
    }

    @Test
    void checkCropScreenshot() {
        step("Открыть главную страницу", () -> {
            open("https://allurereport.org/docs/restassured/");
        });

        step("Проверить скриншот элемента", () -> {
            screenshotObject.checkCropScreenshot($(".content-body"),
                    "cropTestImage", false, 0, 0, 250, 0);
        });
    }
}
