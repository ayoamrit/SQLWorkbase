package main;

import window.Window;
import window.LoginWindow;

public class Main {

    //main method
    public static void main(String[] args){

        Thread thread = new Thread(new ReaderThread());
        thread.start();  //starting thread



        new LoginWindow();  //calling constructor from LoginWindow class
    }
}
