import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class updateTable extends JFrame implements ActionListener {

    private JFrame frame;
    private JLabel label, label1, label2, label3;
    private JPanel panel;
    private JTable table;
    private DefaultTableModel model;
    private JTextField field, field1, field2;
    private JButton update, show, rb;
    private String db, sql, tname, type;
    updateTable(String database, String name){

        db = database;
        tname = name;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(450,500);

        table = new JTable(model);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(400,200));
        panel.setBorder(new LineBorder(Color.BLACK));
        panel.setOpaque(true);


        label3 = new JLabel("Field no.");
        field = new JTextField("");
        field.setPreferredSize(new Dimension(40,40));

        label1 = new JLabel("New value");
        field1 = new JTextField(null);
        field1.setPreferredSize(new Dimension(250,40));

        field2 = new JTextField(null);
        field2.setPreferredSize(new Dimension(200,40));

        show = new JButton("Show");
        show.addActionListener(this);

        update = new JButton("Update");
        update.addActionListener(this);

        rb = new JButton("Return");
        rb.setPreferredSize(new Dimension(250, 30));
        rb.addActionListener(this);

        try{
            CONNEKT obj = new CONNEKT();
            Statement st = obj.KON(db).createStatement();
            ResultSet rs = st.executeQuery("select * from " + tname);
            ResultSetMetaData rsmt = (ResultSetMetaData) rs.getMetaData();
            label2 = new JLabel("Where " + rsmt.getColumnName(1) + " = ");
            panel.setLayout(new GridLayout(rsmt.getColumnCount(),1));
            for(int i=0; i<=rsmt.getColumnCount(); i++) {
                label = new JLabel(i+1 + "." + rsmt.getColumnLabel(i+1) + " " +rsmt.getColumnTypeName(i+1));
                panel.add(label);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            ex.printStackTrace();
        }

        this.add(panel);
        this.add(label3);
        this.add(field);
        this.add(label1);
        this.add(field1);
        this.add(label2);
        this.add(field2);
        this.add(update);
        this.add(show);
        this.add(rb);

        frame = new JFrame();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450,300);
        frame.setLayout(new FlowLayout());
        frame.add(new JScrollPane(table));
        frame.pack();

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == update){
            try{
                CONNEKT obj = new CONNEKT();
                Statement st = obj.KON(db).createStatement();
                ResultSet rs = st.executeQuery("select * from " + tname);
                ResultSetMetaData rsmt = (ResultSetMetaData) rs.getMetaData();
                sql =  "UPDATE " + tname + " set " + rsmt.getColumnLabel(Integer.parseInt(field.getText())) + " = " + field1.getText() + " where Id  = " + field2.getText() +";";
                if(rsmt.getColumnTypeName(Integer.parseInt(field.getText())) == "VARCHAR" || rsmt.getColumnTypeName(Integer.parseInt(field.getText())) == "CHAR"){
                    sql =  "UPDATE " + tname + " set " + rsmt.getColumnLabel(Integer.parseInt(field.getText())) + " = " +"'" +field1.getText() +"'" + " where Id  = " + field2.getText() +";";
                }
                else {
                    sql =  "UPDATE " + tname + " set " + rsmt.getColumnLabel(Integer.parseInt(field.getText())) + " = " + field1.getText() + " where Id  = " + field2.getText() +";";
                }
                panel.setLayout(new GridLayout(rsmt.getColumnCount(),1));
            } catch (SQLException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
            System.out.println(sql);
        }

        if(e.getSource() == update){
            try{
                CONNEKT obj = new CONNEKT();
                Statement st = obj.KON(db).createStatement();
                st.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
            field.setText(null);
            field1.setText(null);
            field2.setText(null);
        }

        if(e.getSource() == show){
            System.out.println("Running");
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from " + tname );
                ResultSetMetaData rst=(ResultSetMetaData) rs.getMetaData();
//                int cno=rst.getColumnCount();
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "" + ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
            frame.setVisible(true);
        }
        if(e.getSource() == rb){
            this.dispose();
            new useTable(db, tname);
        }

    }
}
