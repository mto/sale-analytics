package com.cca.sanalytics.util;

import com.cca.sanalytics.jfx.model.ExcelRow;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author <a href="hoang281283@gmail.com">Minh Hoang TO</a>
 * @date: 1/24/17
 */
public class ExcelUtil {

    public static void writeToExcel(String sheetName, TableView<? extends ExcelRow> tbv, File f){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);

        XSSFRow header = sheet.createRow(0);
        int cindex = 0;
        for(TableColumn tc : tbv.getColumns()){
            XSSFCell hc = header.createCell(cindex);
            hc.setCellValue(tc.getText());

            cindex++;
        }

        int i =0;
        for(ExcelRow xr : tbv.getItems()){
            i++;
            XSSFRow nr = sheet.createRow(i);

            String[] cells = xr.getCells();
            for(int j =0;j< cells.length;j++){
                XSSFCell cell = nr.createCell(j);
                cell.setCellValue(cells[j]);
            }
        }

        try{
            if(!f.getAbsolutePath().endsWith(".xlsx")){
                f = new File(f.getAbsolutePath() + ".xlsx");
            }

            if(f.exists() && f.isFile()) {
                f.delete();
            }
            f.createNewFile();

            OutputStream out = new FileOutputStream(f);
            workbook.write(out);
            out.close();
            workbook.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
