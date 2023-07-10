package com.itppm.base;

import com.github.javafaker.Faker;
import com.google.common.util.concurrent.Uninterruptibles;
import com.itppm.pages.fsales.SalesLoginPage;
import com.itppm.report.ExtentReport;
import com.itppm.utils.ReadExcelFile;
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
            new SalesLoginPage()
                    .clickSignInGoogle()
                    .enterGoogleEmail(getPropertyValue("gmailusername"))
                    .clickGoogleNextWithWait()
                    .clickGoogleNext()
                    .enterEmail(getPropertyValue("username"))
                    .clickNext()
                    .enterPassword(getPropertyValue("password"))
                    .clickCloneSignin()
                    .clickOnContinue();
        Uninterruptibles.sleepUninterruptibly(30, TimeUnit.SECONDS);
    }
}
