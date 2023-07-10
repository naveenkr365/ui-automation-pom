package com.itppm.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.itppm.constants.FrameworkConstants;

public class ExtentReporterNG {
    public static ExtentReports extent;
    public static ExtentSparkReporter reporter;

    public static ExtentReports generateExtentReport(){
        reporter = new ExtentSparkReporter(FrameworkConstants.getReportFile());
        reporter.config().setReportName("Automation Results");
        reporter.config().setDocumentTitle("Execution Results");
        extent = new ExtentReports();
        extent.attachReporter(reporter);
        extent.setSystemInfo("Company", "Freshworks");
        extent.setSystemInfo("Team", "QA");
        //extent.setSystemInfo("Product", "Chargebee SKUs Testing");
        return extent;
    }


}
