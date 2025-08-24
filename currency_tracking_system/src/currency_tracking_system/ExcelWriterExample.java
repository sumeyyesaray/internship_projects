package currency_tracking_system;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

import java.io.FileOutputStream;

public class ExcelWriterExample {
	public static void main(String[] args) {
		
		Workbook workbook = new XSSFWorkbook(); //yeni exel dosyası
		Sheet sheet = workbook.createSheet("Veriler");
		
		//Başlık satırı
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Tarih");
        header.createCell(1).setCellValue("Döviz Kuru");
        
        //örnek veri satırı
        Row row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("2025-08-22");
        row1.createCell(1).setCellValue(35);
        
        try (FileOutputStream fos =new FileOutputStream("doviz_kuru.xlsx")){
			workbook.write(fos);
			System.out.println("Exel dosyası başarıyla oluşturuldu.");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
