package ru.iaygi.ui.service;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.codeborne.selenide.Selenide.$;

public class TestResultLoggerExtension implements TestWatcher, AfterAllCallback {
    private List<TestResultStatus> testResultsStatus = new ArrayList<>();
    private Path path = Path.of("build/reports/tests/");

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {

    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        TestWatcher.super.testDisabled(context, reason);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        TestWatcher.super.testSuccessful(context);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        TestWatcher.super.testAborted(context, cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        TestWatcher.super.testFailed(context, cause);

        String imageName = context.getRequiredTestMethod().getName() + ".png";
        File actualFile = ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.FILE);

        try {
            FileUtils.copyFile(actualFile, new File(path + "/" + imageName));
            attachImageToAllure(imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED;
    }

    public void attachImageToAllure(String imageName) {
        try {
            File expectedFile = new File(path + "/" + imageName);
            byte[] expectedImage = Files.readAllBytes(expectedFile.toPath());
            saveScreenshot("FAILED / " + imageName, expectedImage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Attachment(value = "{name}", type = "image/png")
    private static byte[] saveScreenshot(String name, byte[] image) {
        return image;
    }
}
