package com.uiauto.base;

import com.github.javafaker.Faker;
import com.google.common.util.concurrent.Uninterruptibles;
import com.uiauto.pages.LoginPage;
import com.uiauto.report.ExtentReport;
import com.uiauto.utils.ReadExcelFile;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BaseTest extends BaseMethods {

    public String dataSheetName;
    public Faker faker = new Faker(new Locale("en-IND"));

    @BeforeSuite
    public void beforeSuite(){
        ExtentReport.initReports();
    }

    @BeforeSuite
    public void setUp() throws Exception {
        initializeDriver(getPropertyValue("browser"));
        login();
    }

    @AfterSuite
    public void tearDown() throws IOException {
        driver.quit();
        ExtentReport.flushReports();
    }

    @DataProvider(name="fetchData")
    public Object[] getData(){
        return ReadExcelFile.getSheet(dataSheetName);
    }

    public void login() throws Exception {
            new LoginPage()
                    .enterUsername(getPropertyValue("username"))
                    .enterPassword(getPropertyValue("password"))
                    .clickLoginButton();
        Uninterruptibles.sleepUninterruptibly(7, TimeUnit.SECONDS);
    }
}
