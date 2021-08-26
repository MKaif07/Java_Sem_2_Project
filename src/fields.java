import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class fields implements ActionListener {

    JFrame frame;
    JPanel panel;
    JTextField field, pfield;
    JComboBox comboBox;
    JButton add, undo, exit, done;
    JLabel label, plabel;
    JTable table;
    DefaultTableModel model;
    String sql ="" ,temp = "", tname, db;
    String[] dtypes = {"int", "varchar()", "char", "float", "decimal", "binary", "boolean"};
    int bt = 0;

    fields(String database,String name){

        db = database;
        tname = name;
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setPreferredSize(new Dimension(350,500));

        panel = new JPanel();

        label = new JLabel();
        label.setText("Total fields: " + bt);
        label.setFont(new Font("Consolas",Font.ITALIC,20));
        label.setPreferredSize(new Dimension(300,30));

        plabel = new JLabel("PRIMARY KEY");
        plabel.setFont(new Font("Consolas",Font.ITALIC, 16));
        plabel.setPreferredSize(new Dimension(100,30));

        String[][] d = {{}};
        String[] c = {"Field", "Type"};
        model = new DefaultTableModel(d,c);

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(300,100));
        table.setEnabled(false);

        done = new JButton("Done");
        done.setPreferredSize(new Dimension(100,30));
        done.addActionListener(this);

        exit = new JButton("Exit");
        exit.setPreferredSize(new Dimension(100,30));
        exit.addActionListener(this);

        undo = new JButton("Undo");
        undo.setPreferredSize(new Dimension(100,30));
        undo.addActionListener(this);

        add = new JButton("Add");
        add.setPreferredSize(new Dimension(100,30));
        add.addActionListener(this);

        comboBox = new JComboBox(dtypes);

        field = new JTextField();
        field.setPreferredSize(new Dimension(100,30));
        field.addActionListener(this);
        frame.add(field);

        pfield = new JTextField();
        pfield.setPreferredSize(new Dimension(200,30));
        pfield.addActionListener(this);

        comboBox = new JComboBox(dtypes);
        comboBox.setPreferredSize(new Dimension(100,30));
        comboBox.setEditable(true);
        comboBox.addActionListener(this);
        frame.add(comboBox);

        frame.add(add);
        frame.add(plabel);
        frame.add(pfield);
        frame.add(label);
        frame.add(new JScrollPane(table));
        frame.add(undo);
        frame.add(done);
        frame.add(exit);
        frame.pack();
        frame.setVisible(true);
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == add){

            if(field.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Enter Field");
            }
            else {
                String x = field.getText();
                String y = (String) comboBox.getSelectedItem();

                String[] newRow = {x, y};
                if (bt == 0) {
                    model.insertRow(0, newRow);
                } else {
                    model.insertRow(bt, newRow);
                }

                temp =  field.getText() + " " + comboBox.getSelectedItem() + ", ";
                sql = sql + temp;
                bt++;
                label.setText("Total fields: " + bt);
                field.setText("");
                comboBox.setSelectedIndex(0);
            }
        }
        if(e.getSource() == undo){

            StringBuffer sb1 = new StringBuffer(temp);
            sb1.setLength(sb1.length());
            temp = sb1.toString();
            StringBuffer sb = new StringBuffer(sql);
            sb.setLength(sb.length() - sb1.length());
            sql = sb.toString();

            model = (DefaultTableModel) table.getModel();
            model.setRowCount(bt-1);
            bt--;
            label.setText("Total fields: " + bt);
        }

        if(e.getSource() == done){
            String temp;
            if(bt==0){
                JOptionPane.showMessageDialog(null, "Add fields in Table");
            }
            else {
                if(pfield.getText().isEmpty()){
                    StringBuffer sb = new StringBuffer(sql);
                    sb.setLength(sb.length() - 2);
                    sql = sb.toString();
                    temp = "create table " + tname + "( " + sql + " );";
                    sql = temp;
                    System.out.println(sql);
                }else{
                    StringBuffer sb = new StringBuffer(sql);
                    sb.setLength(sb.length() - 2);
                    sql = sb.toString();
                    temp = "create table " + tname + "( " + sql + ", PRIMARY KEY (" + pfield.getText() + ") );";
                    sql = temp;
                }
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "" + field.getText() + " Table Created \n Table name: " + tname);

                    JOptionPane.showMessageDialog(null, "Now you can't edit table");
                    JOptionPane.showMessageDialog(null, "Enter data with respective fields");
                    new enterData(db, tname);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "" + ex);
                    JOptionPane.showMessageDialog(null, "Table not Created");
                    System.out.println(ex);
                    ex.printStackTrace();
                    frame.dispose();
                    new createTable(db);
                }
            }
            frame.dispose();
        }

        if(e.getSource() == exit){
            frame.dispose();
        }
    }
}
