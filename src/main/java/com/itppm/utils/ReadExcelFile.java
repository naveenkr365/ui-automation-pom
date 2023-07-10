package com.itppm.utils;

import com.itppm.constants.FrameworkConstants;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReadExcelFile {

    public static Object[] getSheet(String dataSheetName) {
        Object[] data = null ;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(FrameworkConstants.getInputFile());
            XSSFSheet sheet = workbook.getSheet(dataSheetName);

            // get the number of rows
            int rowCount = sheet.getLastRowNum();
            // get the number of columns
            int columnCount = sheet.getRow(0).getLastCellNum();

            data = new Object[rowCount];
            Map<Object, Object> map;

            // loop through the rows
            for(int i=1; i <rowCount+1; i++){
                map = new LinkedHashMap<>();
                try {
                    for(int j=0; j <columnCount; j++){ // loop through the columns
                        try {
                            try{
                                String key = sheet.getRow(0).getCell(j).getStringCellValue();

                                XSSFCell cell = sheet.getRow(i).getCell(j);
                                String cellData = null;
                                switch (cell.getCellType()){
                                    case STRING:
                                        cellData = cell.getStringCellValue();
                                        break;
                                    case NUMERIC:
                                        cellData = String.valueOf((long) cell.getNumericCellValue());
                                        break;
                                    case BLANK:
                                        cellData = "";
                                        break;
                                }


                                map.put(key,cellData);

                            }catch(NullPointerException e){

                            }
                            data[i-1]  = map; // add to the data array
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}
