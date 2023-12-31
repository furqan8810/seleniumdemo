package com.demo.base;

import com.demo.factory.Browser;
import com.demo.factory.BrowserFactory;
import com.demo.factory.BrowserFactoryProvider;
import com.demo.utilities.ElementUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    @AfterSuite
    public void tearDown(){
        if(driver!=null)
        driver.quit();
    }
}
