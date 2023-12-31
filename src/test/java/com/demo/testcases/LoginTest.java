package com.demo.testcases;

import com.demo.base.TestBase;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {
    @Test
    public void loginAsBankManager(){
        driver.findElement(By.cssSelector(OR.getProperty("bmlBtn"))).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assert.assertTrue(elementUtils.isElementPresent(By.cssSelector(OR.getProperty("addCustBtn"))),"Login not succesful");
    }
}
