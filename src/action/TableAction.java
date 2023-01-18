package action;

import javax.swing.JTextField;
import connection.FetchDatabase;
import java.sql.ResultSetMetaData;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.TreePath;
import java.sql.ResultSet;

public class TableAction {

    JTextField selectedDatabase;  //initializing selectedDatabase text field
    JTextField selectedTable;  //initializing selectedTable text field

    DefaultTableModel tableModel;

    //constructor getting parameters and setting required values
    public TableAction(JTextField selectedDatabase, JTextField selectedTable, DefaultTableModel tableModel){
        this.selectedDatabase = selectedDatabase;  //setting selectedDatabase
        this.selectedTable = selectedTable;  //setting selectedTable
        this.tableModel = tableModel;
    }

    //method pushing data to window text fields
    protected void setTableField(TreePath path){
        int pathCount = path.getPathCount();  //checking how many components are there in the selected path

        //if the count is 2 which means that only database name is selected by the user
        if(pathCount == 2){

            //pushing selected database name to the selectedDatabase text field
            selectedDatabase.setText(String.valueOf(path.getPathComponent(1)));

            //setting selectedTable text field to empty
            selectedTable.setText(" ");
        }

        //if the count is 3 which means that both table and database are selected by the user
        else if(pathCount == 3){

            //pushing selected database name to the selectedDatabase text field
            selectedDatabase.setText(String.valueOf(path.getPathComponent(1)));

            //pushing selected table name to the selectedTable text field
            selectedTable.setText(String.valueOf(path.getPathComponent(2)));
        }
    }

    protected void setWindowTable(){

        //calling method and setting resultSet
        ResultSet resultSet = new FetchDatabase().getTableColumns(selectedDatabase.getText(), selectedTable.getText());

        setCellCount();  //calling method to clear all the columns and rows from table before inserting new records

        //using try and catch statement
        try{
            ResultSetMetaData metaData = resultSet.getMetaData();  //initializing resultSet meta data
            int columnCount = metaData.getColumnCount();

            //using for loop to get columns name from sql database table
            for(int x = 1;x <= columnCount;x++){
                tableModel.addColumn(metaData.getColumnName(x)); //adding column name to the window table
            }

            int row = 0;  //row cell

            //using while loop to fetch all the cell values from sql table
            while(resultSet.next()){
                tableModel.addRow(new Object[]{null});  //adding row to the table

                //using for loop to insert all the cell values in the window table cell
                for(int i = 1; i <= columnCount; i++) {
                    tableModel.setValueAt(resultSet.getString(i), row,i-1);  //setting value of the cells
                }
                row++;  //incrementing row after filling out a row
            }

        }catch(Exception e){  //throwing exception
            new DialogMessage().errorMessage("Unable to fetch data from sql server");
        }
    }

    private void setCellCount(){
        tableModel.setRowCount(0);  //setting row count to 0
        tableModel.setColumnCount(0);  //setting column count to 0
    }
}
