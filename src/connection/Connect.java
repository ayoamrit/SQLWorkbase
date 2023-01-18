package connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Statement;

public class Connect{

    protected static String URL = "jdbc:mysql://localhost:3306/";
    protected static String username;
    protected static String password;


    //method setting local variables
    public Connection isConnected(String username, String password){
        Connect.username = username;  //setting username
        Connect.password = password;  //setting password

        return getConnection();  //calling and returning method to make that database is accessible
    }

    //statement method
    protected Statement getStatement(){
        Statement statement = null;  //initializing statement

        //using try and catch statement
        try{
            Connection connection = DriverManager.getConnection(URL, username, password);  //connecting to the sql server
            statement = connection.createStatement();  //creating statement

        }catch(SQLException e){
            return null;  //returning null if there's any problem while connecting to the server
        }

        return statement;  //returning statement if statement get created successfully
    }

    //connection method
    protected Connection getConnection(){
        Connection connection = null;  //initializing connection

        //using try and catch statement
        try{
            connection = DriverManager.getConnection(URL,username,password);  //connecting to the sql server

        }catch(SQLException e){  //throwing exception
            return null;  //returning null if there's any problem while connecting to the server
        }

        return connection;  //returning connection if connection is successful
    }
}
