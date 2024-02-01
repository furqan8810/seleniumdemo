package com.demo.testcases;

import com.demo.base.TestBase;
import com.demo.utilities.TestUtil;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class AddCustomerTest extends TestBase {
    @Test(dataProviderClass= TestUtil.class,dataProvider="dp")
    public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException{

        if(!data.get("runmode").equals("Y")){

            throw new SkipException("Skipping the test case as the Run mode for data set is NO");
        }


        click("addCustBtn_CSS");
        type("firstname_CSS",data.get("firstname"));
        type("lastname_XPATH",data.get("lastname"));
        type("postcode_CSS",data.get("postcode"));
        click("addbtn_CSS");
        Thread.sleep(2000);
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
        alert.accept();

        Thread.sleep(2000);

    }
}
