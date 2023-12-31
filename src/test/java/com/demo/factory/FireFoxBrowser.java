package com.demo.factory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FireFoxBrowser implements Browser{
    @Override
    public WebDriver open() {
        System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"/src/test/resources/executables/geckodriver");
        return new FirefoxDriver();
    }
}
