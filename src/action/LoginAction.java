package action;
import java.io.File;
import java.io.FileWriter;

public class LoginAction {

    private String password;  //password variable
    private String username;  //username variable

    //method getting username and password entered by the user in text fields
    public void getText(String username, String password){
        this.username = username;  //setting username
        this.password = password;  //setting password

        storeText();  //calling method to store username and password in a text file
    }

    private void storeText(){
        File file = new File("ref/records.txt");  //object of File class

        try{
            FileWriter writer = new FileWriter(file);  //using FileWriter to write to a text file
            writer.write(username+","+password);  //writing username and password
            writer.close();  //closing FileWriter

        }catch(Exception e){  //throwing exception
            new DialogMessage().errorMessage("Unable to save username & password");
        }
    }
}
