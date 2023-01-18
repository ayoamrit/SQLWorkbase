package action;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.TreePath;
import javax.swing.JTextField;

public class myMouseListener extends TableAction implements MouseListener{
    JTree windowTree;  //initializing JTree object

    //constructor getting required value as parameter
    public myMouseListener(JTree windowTree,JTextField selectedDatabase, JTextField selectedTable, DefaultTableModel tableModel) {
        super(selectedDatabase, selectedTable, tableModel);  //calling constructor from TableAction class as passing parameters
        this.windowTree = windowTree;  //setting JTree
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1) {
            TreePath path = windowTree.getSelectionPath();  //getting selected path from JTree

            //if the path is not null
            if (path != null) {
                setTableField(path);  //calling method from TableAction class and passing selected Path as a parameter
            }
        }
        else{
            setWindowTable();  //calling method to add database table data to window table
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
