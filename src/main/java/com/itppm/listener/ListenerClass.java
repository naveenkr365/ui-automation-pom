package com.itppm.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.itppm.base.BaseMethods;
import com.itppm.utils.ExtentReporterNG;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;

public class ListenerClass extends BaseMethods implements ITestListener {

    ExtentReports extent = ExtentReporterNG.generateExtentReport();
    ExtentTest test;
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, result.getMethod().getMethodName() + " is Passed");
    }

    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, result.getMethod().getMethodName() + " is Skipped");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        extentTest.get().fail(result.getThrowable());
        try {
            extentTest.get().addScreenCaptureFromPath(getScreenshot(result.getMethod().getMethodName()), result.getMethod().getMethodName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
