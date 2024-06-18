package ru.iaygi.ui.tests;

import com.codeborne.selenide.junit5.TextReportExtension;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.iaygi.ui.objects.ScreenshotObject;
import ru.iaygi.ui.service.TestBaseUi;
import ru.iaygi.ui.service.TestResultLoggerExtension;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static ru.iaygi.common.EndPoints.ALLURE_REST_ASSURED;
import static ru.iaygi.common.EndPoints.HABR_POST;
import static ru.iaygi.ui.data.TestData.enableSelenoid;
import static ru.iaygi.ui.objects.Elements.ARTICLE_SNIPPET;

@Owner("iaygi")
@Severity(CRITICAL)
@Tag("ui_test")
@Tag("regression")
@Epic("WebSite")
@Feature("Основная функциональность")
@ExtendWith({TextReportExtension.class, TestResultLoggerExtension.class})
public class ScreenshotTest extends TestBaseUi {

    private ScreenshotObject screenshotObject = new ScreenshotObject();
    private static final boolean USE_SELENOID = enableSelenoid;

    @BeforeAll
    public static void setUp() {
        initDriver(USE_SELENOID);
    }

    @BeforeEach
    public void init() {
    }

    @AfterEach
    public void clear() {
    }

    @AfterAll
    public static void tearDown() {
        closeDriver(USE_SELENOID);
    }

    @Test
    void checkScreenshot() {
        step("Открыть главную страницу", () -> {
            open(ALLURE_REST_ASSURED);
        });

        step("Проверить скриншот элемента", () -> {
            screenshotObject.checkScreenshot($("#allure-rest-assured"), "simpleTestImage", false);
        });
    }

    @Test
    void checkExcludeScreenshot() {
        step("Открыть главную страницу", () -> {
            open(HABR_POST);
        });

        step("Проверить скриншот элемента", () -> {
            screenshotObject.checkScreenshotWithExclude("excludeTestImage", false);
        });
    }

    @Test
    void checkCropScreenshot() {
        step("Открыть главную страницу", () -> {
            open(HABR_POST);
        });

        step("Проверить скриншот элемента", () -> {
            screenshotObject.checkCropScreenshot($(ARTICLE_SNIPPET),
                    "cropTestImage", true, 0, 30, 0, 90);
        });

    }
}
