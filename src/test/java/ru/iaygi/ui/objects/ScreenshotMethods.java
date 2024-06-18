package ru.iaygi.ui.objects;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

import static com.codeborne.selenide.Condition.exist;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ScreenshotMethods {

    private static final Path PATH = Path.of("src/test/resources/img/");

    public void simpleScreenshot(SelenideElement selenideElement, String imageName, boolean toCreate) {
        SelenideElement element = selenideElement.should(exist, Duration.ofMillis(10_000));
        ImageDiff diff = null;
        BufferedImage diffImage = null;
        BufferedImage actualImage = null;
        BufferedImage expectedImage = null;
        File actualFile = element.getScreenshotAs(OutputType.FILE);

        try {
            actualImage = ImageIO.read(actualFile);
            if (toCreate) {
                FileUtils.copyFile(actualFile, new File(PATH + "/expected/" + imageName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File expectedFile = new File(PATH + "/expected/" + imageName);
            expectedImage = ImageIO.read(expectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (expectedImage != null && actualImage != null) {
            diff = new ImageDiffer().makeDiff(actualImage, expectedImage);
            diffImage = diff.getMarkedImage();
        }

        if (diffImage != null && diff.hasDiff()) {
            try {
                File diffFile = new File(PATH + "/different/" + imageName);
                ImageIO.write(diffImage, "png", diffFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileUtils.copyFile(actualFile, new File(PATH + "/actual/" + imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (diff.hasDiff()) {
            attachImageToAllure(imageName);
            assertFalse(diff.hasDiff());
        }
    }

    public void screenshotWithExclude(SelenideElement selenideElement, Set<By> webElements,
                                      String imageName, boolean toCreate) {
        WebDriver driver = WebDriverRunner.getWebDriver();
        SelenideElement element = selenideElement.should(exist, Duration.ofMillis(10_000));
        ImageDiff diff = null;
        BufferedImage diffImage = null;
        BufferedImage actualImage = null;
        Screenshot expectedImage = null;

        Screenshot elementScreenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .ignoredElements(webElements)
                .coordsProvider(new WebDriverCoordsProvider())
                .takeScreenshot(driver, element);

        actualImage = elementScreenshot.getImage();

        if (toCreate) {
            try {
                ImageIO.write(actualImage, "PNG",
                        new File(PATH + "/expected/" + imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            expectedImage = new Screenshot(ImageIO.read(new File(PATH + "/expected/" + imageName)));
            expectedImage.setIgnoredAreas(elementScreenshot.getIgnoredAreas());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (expectedImage != null) {
            diff = new ImageDiffer().makeDiff(elementScreenshot, expectedImage);
            diffImage = diff.getMarkedImage();
        }

        if (diffImage != null && diff.hasDiff()) {
            try {
                File diffFile = new File(PATH + "/different/" + imageName);
                ImageIO.write(diffImage, "png", diffFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                ImageIO.write(actualImage, "PNG",
                        new File(PATH + "/actual/" + imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (diff.hasDiff()) {
            attachImageToAllure(imageName);
            assertFalse(true);
        }
    }

    public void cropScreenshot(SelenideElement selenideElement,
                                              String imageName, Map crop, boolean toCreate) {

        SelenideElement element = selenideElement.should(exist, Duration.ofMillis(10_000));
        ImageDiff diff = null;
        BufferedImage diffImage = null;
        BufferedImage actualImage = null;
        BufferedImage expectedImage = null;
        File actualFile = element.getScreenshotAs(OutputType.FILE);

        try {
            actualImage = ImageIO.read(actualFile);
            int widthImage = actualImage.getWidth() - (Integer) crop.get("w");
            int heightImage = actualImage.getHeight() - (Integer) crop.get("h");
            actualImage = actualImage.getSubimage((Integer) crop.get("x"), (Integer) crop.get("y"),
                    widthImage, heightImage);

            if (toCreate) {
                File expectedFile = new File(PATH + "/expected/" + imageName);
                ImageIO.write(actualImage, "png", expectedFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            File expectedFile = new File(PATH + "/expected/" + imageName);
            expectedImage = ImageIO.read(expectedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (expectedImage != null && actualImage != null) {
            diff = new ImageDiffer().makeDiff(actualImage, expectedImage);
            diffImage = diff.getMarkedImage();
        }

        if (diffImage != null && diff.hasDiff()) {
            try {
                File diffFile = new File(PATH + "/different/" + imageName);
                ImageIO.write(diffImage, "png", diffFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                FileUtils.copyFile(actualFile,
                        new File(PATH + "/actual/" + imageName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (diff.hasDiff()) {
            attachImageToAllure(imageName);
            assertFalse(true);
        }
    }


    public void attachImageToAllure(String imageName) {
        try {
            File expectedFile = new File(PATH + "/expected/" + imageName);
            byte[] expectedImage = Files.readAllBytes(expectedFile.toPath());
            saveScreenshot("Expected / " + imageName, expectedImage);

            File actualFile = new File(PATH + "/actual/" + imageName);
            byte[] actualImage = Files.readAllBytes(actualFile.toPath());
            saveScreenshot("Actual / " + imageName, actualImage);

            File difFile = new File(PATH + "/different/" + imageName);
            byte[] difImage = Files.readAllBytes(difFile.toPath());
            saveScreenshot("Different / " + imageName, difImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "{name}", type = "image/png")
    private static byte[] saveScreenshot(String name, byte[] image) {
        return image;
    }
}
