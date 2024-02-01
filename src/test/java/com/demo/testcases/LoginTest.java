package com.demo.testcases;

import com.demo.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest extends TestBase {
    @Test
    public void loginAsBankManager() throws IOException, InterruptedException {
        verifyEquals("abc", "xyz");
        Thread.sleep(3000);
        log.debug("Inside Login Test");
        click("bmlBtn_CSS");

        Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustBtn_CSS"))), "Login not successful");

        log.debug("Login successfully executed");
    }
}
