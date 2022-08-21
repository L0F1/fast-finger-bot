package pages;

import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.openqa.selenium.By.className;
import static org.openqa.selenium.By.id;

public class MainPage extends Page {
    private static final String FAST_FINGERS_URL = "https://10fastfingers.com/typing-test/russian";

    public MainPage(DriverWrapper driverWrapper) {
        super(driverWrapper);
    }

    @Override
    public void navigate() {
        driverWrapper.get(FAST_FINGERS_URL);
        driverWrapper.waitUntil(driver -> driver.findElement(className("CybotCookiebotDialogContentWrapper")).isDisplayed());
        driverWrapper.findElement(id("CybotCookiebotDialogBodyButtonDecline")).click();
    }

    public void typingTest() {
        WebElement input = driverWrapper.waitUntil(driver -> driver.findElement(id("inputfield")));
        WebElement words = driverWrapper.waitUntil(driver -> driver.findElement(id("words")));

        enterWords(words, input);

        waitTimer();
    }

    private void waitTimer() {
        WebElement timer = driverWrapper.findElement(id("timer"));
        int remainingSeconds = Integer.parseInt(timer.getText().substring(2));
        driverWrapper.waitUntil(driver -> timer.getText().equals("0:00"),
                Duration.of(remainingSeconds + 1, ChronoUnit.SECONDS));
    }
}
