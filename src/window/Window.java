package window;
import connection.FetchDatabase;
import javax.swing.*;
import action.myMouseListener;
import action.DialogMessage;
import excel.IntoSpreadsheet;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;

public class Window {

    final JFrame window = new JFrame();  //creating a new window frame

    private JTextField selectedDatabaseField = new JTextField();  //selectedDatabase text field
    private JTextField selectedTableField = new JTextField();  //selectedTable text field

    private JMenuItem exportData = new JMenuItem("Excel Spreadsheet");

    DefaultTableModel tableModel = new DefaultTableModel();  //default table model
    JTable table = new JTable(tableModel);  //table
    JScrollPane tablePane = new JScrollPane(table);  //table scroll pane

    //constructor
    public Window(){
        windowProperties();  //calling method to set window frame properties
        windowTree();  //calling method to add tree panel to the window frame
        windowLabel();  //calling method to add labels to the window frame
        windowTextField();  //calling method to add text fields to the window frame
        windowTable();  //calling method to add table to the window frame
        windowMenuBar();  //calling method to add menu bar to the window frame
        windowIcon();  //calling method to set window frame icon
        window.setVisible(true);  //setting window frame to visible after adding stuff to it
    }

    private void windowProperties(){
        window.setTitle("SQL Workbase");  //setting name of the window frame
        window.setSize(900,600);  //setting size of the window frame
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //terminating program once the cross button is pressed
        window.setLocationRelativeTo(null);  //setting relative location to null
        window.setLayout(null);  //setting layout to null
        window.setResizable(false);  //window is not resizable
    }

    //method adding tree panel view to the window frame
    private void windowTree(){
        DefaultMutableTreeNode database = new DefaultMutableTreeNode("Database");  //root treeNode

        //calling method from another package to add data to the tree panel
        //sending root treeNode as a parameter
        new FetchDatabase().addDatabaseTree(database);

        JTree windowTree = new JTree(database);  //adding rootTreeNode to windowTree
        JScrollPane scrollPane = new JScrollPane(windowTree);  //adding scrollPanel to the window tree
        scrollPane.setBounds(10,30,200,500);  //setting bounds of the window tree

        myMouseListener mouseListener = new myMouseListener(windowTree, selectedDatabaseField, selectedTableField, tableModel);
        windowTree.addMouseListener(mouseListener);
        window.add(scrollPane);  //adding scrollPanel and windowTree to the window
    }

    private void windowLabel(){

        //initializing labels
        JLabel selectedDatabaseLabel = new JLabel("Database");
        JLabel selectedTableLabel = new JLabel("Table");

        selectedDatabaseLabel.setBounds(250,30,100,25);  //setting bounds of selectedDatabase label
        selectedTableLabel.setBounds(500,30,100,25);  //setting bounds of selectedTable label

        //adding labels to the window frame
        window.add(selectedDatabaseLabel);
        window.add(selectedTableLabel);
    }

    private void windowTextField(){
        selectedDatabaseField.setBounds(320,30,150,25);  //setting bounds of selectedDatabase field
        selectedTableField.setBounds(550,30,150,25);  //setting bounds of selectedTable field


        //adding text fields to the window frame
        window.add(selectedDatabaseField);
        window.add(selectedTableField);
    }

    private void windowTable(){
        tablePane.setBounds(250,80,600,450);  //setting bounds of table
        table.setRowSelectionAllowed(false);  //row selection is not allowed in table
        table.setDefaultEditor(Object.class, null);  //table cells are not editable
        window.add(tablePane);  //adding table to the window frame
    }

    private void windowIcon(){
        ImageIcon imageIcon = new ImageIcon("ref/icons/window.png");  //object of class ImageIcon
        window.setIconImage(imageIcon.getImage());  //setting image of window frame
    }

    private void windowMenuBar(){
        JMenuBar menuBar = new JMenuBar();  //creating menuBar
        JMenu menu = new JMenu("Export");  //creating object of JMenu

        menu.add(exportData);  //adding menuItem to menu
        menuBar.add(menu);  //adding menu to menuBar
        exportData.addActionListener(this::actionPerformed);  //adding actionListener to exportData menuItem

        window.setJMenuBar(menuBar);  //adding menuBar to window frame
    }

    //this method will be executed whenever menuItem is clicked
    private void actionPerformed(ActionEvent actionEvent) {

        //checking whether the row and column count is 0 or not
        if(tableModel.getRowCount() == 0 && tableModel.getColumnCount() == 0){
            new DialogMessage().errorMessage("Insufficient data in selected table");
        }
        else{

            //calling constructor from excel package to insert tables data into excel
            new IntoSpreadsheet(tableModel, new DialogMessage().inputDialog(), String.valueOf(selectedTableField.getText()));
        }
    }

}
