package window;
import connection.Connect;
import action.DialogMessage;
import action.LoginAction;
import main.ReaderThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginWindow {

    final JFrame loginWindow = new JFrame();  //loginWindow frame

    public JTextField usernameField = new JTextField();  //username text field
    public JPasswordField passwordField = new JPasswordField();  //password text field

    JCheckBox checkBox = new JCheckBox("Remember Me");  //checkBox


    //initializing constructor
    public LoginWindow(){
        loginWindowProperties();  //method setting window frame properties
        loginWindowLabels();  //method adding labels to the window frame
        loginWindowTextField();  //method adding text fields to the window frame
        loginWindowCheckBox();  //method adding checkBox to the window frame
        loginWindowIcon();  //method setting window frame icon
        loginWindowButton();  //method adding button to the window frame

        loginWindow.setVisible(true);  //setting window frame to visible after adding everything to the frame
    }

    private void loginWindowProperties(){
        loginWindow.setTitle("Connection");  //setting up name of the window frame
        loginWindow.setSize(350,250);  //setting up size of the window frame
        loginWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //program will terminate once the cross button is pressed
        loginWindow.setLocationRelativeTo(null);    //window will open in the center of the display
        loginWindow.setResizable(false);  //window is not resizable
        loginWindow.setLayout(null);  //no layout is being used
    }

    //method adding labels to the window frame
    private void loginWindowLabels(){
        JLabel messageLabel = new JLabel("Connect To SQL Server");  //message label
        JLabel usernameLabel = new JLabel("Username");  //username label
        JLabel passwordLabel = new JLabel("Password");  //password label

        messageLabel.setBounds(0,0,350,40);  //setting bounds of message label
        messageLabel.setBackground(Color.LIGHT_GRAY);  //setting background color of message label
        messageLabel.setOpaque(true);  //setting message label property to opaque
        messageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));  //setting border around message label
        messageLabel.setHorizontalAlignment(JLabel.CENTER);  //setting horizontal alignment
        messageLabel.setVerticalAlignment(JLabel.CENTER);  //setting vertical alignment
        usernameLabel.setBounds(10,60,80,25);  //setting bounds of username label
        passwordLabel.setBounds(10,100,80,25);  //setting bounds of password label

        //adding labels to the window frame
        loginWindow.add(usernameLabel);
        loginWindow.add(passwordLabel);
        loginWindow.add(messageLabel);
    }

    //method adding text fields to the window frame
    private void loginWindowTextField(){
        usernameField.setBounds(100,60,180,25);  //setting bounds of the username text field
        passwordField.setBounds(100,100,180,25);  //setting bounds of the password text field

        //reading data from the text file if it is saved by the user
        //username and password would be fetched from ReaderThread class, which is in main package
        usernameField.setText(new ReaderThread().getUsernameText());  //setting username field text
        passwordField.setText(new ReaderThread().getPasswordText());  //setting password field text

        //adding text fields to the window frame
        loginWindow.add(usernameField);
        loginWindow.add(passwordField);
    }

    //method adding checkbox to the window frame
    private void loginWindowCheckBox(){

        checkBox.setBounds(10,140,180,25);  //setting bounds of the checkbox
        checkBox.setFocusable(false);  //setting focus to false

        //adding checkbox to the window frame
        loginWindow.add(checkBox);
    }

    private void loginWindowIcon(){
        ImageIcon imageIcon = new ImageIcon("ref/icons/loginWindow.png");  //object of class ImageIcon
        loginWindow.setIconImage(imageIcon.getImage());  //setting icon of the loginWindow frame
    }

    //method adding button to the window frame
    private void loginWindowButton(){
        JButton connectButton = new JButton("Connect");  //object of JButton
        connectButton.setBounds(10,180,315,25);  //setting bounds of the button
        connectButton.setFocusable(false);  //setting focus to false
        connectButton.addActionListener(this::actionPerformed);  //adding action listener to perform function

        //adding button to the window frame
        loginWindow.add(connectButton);
    }

    //this method would be executed when button is pressed by the user
    private void actionPerformed(ActionEvent e){
        Connect connect = new Connect();  //object of Connect class

        //if both usernameTextField and passwordTextField is not empty
        if(!usernameField.getText().isEmpty() && !String.valueOf(passwordField.getPassword()).isEmpty()) {

            //if the object of Connect class return true which mean that both username and password are correct
            //& can be used to connect to the local sql server.
            if(connect.isConnected(usernameField.getText(), String.valueOf(passwordField.getPassword())) != null){

                //if the checkbox is checked then username and password would be stored to a text file and will be used
                //anytime user would try to run this application
                //NOTE: username and password would not be saved in a text file if they are wrong
                if(checkBox.isSelected()) {

                    //calling method from LoginAction class to save username and password to a text file
                    new LoginAction().getText(usernameField.getText(), String.valueOf(passwordField.getPassword()));
                }

                //after making sure that username and password are correct and can be used to log in to the sql server
                //the loginWindow will be terminated
                loginWindow.dispose();

                openMainWindow();  //calling method to open mainWindow
            }
            else{  //if any problem occurred while logging in to the sql server that errorMessage would be displayed
                new DialogMessage().errorMessage("Unable to connect");
            }
        }
        else{  //if any of the fields is empty this message would be shown
            new DialogMessage().errorMessage("Empty Field");
        }
    }

    private void openMainWindow(){
        new Window();
    }
}
