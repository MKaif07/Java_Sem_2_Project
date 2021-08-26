import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class enterData extends JFrame implements ActionListener {

    private JFrame frame;
    private JLabel label;
    JPanel panel;
    private JTextField field;
    private JButton add, show, exit, undo;
    private String sql, tname, db, type;
    private JTable table;
    int lab = 2, col;
    DefaultTableModel model;

    enterData(String database, String name){
        JOptionPane.showMessageDialog(this,"You can use SHOW to display table Structure");
        db = database;
        tname = name;
        System.out.println(tname);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(new FlowLayout());

        label = new JLabel("Enter Data: ");

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(50,30));

        try{
            CONNEKT obj = new CONNEKT();
            Statement st = obj.KON(db).createStatement();
            ResultSet rs = st.executeQuery("select * from " + tname);
            ResultSetMetaData rsmt = (ResultSetMetaData) rs.getMetaData();
            type = rsmt.getColumnTypeName(1);
            label.setText(rsmt.getColumnLabel(1) + " " + type);

            } catch (SQLException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }

        field = new JTextField(null);
        field.setPreferredSize(new Dimension(150, 40));

        add = new JButton("Add");
        add.addActionListener(this);

        show = new JButton("Show");
        show.addActionListener(this);

        undo = new JButton("Undo");
        undo.addActionListener(this);

        exit = new JButton("Exit");
        exit.addActionListener(this);

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(300,100));
        table.setEnabled(false);


        this.add(label);
        this.add(field);
        this.add(panel);
        this.add(add);
        this.add(show);
        this.add(undo);
        this.add(exit);
        this.pack();

        frame = new JFrame();
        frame.setSize(450,300);
        frame.setLayout(new FlowLayout());
        frame.add(new JScrollPane(table));

        this.setVisible(true);
        sql = "insert into " + tname +" values(";
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == add) {
            if (field.getText() == null) {
                JOptionPane.showMessageDialog(null, "Enter data to Add");
            } else {
                if (type == "VARCHAR" || type == "CHAR"){
                    String temp = "'" + field.getText();
                    sql = sql + temp;
                    sql = sql + "',";
                } else {
                    String temp = field.getText();
                    sql = sql + temp;
                    sql = sql + ",";
                }
                try {
                    CONNEKT obj = new CONNEKT();
                    Statement st = obj.KON(db).createStatement();
                    ResultSet rs = st.executeQuery("select * from " + tname);
                    ResultSetMetaData rsmt = (ResultSetMetaData) rs.getMetaData();
                    type = rsmt.getColumnTypeName(lab);
                    label.setText(rsmt.getColumnLabel(lab) + " " + type);
                    col = rsmt.getColumnCount();
                    } catch (SQLException ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                }
                field.setText(null);
                if(lab > col){
                    StringBuffer sb = new StringBuffer(sql);
                    sb.setLength(sb.length() - 1);
                    sql = sb.toString();
                    sql = sql + ");";

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
                        Statement statement = connection.createStatement();
                        statement.executeUpdate(sql);
                        JOptionPane.showMessageDialog(null, "Row added Succesfully");

                        CONNEKT obj = new CONNEKT();
                        Statement st = obj.KON(db).createStatement();
                        ResultSet rs = st.executeQuery("select * from " + tname);
                        ResultSetMetaData rsmt = (ResultSetMetaData) rs.getMetaData();
                        type = rsmt.getColumnTypeName(1);
                        label.setText(rsmt.getColumnLabel(1) + " " + type);

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "" + ex);
                        System.out.println(ex);
                        ex.printStackTrace();
                    }
                    System.out.println(sql);
                    sql = "insert into " + tname +" values(";
                    lab = 2;
                }
                else{
                    lab++;
                }
            }
        }

        if(e.getSource() == show){
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from " + tname );
                ResultSetMetaData rst=(ResultSetMetaData) rs.getMetaData();
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "" + ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
            frame.setVisible(true);
        }

        if(e.getSource() == undo){
            sql = "insert into " + tname +" values(";

            try{
                CONNEKT obj = new CONNEKT();
                Statement st = obj.KON(db).createStatement();
                ResultSet rs = st.executeQuery("select * from " + tname);
                ResultSetMetaData rsmt = (ResultSetMetaData) rs.getMetaData();
                type = rsmt.getColumnTypeName(1);
                label.setText(rsmt.getColumnLabel(1) + " " + type);
            } catch (SQLException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "UNDO is useful until You haven't pressed DONE \n Re-enter data");
            lab = 2;
        }

        if(e.getSource()==exit){
            this.dispose();
            frame.dispose();
        }

    }
}
