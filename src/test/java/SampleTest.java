import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class SampleTest {
    enum Platform {Android, IOS}
    Platform platform = Platform.Android;

    private AppiumDriver driver;
    private MobileObjects mobileObjects;
    private String textToSetEmpty = " ";
    private String textToSetActivity = "Helloy!:)";

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @BeforeEach
    public void setUp() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);
        if (platform == Platform.Android) {
            desiredCapabilities.setCapability("platformName", "android");
            desiredCapabilities.setCapability("appium:deviceName", "some name");
            desiredCapabilities.setCapability("appium:appPackage", "ru.netology.testing.uiautomator");
            desiredCapabilities.setCapability("appium:appActivity", ".MainActivity");
            desiredCapabilities.setCapability("appium:automationName", "uiautomator2");
            driver = new AndroidDriver(getUrl(), desiredCapabilities);
        }
        mobileObjects = new MobileObjects(driver);
    }

    @Test
    public void testEmptyStringText() {
        mobileObjects.userInput.isDisplayed();
        mobileObjects.userInput.sendKeys(textToSetEmpty);

        mobileObjects.textToBeChanged.isDisplayed();
        String expected = mobileObjects.textToBeChanged.getText();

        mobileObjects.buttonChange.isDisplayed();
        mobileObjects.buttonChange.click();

        Assert.assertEquals(expected, mobileObjects.textToBeChanged.getText());
    }

    @Test
    public void testForButtonActivityText() {
        mobileObjects.userInput.isDisplayed();
        mobileObjects.userInput.sendKeys(textToSetActivity);

        mobileObjects.buttonActivity.isDisplayed();
        mobileObjects.buttonActivity.click();

        mobileObjects.text.isDisplayed();
        Assertions.assertEquals(textToSetActivity, mobileObjects.text.getText());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}