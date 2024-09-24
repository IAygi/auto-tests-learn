package ru.iaygi.ui.service;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static ru.iaygi.common.EndPoints.BASE_URL;
import static ru.iaygi.ui.data.TestData.*;

public class TestBaseUi {

    public static ChromeOptions options;
    private static RemoteWebDriver driver;

    @Step("Настройка конфигурации браузера")
    public static void initDriver(boolean useSelenoid) {
        SelenideLogger.addListener("allure", new AllureSelenide());

        Configuration.baseUrl = BASE_URL;
        Configuration.browserSize = "1920x1080";

        if (!useSelenoid) {
            Configuration.holdBrowserOpen = true;
        }

        if (useSelenoid) {
            options = new ChromeOptions();
            options.setCapability("browserVersion", "124.0");
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            options.setCapability("selenoid:options", new HashMap<String, Object>() {
                {
                    put("name", "Website Test");
                    put("sessionTimeout", "30m");
                    put("enableVNC", enableVNC);
                    put("screenResolution", "1920x1080x24");
                    put("env", new ArrayList<String>() {
                        {
                            add("TZ=UTC");
                        }
                    });
                    put("labels", new HashMap<String, Object>() {
                        {
                            put("manual", "true");
                        }
                    });
                    put("enableVideo", enableVideo);
                }
            });

            try {
                driver = new RemoteWebDriver(new URL(SELENOID), options);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            driver.manage().window().setSize(new Dimension(1920, 1080));
            WebDriverRunner.setWebDriver(driver);
        }
    }

    @Step("Закрытие драйвера")
    public static void closeDriver(boolean useSelenoid) {
        if (useSelenoid) {
            driver.quit();
        }
    }
}
