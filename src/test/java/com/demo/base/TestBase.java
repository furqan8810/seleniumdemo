package com.demo.base;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.demo.factory.Browser;
import com.demo.factory.BrowserFactory;
import com.demo.factory.BrowserFactoryProvider;
import com.demo.listeners.CustomListeners;
import com.demo.utilities.ElementUtils;
import com.demo.utilities.ExcelReader;
import com.demo.utilities.TestUtil;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class TestBase {
    public static WebDriver driver;
    public static Properties config=new Properties();
    public static Properties OR=new Properties();
    public static FileInputStream fis;
    public static Logger log= Logger.getLogger("DevPiLogger");
    public static ElementUtils elementUtils;
    private BrowserFactory browserFactory;
    private Browser browser;
    public static ExcelReader excel = new ExcelReader(
            System.getProperty("user.dir") + "/src/test/resources/excel/testdata.xlsx");
    public static WebDriverWait wait;

    @BeforeSuite
    public void setUp()
    {
        try {
            fis=new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/properties/Config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            config.load(fis);
            log.debug("config file loaded !!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            fis=new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/properties/OR.properties");
            log.debug("properties have been loaded");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            OR.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        browserFactory = BrowserFactoryProvider.getBrowserFactory(config);
        browser = browserFactory.createBrowser();
        driver=browser.open();
        driver.get(config.getProperty("testsiteurl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wait")), TimeUnit.SECONDS);
        elementUtils=new ElementUtils(driver);
    }
    public void click(String locator) {

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }
        CustomListeners.testReport.get().log(Status.INFO, "Clicking on : " + locator);
    }

    public void type(String locator, String value) {

        if (locator.endsWith("_CSS")) {
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_XPATH")) {
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        } else if (locator.endsWith("_ID")) {
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }

        CustomListeners.testReport.get().log(Status.INFO, "Typing in : " + locator + " entered value as " + value);

    }

    static WebElement dropdown;

    public void select(String locator, String value) {

        if (locator.endsWith("_CSS")) {
            dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        } else if (locator.endsWith("_XPATH")) {
            dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
        } else if (locator.endsWith("_ID")) {
            dropdown = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropdown);
        select.selectByVisibleText(value);

        CustomListeners.testReport.get().log(Status.INFO, "Selecting from dropdown : " + locator + " value as " + value);

    }

    public boolean isElementPresent(By by) {

        try {

            driver.findElement(by);
            return true;

        } catch (NoSuchElementException e) {

            return false;

        }

    }

    public static void verifyEquals(String expected, String actual) throws IOException {

        try {

            Assert.assertEquals(actual, expected);

        } catch (Throwable t) {

            TestUtil.captureScreenshot();
            // ReportNG
            Reporter.log("<br>" + "Verification failure : " + t.getMessage() + "<br>");
            Reporter.log("<a target=\"_blank\" href=" + TestUtil.screenshotName + "><img src=" + TestUtil.screenshotName
                    + " height=200 width=200></img></a>");
            Reporter.log("<br>");
            Reporter.log("<br>");
            // Extent Reports
            CustomListeners.testReport.get().log(Status.FAIL, " Verification failed with exception : " + t.getMessage());
            CustomListeners.testReport.get().fail("<b>" + "<font color=" + "red>" + "Screenshot of failure" + "</font>" + "</b>",
                    MediaEntityBuilder.createScreenCaptureFromPath(TestUtil.screenshotName)
                            .build());

        }

    }

    @AfterSuite
    public void tearDown(){
        if(driver!=null)
        driver.quit();
    }
}
