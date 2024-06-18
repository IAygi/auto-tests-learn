package ru.iaygi.ui.objects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.codeborne.selenide.Selenide.$;

public class ScreenshotObject {

    private ScreenshotMethods screenObject = new ScreenshotMethods();

    public void checkScreenshot(SelenideElement selenideElement, String imageName, boolean toCreate) {
        screenObject.simpleScreenshot(selenideElement, imageName + ".png", toCreate);
    }

    public void checkScreenshotWithExclude(String imageName, boolean toCreate) {
        SelenideElement element = $("[class=\"tm-article-snippet tm-article-snippet tm-article-presenter__snippet\"]");
        By webElement = By.cssSelector("[data-test-id=\"articleStats\"]");
        By webElement2 = By.cssSelector("[class=\"tm-article-datetime-published\"]");
        Set<By> ignoredElements = new HashSet<>();
        ignoredElements.add(webElement);
        ignoredElements.add(webElement2);
        screenObject.screenshotWithExclude(element, ignoredElements, imageName + ".png", toCreate);
    }

    public void checkCropScreenshot(SelenideElement selenideElement, String imageName, boolean toCreate, Integer... values) {
        Map<String, Integer> cropValues = Map.of(
                "x", values[0],
                "y", values[1],
                "w", values[2],
                "h", values[3]
        );
        screenObject.cropScreenshot(selenideElement, imageName + ".png", cropValues, toCreate);
    }
}
