package com.uiauto.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.uiauto.constants.FrameworkConstants;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ExtentReport {

    private ExtentReport(){}

    private static ExtentReports extent;
    public static ExtentTest test;

    public static void initReports(){
        if(Objects.isNull(extent)) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter(FrameworkConstants.getReportFile());
            spark.config().setReportName(" GTM Test Execution");
            spark.config().setDocumentTitle("GTM Execution Results");
            extent.attachReporter(spark);
            extent.setSystemInfo("Company", "Freshworks");
            extent.setSystemInfo("Team", "IT QA");
            extent.setSystemInfo("Product", "CRM - Interstellar");
        }
    }

    public static void flushReports(){
        if(Objects.nonNull(extent)) {
            extent.flush();
        }
        try {
            Desktop.getDesktop().browse(new File(FrameworkConstants.getReportFile()).toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static  void  createTest(String testCaseName){
        test = extent.createTest(testCaseName);
        ExtentManager.setExtentTest(test);
    }
}
