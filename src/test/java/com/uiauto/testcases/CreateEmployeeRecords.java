package com.uiauto.testcases;

import com.uiauto.base.BaseTest;
import com.uiauto.pages.HomePage;
import com.uiauto.pages.PIMPage;
import com.uiauto.report.ExtentReport;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Map;

public class CreateEmployeeRecords extends BaseTest {

    public static String firstName;
    public static String lastName;

    @BeforeTest
    public void setData(){
        dataSheetName = "Employee List";
    }

    public void setValues(Map<String, String> map){
        firstName = map.get("FirstName");
        lastName = map.get("LastName");
    }

    @Test(dataProvider = "fetchData")
    public void createEmployees(Map<String, String> map){
        setValues(map);
        ExtentReport.createTest("Create and verify an Employee record - "+firstName);
        //Create an employee and verify the employee records
        new HomePage()
                .clickPIMTab()
                .clickAdd()
                .enterFirstName(firstName)
                .enterLastName(lastName)
                .clickSave()
                .clickPIMTab()
                .searchEmployee(firstName)
                .verifyName(firstName);
        writeToWord("Verified that Employee record -> FirstName : "+firstName+" LastName :"+lastName+" is created successfully",false,true);
    }

    @AfterClass
    public void navigateToHomeScreen(){
        new PIMPage()
                .clickDashboardTab();
    }
}
