package com.uiauto.pages;

import com.uiauto.base.BaseMethods;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends BaseMethods {

    @FindBy(xpath = "//span[text()='PIM']")
    private WebElement elePIM;

    public HomePage(){
        PageFactory.initElements(driver,this);
    }

    public PIMPage clickPIMTab(){
        click(elePIM, "Clicked on PIM tab");
        return new PIMPage();
    }
}
