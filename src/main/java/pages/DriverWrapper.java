package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Function;

public class DriverWrapper {
    private final WebDriver webDriver;

    private static final Duration DEFAULT_TIMEOUT = Duration.of(10, ChronoUnit.SECONDS);

    public DriverWrapper(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public WebDriver execute() {
        return webDriver;
    }

    public void get(String url) {
        webDriver.get(url);
    }

    public WebElement findElement(By by) {
        return webDriver.findElement(by);
    }

    public WebElement findElementQuietly(By by) {
        try {
            return webDriver.findElement(by);
        } catch (java.util.NoSuchElementException e) {
            return null;
        }
    }

    public <T> T waitUntil(Function<WebDriver, T> condition) {
        return waitUntil(condition, DEFAULT_TIMEOUT);
    }

    public <T> T waitUntil(Function<WebDriver, T> condition, Duration timeout) {
        return new WebDriverWait(webDriver, timeout)
                .ignoreAll(List.of(
                        NoSuchElementException.class,
                        UnhandledAlertException.class,
                        StaleElementReferenceException.class))
                .until(condition);
    }
}
