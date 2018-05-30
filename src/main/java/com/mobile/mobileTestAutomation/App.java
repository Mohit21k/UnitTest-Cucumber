package com.mobile.mobileTestAutomation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println(getCurrentTimeStamp("dd_MM_YYYY_HH_mm_ss_SSS"));
        System.out.println(current_Date("MM/dd/yyyy"));
    }
    
    public  static String getCurrentTimeStamp(String strFormat) {
	 	SimpleDateFormat sdfDate = new SimpleDateFormat(strFormat);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());			
		String strDate = sdfDate.format(timestamp); 
		return strDate;
	    }
    
    public static String current_Date(String strDateFormat) {
		 LocalDate localDate = LocalDate.now();
		 String currentDate=DateTimeFormatter.ofPattern(strDateFormat).format(localDate);
		 return currentDate;
	}
    
	public String ExcelRead(String filepath, int RowNum, int CellNum, int sheetIndex)
			throws IOException, InvalidFormatException {

		File file = new File(filepath);
		FileInputStream fs = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet = wb.getSheetAt(sheetIndex);
		DataFormatter formatter = new DataFormatter();
		String value = formatter.formatCellValue(sheet.getRow(RowNum).getCell(CellNum));
		fs.close();
		wb.close();
		return value;

	}

	public void ExcelWrite(String filepath, int RowNum, int CellNum, Object data, int sheetIndex)
			throws IOException, InvalidFormatException {

		File file = new File(filepath);
		FileInputStream fs = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet = wb.getSheetAt(sheetIndex);
		Cell cell = sheet.getRow(RowNum).getCell(CellNum);
		 FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
		switch(formulaEvaluator.evaluateInCell(cell).getCellTypeEnum())
        {
        case NUMERIC:
        	if(data instanceof Double) {
        		 cell.setCellValue((Double)data);
        	}else {
        		 cell.setCellValue((int)data);
        	}           
            break;
        case STRING:
        	 cell.setCellValue((String)data);
            break;
        case BOOLEAN:
        	 cell.setCellValue((boolean)data);	 
        default:
            break;
        }
		FileOutputStream fo = new FileOutputStream(file);
		wb.write(fo);
		fo.close();
		wb.close();
	}
}
