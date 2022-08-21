package pages;

import dev.failsafe.function.CheckedRunnable;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.openqa.selenium.By.className;

public abstract class Page {
    protected final DriverWrapper driverWrapper;

    private static long DELAY;
    private static int DISPERSION;

    static {
        wrap(() ->
            Files.lines(Paths.get(System.getProperty("user.dir"),
                    "src", "main", "resources", "application.properties"))
            .forEach(l -> {
                if (l.startsWith("delay")) {
                    String delay = l.substring(l.lastIndexOf("=") + 1);
                    DELAY = delay.isBlank() ? 1 : Long.parseLong(delay);
                }
                if (l.startsWith("dispersion")) {
                    String dispersion = l.substring(l.lastIndexOf("=") + 1);
                    DISPERSION = dispersion.isBlank() ? 1 : Integer.parseInt(dispersion);
                }
            })
        );
    }

    protected Page(DriverWrapper driverWrapper) {
        this.driverWrapper = driverWrapper;
    }

    public abstract void navigate();

    public void quit() {
        driverWrapper.execute().quit();
    }

    protected void enterWords(WebElement words, WebElement input) {
        WebElement highlightedWord = driverWrapper.waitUntil(driver -> words.findElement(className("highlight")));

        while (highlightedWord != null) {
            wrap(() -> Thread.sleep(DELAY + new Random().nextInt(DISPERSION)));

            input.sendKeys(highlightedWord.getText());
            input.sendKeys(" ");

            try {
                highlightedWord = driverWrapper.waitUntil(driver -> words.findElement(className("highlight")));
            } catch (TimeoutException e) {
                highlightedWord = null;
            }
        }
    }

    private static void wrap(CheckedRunnable runnable) {
        try {
            runnable.run();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
