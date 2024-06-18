package ru.iaygi.ui.tests;

import com.codeborne.selenide.junit5.TextReportExtension;
import io.github.artsok.RepeatedIfExceptionsTest;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.iaygi.ui.objects.LeagueObject;
import ru.iaygi.ui.objects.MainPageObjects;
import ru.iaygi.ui.service.TestBaseUi;
import ru.iaygi.ui.service.TestResultLoggerExtension;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iaygi.ui.data.TestData.enableSelenoid;

@Owner("iaygi")
@Severity(CRITICAL)
@Tag("ui_test")
@Tag("regression")
@Epic("WebSite")
@Feature("Основная функциональность")
@ExtendWith({TextReportExtension.class, TestResultLoggerExtension.class})
public class WebsiteTest extends TestBaseUi {

    private static MainPageObjects mainPageObjects;
    private static final boolean USE_SELENOID = enableSelenoid;
    private LeagueObject leagueObject = new LeagueObject();

    @BeforeAll
    public static void setUp() {
        initDriver(USE_SELENOID);
        mainPageObjects = new MainPageObjects();
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

    /**
     * Page Object
     */
    @Test
    @Tag("smoke")
    @Tag("only_one")
    @DisplayName("Проверка главной страницы")
    @Description("Проверить отображение на главной странице заголовка, галереи и подгалереи")
    public void mainPage() {

        mainPageObjects.openMainPage();
        mainPageObjects.checkPageTitle("Фотограф Татьяна Айги");
        mainPageObjects.checkMainGallery();
        mainPageObjects.checkSubGallery();
    }

    @RepeatedTest(value = 2, name = "Проверка страницы Портфолио {currentRepetition}/{totalRepetitions}")
    @Tag("smoke")
    @DisplayName("RepeatedTest: ")
    @Description("Проверить отображение заголовка на странице Портфолио")
    public void portfolioPage() {

        step("Открыть страницу Портфолио", () -> {
            open("/portfolio/");
        });

        step("Проверить заголовок страницы", () -> {
            String title = $(".page-title").shouldBe(visible).getText();
            assertEquals(title, "Портфолио");
        });
    }

    @RepeatedIfExceptionsTest(repeats = 2)
    @Tag("smoke")
    @DisplayName("Проверка страницы Контакты")
    @Description("Проверить отображение заголовка на странице Контакты")
    public void contactsPage() {

        step("Открыть страницу Контакты", () -> {
            open("/contacts/");
        });

        step("Проверить заголовок страницы", () -> {
            assertEquals($(".page-title").getText(), "Контакты?");
        });
    }

    @Test
    @Tag("smoke")
    @DisplayName("Проверка страницы Обо мне")
    @Description("Проверить отображение заголовка на странице Обо мне")
    public void aboutPage() {

        step("Открыть страницу Обо мне", () -> {
            open("/about/");
        });

        step("Проверить заголовок страницы", () -> {
            String title = $(".page-title").shouldBe(visible).getText();
            assertEquals(title, "Обо мне");
        });
    }

    /**
     * Allure Steps & Page Object
     */
    @Test
    void simpleTest() {
        step("Открыть главную страницу", () -> {
            leagueObject.openMainPage();
        });

        step("Навести на пункт меню 'Карьера'", () -> {
            leagueObject.careerMenuHover();
        });

        step("Нажать на '#ЛигаВозможностей' в выпадающем меню", () -> {
            leagueObject.clickToItem();
        });

        step("Проверить текст заголовока", () -> {
            leagueObject.checkHeader();
        });
    }
}
