package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.openqa.selenium.By.*;

public class MultiplayerPage extends Page {
    private static final String MULTIPLAYER_URL = "https://10ff.net/login";

    public MultiplayerPage(DriverWrapper driverWrapper) {
        super(driverWrapper);
    }

    @Override
    public void navigate() {
        driverWrapper.get(MULTIPLAYER_URL);
    }

    public void play() {
        String name = "Blue switches";

        driverWrapper.findElement(id("username")).sendKeys(name);
        driverWrapper.findElement(By.xpath("//input[@type='submit']")).click();

        startNewGame();
    }

    private void startNewGame() {
        waitForOverlayer();

        WebElement input = driverWrapper.waitUntil(driver -> driver.findElement(xpath("//input[@type='text']")));
        WebElement words = driverWrapper.waitUntil(driver -> driver.findElement(className("place")));

        enterWords(words, input);
    }

    private void waitForOverlayer() {
        WebElement overlayer = driverWrapper.waitUntil(driver -> driver.findElement(className("overlayer")));
        // waiting for additional players
        driverWrapper.waitUntil(driver -> !overlayer.getText().equals(""), Duration.of(30, ChronoUnit.SECONDS));
        int seconds = Integer.parseInt(overlayer.getText());

        driverWrapper.waitUntil(driver -> !Arrays.asList(overlayer
                .getAttribute("class")
                .split(" ")).contains("active"),
                Duration.of(seconds + 1, ChronoUnit.SECONDS));
    }
}
