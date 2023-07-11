package com.uiauto.constants;

public final class FrameworkConstants {

    private FrameworkConstants() {
    }

    private static final String PROPERTIESFILE = System.getProperty("user.dir") + "/src/main/resources/data.properties";
    private static final String REPORTFILE = System.getProperty("user.dir") + "/reports/report.html";
    private static final String INPUTFILE = System.getProperty("user.dir") + "/src/test/java/com/uiauto/testData/EmployeeList.xlsx";

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
