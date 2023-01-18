package excel;

import action.DialogMessage;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;

public class IntoSpreadsheet {
    private DefaultTableModel tableModel;  //tableModel
    private String filename;  //filename
    private String tableName;  //tableName

    //constructor
    public IntoSpreadsheet(DefaultTableModel tableModel, String filename, String tableName){
        this.tableModel = tableModel;  //setting tableModel
        this.filename = filename;  //setting fileName
        this.tableName = tableName;  //setting tableName

        writeSpreadsheet();  //calling method to insert data into spreadsheet
    }

    private void writeSpreadsheet(){

        //using try and catch statement
        try{

            //initializing a workbook
            Workbook workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet(tableName);  //creating a sheet in the workbook

            //calling method to set cell style to bold for column names
            HSSFCellStyle cellStyle = getStyle(workbook);

            Row columnNameRow = sheet.createRow(0);  //creating a row to insert column names
            for(int x =0;x<tableModel.getColumnCount();x++){
                Cell columnNameCell = columnNameRow.createCell(x);  //creating cell
                columnNameCell.setCellValue(String.valueOf(tableModel.getColumnName(x))); //inserting columns name into cells
                columnNameCell.setCellStyle(cellStyle);  //setting font style of column names to bold
            }

            //using for loop to insert all the table data to excel file
            for(int x = 0;x<tableModel.getRowCount();x++){
                Row row = sheet.createRow(x+1);  //creating row

                for(int y = 0;y<tableModel.getColumnCount();y++){
                    Cell cell = row.createCell(y);  //creating cell
                    cell.setCellValue(String.valueOf(tableModel.getValueAt(x,y)));  //inserting data into cells
                }
            }

            workbook.write(new FileOutputStream("ref/"+filename+".xls"));  //writing data into workbook using fileOutputStream
            workbook.close();  //closing workbook after writing it

            infoMessage();  //displaying infoMessage saying that file has been created successfully

        }catch(Exception e){  //throwing exception
            new DialogMessage().errorMessage("Cannot execute excel file");
        }
    }

    //method setting workbook cell style to bold
    private HSSFCellStyle getStyle(Workbook workbook){
        HSSFCellStyle cellStyle = (HSSFCellStyle) workbook.createCellStyle();  //initializing cell style
        HSSFFont font = (HSSFFont) workbook.createFont();  //initializing font

        font.setBold(true);  //setting font to bold
        cellStyle.setFont(font);  //setting cell style to bold

        return cellStyle;  //returning cell style
    }

    private void infoMessage(){
        new DialogMessage().infoMessage("File created successfully");
    }
}
