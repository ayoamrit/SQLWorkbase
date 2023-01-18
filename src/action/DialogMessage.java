package action;
import javax.swing.JOptionPane;

public class DialogMessage {

    //in case of any error this method will be used to notify user about the error
    public void errorMessage(String message){
        JOptionPane.showMessageDialog(null,message,"Error",JOptionPane.ERROR_MESSAGE);
    }

    //user will be notified using this method regarding successfully completed tasks
    public void infoMessage(String message){
        JOptionPane.showMessageDialog(null,message,"Information",JOptionPane.INFORMATION_MESSAGE);
    }

    public String inputDialog(){
        return JOptionPane.showInputDialog("Enter the file name");
    }
}
