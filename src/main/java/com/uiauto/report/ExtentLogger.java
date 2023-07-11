package com.uiauto.report;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.uiauto.base.BaseMethods;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public final class ExtentLogger {

    private ExtentLogger(){}

    public static void pass(String message){
        ExtentManager.getExtentTest().pass(message);
    }

    public static void fail(String message){
        ExtentManager.getExtentTest().fail(message);
    }

    public static void skip(String message){
        ExtentManager.getExtentTest().skip(message);
    }

    public static void warn(String message){
        ExtentManager.getExtentTest().warning(message);
    }

    public static void pass(String message, boolean isScreenshotNeeded){
        try {
            if(BaseMethods.getPropertyValue("passedStepsScreenshots").equalsIgnoreCase("yes") && isScreenshotNeeded){
                ExtentManager.getExtentTest().pass(message, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());

            }
            else
                pass(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void fail(String message, boolean isScreenshotNeeded){
        try {
            if(BaseMethods.getPropertyValue("failedStepsScreenshots").equalsIgnoreCase("yes") && isScreenshotNeeded){
                ExtentManager.getExtentTest().fail(message, MediaEntityBuilder.createScreenCaptureFromBase64String(getBase64Image()).build());

            }
            else fail(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getBase64Image(){
        return ((TakesScreenshot)(BaseMethods.getDriver())).getScreenshotAs(OutputType.BASE64);
    }

}
