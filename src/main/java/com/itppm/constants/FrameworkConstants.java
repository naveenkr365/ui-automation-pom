package com.itppm.constants;

public final class FrameworkConstants {

    private FrameworkConstants() {
    }

    private static final String CHROMEDRIVERPATH = System.getProperty("user.dir") + "/src/main/resources/driver/chromedriver/chromedriver";
    private static final String PROPERTIESFILE = System.getProperty("user.dir") + "/src/main/resources/data.properties";
    private static final String REPORTFILE = System.getProperty("user.dir") + "/reports/report.html";
    private static final String INPUTFILE = System.getProperty("user.dir") + "/src/main/resources/testData/FieldMapping_Roles.xlsx";

    public static String getChromedriverpath() {
        return CHROMEDRIVERPATH;
    }

    public static String getPropertiesfile() {
        return PROPERTIESFILE;
    }

    public static String getReportFile() {
        return REPORTFILE;
    }

    public static String getInputFile() {
        return INPUTFILE;
    }
}
