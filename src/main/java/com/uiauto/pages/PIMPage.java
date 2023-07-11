package com.uiauto.pages;

import com.google.common.util.concurrent.Uninterruptibles;
import com.uiauto.base.BaseMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class PIMPage extends BaseMethods {

    @FindBy(xpath = "(//*[text()[contains(.,'Add')]])[2]")
    private WebElement eleAddBtn;

    @FindBy(xpath = "//*[text()[contains(.,'Search')]]")
    private WebElement eleSearchBtn;

    @FindBy(name = "firstName")
    private WebElement eleFirstName;

    @FindBy(name = "lastName")
    private WebElement eleLastName;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement eleSavebtn;

    @FindBy(xpath = "//span[text()='PIM']")
    private WebElement elePIM;

    @FindBy(xpath = "//span[text()='Dashboard']")
    private WebElement eleDashboard;

    @FindBy(xpath = "(//input[@placeholder='Type for hints...'])[1]")
    private WebElement eleEmployeeName;

    @FindBy(xpath = "(//div[@class='oxd-table-card'])[1]/div/div[3]/div")
    private WebElement eleFirstNameRecord;

    @FindBy(xpath = "(//input[@type='checkbox'])[2]")
    private WebElement eleSelectCheckbox;

    @FindBy(xpath = "//*[text()[contains(.,'Delete Selected')]]")
    private WebElement eleDeleteSelected;

    @FindBy(xpath = "//*[text()[contains(.,'Yes, Delete')]]")
    private WebElement eleConfirmDelete;

    @FindBy(xpath = "//*[text()='No Records Found']")
    private WebElement eleNoRecords;

    public PIMPage(){
        PageFactory.initElements(driver,this);
    }

    public PIMPage clickAdd(){
        click(eleAddBtn, "Clicked on Add button");
        return this;
    }

    public PIMPage enterFirstName(String fName){
        type(eleFirstName, fName, "Entered the Firstname - "+fName);
        return this;
    }

    public PIMPage enterLastName(String lName){
        type(eleLastName, lName, "Entered the Lastname - "+lName);
        return this;
    }

    public PIMPage clickSave(){
        click(eleSavebtn, "Clicked on Save button");
        Uninterruptibles.sleepUninterruptibly(3,TimeUnit.SECONDS);
        return this;
    }

    public PIMPage clickPIMTab(){
        click(elePIM, "Clicked on PIM tab");
        return this;
    }

    public PIMPage searchEmployee(String name){
        type(eleEmployeeName, name, "Search the employee name - "+name);
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        click(eleSearchBtn);
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        return this;
    }

    public PIMPage verifyName(String name){
        verifyExactText(eleFirstNameRecord, name);
        return this;
    }

    public PIMPage deleteEmployeeRecord(){
        click(eleSelectCheckbox);
        click(eleDeleteSelected);
        click(eleConfirmDelete);
        return this;
    }

    public PIMPage verifyNoRecordsFound(String name){
        searchEmployee(name);
        verifyExactText(eleNoRecords,"No Records Found");
        return this;
    }

    public HomePage clickDashboardTab(){
        click(eleDashboard, "Clicked on Dashboard tab");
        return new HomePage();
    }




}
