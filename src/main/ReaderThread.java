package main;
import action.DialogMessage;

import java.util.Scanner;
import java.io.File;

public class ReaderThread implements Runnable{

    private static String usernameText;  //usernameText variable
    private static String passwordText;  //passwordText variable

    public void run(){  //runnable method

        try {
            readText();  //calling method to read saved username and password to log in to local sql server

        } catch (Exception e) {  //if the method didn't work due to any reason exception would be thrown and message would
            //be displayed to notify the user about the error
            new DialogMessage().errorMessage("Saved username & password cannot be read");
        }
    }

    private void readText() throws Exception{

        File file = new File("ref/records.txt");  //object of File class

        if(file.exists()) {  //if the file exists
            Scanner reader = new Scanner(file);  //scanner to read data from a file

            String[] fieldsText = reader.nextLine().split(",");  //storing username and password, separated by a comma,
            // in an array.

            usernameText = fieldsText[0];  //setting usernameText variable
            passwordText = fieldsText[1];  //setting passwordText variable

            reader.close();  //closing scanner after reading username and password from a text file
        }
        else{  //if the file does not exist then username and password are empty spaces
            usernameText = "";
            passwordText = "";
        }
    }

    //method retuning read usernameText from a text file
    public String getUsernameText(){
        return usernameText;
    }

    //method returning read passwordText from a text file
    public String getPasswordText(){
        return passwordText;
    }
}
