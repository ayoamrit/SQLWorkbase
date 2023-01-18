package connection;

import action.DialogMessage;

import javax.swing.tree.DefaultMutableTreeNode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class FetchDatabase extends Connect{

    public void addDatabaseTree(DefaultMutableTreeNode parentTreeNode) {

        //using try and catch statement
        try{
            //executing query to get databases name from the sql server
            ResultSet resultSet = getStatement().executeQuery("SHOW DATABASES");

            //using while loop to add the names to the TreeNode
            while(resultSet.next()){

                //adding database name to the tree node
                DefaultMutableTreeNode databaseNode = new DefaultMutableTreeNode(resultSet.getString(1));

                //calling method to get table names
                //getting all the tables name under specific database and adding them to the tree node
                ResultSet tableResult = getTableNames(resultSet.getString(1));  //calling another method to get resultSet

                if(tableResult != null) {

                    //using while loop
                    while (tableResult.next()) {

                        //adding tables name under their database name in tree node
                        databaseNode.add(new DefaultMutableTreeNode(tableResult.getString(1)));
                    }

                    //adding databaseTreeNode to the rootTreeNode or parentTreeNode created in window package
                    parentTreeNode.add(databaseNode);
                }
                else{
                    new DialogMessage().errorMessage((resultSet.getString(1)+" is not accessible"));
                }

            }
        }catch(SQLException e){
            new DialogMessage().errorMessage(String.valueOf(e));
        }
    }

    private ResultSet getTableNames(String databaseName){
        ResultSet resultSet = null;  //initializing result set

        //using try and catch statement
        try{
            Statement statement = getConnection().createStatement();  //creating a statement
            statement.executeUpdate("USE "+databaseName);  //updating query
            resultSet = statement.executeQuery("SHOW TABLES");  //executing query

        }catch(SQLException e){
            return null;  //returning null if an error occurred
        }

        return resultSet;  //returning result set
    }

    public ResultSet getTableColumns(String databaseName, String tableName){
        ResultSet resultSet = null;  //using meta data to get columns name from database

        try{
            Statement statement = getConnection().createStatement();  //creating statement
            statement.executeUpdate("USE "+databaseName);  //executing update
            resultSet = statement.executeQuery("SELECT * FROM "+tableName);  //executing query using result set


        }catch(SQLException e){  //throwing exception
            return null;  //returning null in case of any error
        }

        return resultSet;  //returning meta data
    }

}
