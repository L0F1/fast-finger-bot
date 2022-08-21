import org.openqa.selenium.chrome.ChromeDriver;
import pages.DriverWrapper;
import pages.MainPage;
import pages.MultiplayerPage;

import java.nio.file.Paths;

public class Main {
    private static DriverWrapper driverWrapper;

    private static void init() {
        System.setProperty("webdriver.chrome.driver",
                Paths.get(System.getProperty("user.dir"), "chromedriver.exe").toString());

        driverWrapper = new DriverWrapper(new ChromeDriver());
    }

    public static void main(String[] args) throws InterruptedException {
        init();

        multiplayer();
    }

    private static void typingTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driverWrapper);
        mainPage.navigate();
        mainPage.typingTest();
    }

    private static void multiplayer() throws InterruptedException {
        MultiplayerPage multiplayerPage = new MultiplayerPage(driverWrapper);
        multiplayerPage.navigate();
        multiplayerPage.play();
    }
}
