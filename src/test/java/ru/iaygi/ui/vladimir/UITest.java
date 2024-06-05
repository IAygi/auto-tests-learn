package ru.iaygi.ui.vladimir;

import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import ru.iaygi.ui.service.TestBaseUi;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.NORMAL;
import static org.assertj.core.api.Assertions.assertThat;

@Severity(NORMAL)
@Owner("vpakhomo")
@Tag("ui_test")
@Epic("UI")
@Feature("Тестирование UI")
public class UITest extends TestBaseUi {

    private static final boolean USE_SELENOID = true;

    @BeforeAll
    public static void setUp() {
        initDriver(USE_SELENOID);
    }

    @BeforeEach
    void prepare() {

    }

    @AfterEach
    void cleanup() {
        closeDriver(USE_SELENOID);
    }

    @Test
    @DisplayName("Проверка значения на странице о стажировках")
    @Description("Проверить условия для поступления на стажировку")
    public void checkInternshipCondition() {

        step("Открыть главную страницу", () -> {
            open("https://career.gazprom-neft.ru");
        });

        step("Проверить заголовок страницы", () -> {
            String title = $("h1").shouldBe(visible).getText();
            assertThat(title).isEqualTo("Карьера в «Газпром нефти»");
        });

        step("Найти ссылку по стажировкам и перейти по ней", () -> {
            $(byTitle("Подробнее о стажировках")).scrollTo().click();
        });

        step("Проверить заголовок страницы", () -> {
            String title = $("h1").shouldBe(visible).getText();
            assertThat(title).contains("Мы приглашаем студентов");
        });

        step("Найти частые вопросы и раскрыть информацию об условиях", () -> {
            $(byClassName("accordion-list")).scrollTo().find("a").click();
        });

        step("Проверить условие попадания под стажировку", () -> {
            String conditionText = $(byId("collapse-101")).find("p").shouldBe(visible).getText();
            assertThat(conditionText).contains("Студент 4 курса бакалавриата");
        });
    }
}
