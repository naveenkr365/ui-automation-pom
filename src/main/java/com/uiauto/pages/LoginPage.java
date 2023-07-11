package com.uiauto.pages;

import com.uiauto.base.BaseMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseMethods {

    @FindBy(name = "username")
    private WebElement eleUserName;

    @FindBy(name = "password")
    private WebElement elePassword;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement eleLoginbtn;

    public LoginPage(){
        PageFactory.initElements(driver,this);
    }

    public LoginPage enterUsername(String name){
        type(eleUserName, name);
        return this;
    }

    public LoginPage enterPassword(String pwd){
        type(elePassword, pwd);
        return this;
    }

    public HomePage clickLoginButton(){
        click(eleLoginbtn);
        return new HomePage();
    }







}
