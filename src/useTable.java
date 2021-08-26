import net.proteanit.sql.DbUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class useTable extends JFrame implements ActionListener {

    private JFrame frame, uframe;
    private JPanel panel;
    private JButton insert, search, update,next, next1, prev, prev1, show,done, delete, rb, exit, exit1;
    private JTextField field, field1;
    private JLabel label, label1;
    private String sql, db, tname;
    private JTable table;
    DefaultTableModel model;
    int cur = 1;

    useTable(String database, String name){
        db = database;
        tname = name;


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,400);
        this.setLayout(new FlowLayout());

        field = new JTextField(null);
        field.setPreferredSize(new Dimension(200,30));

        field1 = new JTextField(null);
        field1.setPreferredSize(new Dimension(250,30));

        label = new JLabel("Enter data");
        label.setPreferredSize(new Dimension(150,30));

        label1 = new JLabel("Enter data");
        label1.setPreferredSize(new Dimension(150,30));

        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(300,100));
        table.setEnabled(false);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from " + tname );
            ResultSetMetaData rst=(ResultSetMetaData) rs.getMetaData();
            label.setText(rst.getColumnLabel(cur));
            label1.setText(rst.getColumnLabel(cur));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "" + ex);
            System.out.println(ex);
            ex.printStackTrace();
        }


        insert = new JButton("Insert Data");
        insert.setPreferredSize(new Dimension(250, 30));
        insert.addActionListener(this);

        next = new JButton("Next");
        next.addActionListener(this);

        next1 = new JButton("Next");
        next1.addActionListener(this);

        prev = new JButton("Previous");
        prev.addActionListener(this);

        prev1 = new JButton("Previous");
        prev1.addActionListener(this);

        search = new JButton("Search");
        search.addActionListener(this);

        rb = new JButton("Return");
        rb.setPreferredSize(new Dimension(250, 30));
        rb.addActionListener(this);

        show = new JButton("Show");
        show.addActionListener(this);

        update = new JButton("Set");
        update.addActionListener(this);

        done = new JButton("Update");
        done.addActionListener(this);

        delete = new JButton("Delete");
        delete.addActionListener(this);

        exit = new JButton("Exit");
        exit.addActionListener(this);

        exit1 = new JButton("Return");
        exit1.addActionListener(this);

        panel = new JPanel();
        panel.setPreferredSize(new Dimension(400,130));
        panel.add(label);
        panel.add(field);
        panel.add(next);
        panel.add(prev);
        panel.add(search);
        panel.add(update);
        panel.add(delete);

        this.add(insert);
        this.add(panel);
        this.add(rb);
        this.add(show);
        this.setVisible(true);
        this.add(exit);

        frame = new JFrame();
        frame.setSize(350,300);
        frame.setLayout(new FlowLayout());
        frame.add(new JScrollPane(table));
        frame.add(exit1);

        uframe = new JFrame();
        uframe.setSize(450,300);
        uframe.setLayout(new FlowLayout());
        uframe.add(label1);
        uframe.add(field1);
        uframe.add(next1);
        uframe.add(prev1);
        uframe.add(done);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == insert){
            new enterData(db,tname);
            field.setText(null);
            frame.dispose();
        }

        if(e.getSource() == next || e.getSource() == next1){

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from " + tname );
                ResultSetMetaData rst=(ResultSetMetaData) rs.getMetaData();

                if(cur+1 > rst.getColumnCount()){
                    cur  = 1;
                }else{
                    cur++;
                }

                label.setText(rst.getColumnLabel(cur));
                label1.setText(rst.getColumnLabel(cur));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "" + ex);
                System.out.println(ex);
                ex.printStackTrace();
            }

        }

        if(e.getSource() == prev || e.getSource()== prev1){
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from " + tname );
                ResultSetMetaData rst=(ResultSetMetaData) rs.getMetaData();

                if(cur == 1){
                    cur = rst.getColumnCount();
                }else{
                    cur--;
                }

                label.setText(rst.getColumnLabel(cur));
                label1.setText(rst.getColumnLabel(cur));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "" + ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
        }

        if(e.getSource()==search) {

            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill fields","Fields empty", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "If table is empty that means no record found");

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, "root", "root");
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("select * from " + tname);
                    ResultSetMetaData rst = (ResultSetMetaData) rs.getMetaData();
                    label.setText(rst.getColumnLabel(cur));
                    if (rst.getColumnTypeName(cur) == "VARCHAR" || rst.getColumnTypeName(cur) == "CHAR") {
                        sql = "select * from " + tname + " where " + rst.getColumnLabel(cur) + " = '" + field.getText() + "';";
                    } else {
                        sql = "select * from " + tname + " where " + rst.getColumnLabel(cur) + " = " + field.getText() + ";";
                    }
                    rs = statement.executeQuery(sql);
                    table.setModel(DbUtils.resultSetToTableModel(rs));

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "" + ex);
                    System.out.println(ex);
                    ex.printStackTrace();
                }
                frame.setVisible(true);
                field.setText(null);
            }
        }

        if(e.getSource() == delete) {

            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill fields", "Fields empty", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, "root", "root");
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery("select * from " + tname);
                    ResultSetMetaData rst = (ResultSetMetaData) rs.getMetaData();
                    sql = "delete from " + tname + " where " + rst.getColumnName(cur) + " = " + field.getText() + ";";
                    if (rst.getColumnTypeName(cur) == "VARCHAR" || rst.getColumnTypeName(cur) == "CHAR") {
                        sql = "delete from " + tname + " where " + rst.getColumnName(cur) + " = '" + field.getText() + "';";
                    } else {
                        sql = "delete from " + tname + " where " + rst.getColumnName(cur) + " = " + field.getText() + ";";
                    }
                    statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Row deleted where " + rst.getColumnLabel(cur) + " = " + field.getText());

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "" + ex);
                    System.out.println(ex);
                    ex.printStackTrace();
                }
                field.setText(null);
            }
        }

        if(e.getSource() == show) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db, "root", "root");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("select * from " + tname);
                ResultSetMetaData rst = (ResultSetMetaData) rs.getMetaData();
                table.setModel(DbUtils.resultSetToTableModel(rs));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "" + ex);
                System.out.println(ex);
                ex.printStackTrace();
            }
            field.setText(null);
            frame.setVisible(true);
        }

        if(e.getSource() == rb){
            this.dispose();
            new createTable(db);
        }

        if(e.getSource()==update) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill fields", "Fields empty", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    CONNEKT obj = new CONNEKT();
                    Statement st = obj.KON(db).createStatement();
                    ResultSet rs = st.executeQuery("select * from " + tname);
                    ResultSetMetaData rsmt = (ResultSetMetaData) rs.getMetaData();
                    uframe.setVisible(true);
                    if (rsmt.getColumnTypeName(cur) == "VARCHAR" || rsmt.getColumnTypeName(cur) == "CHAR") {
                        sql = "UPDATE " + tname + " set " + rsmt.getColumnLabel(cur) + " = " + "'" + field.getText() + "'" + " where ";
                    } else {
                        sql = "UPDATE " + tname + " set " + rsmt.getColumnLabel(cur) + " = " + field.getText() + " where ";
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                }

            }
        }

        if(e.getSource()==done) {

            if (field1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Fill fields", "Fields empty", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    CONNEKT obj = new CONNEKT();
                    Statement st = obj.KON(db).createStatement();
                    ResultSet rs = st.executeQuery("select * from " + tname);
                    ResultSetMetaData rsmt = (ResultSetMetaData) rs.getMetaData();
//                sql =  sql + rsmt.getColumnLabel(cur) + " " + field1.getText() + ";";
                    uframe.setVisible(true);
                    if (rsmt.getColumnTypeName(cur) == "VARCHAR" || rsmt.getColumnTypeName(cur) == "CHAR") {
                        sql = sql + rsmt.getColumnLabel(cur) + " = '" + field1.getText() + "';";
                    } else {
                        sql = sql + rsmt.getColumnLabel(cur) + " = " + field1.getText() + ";";
                    }
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "UPDATE");

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex);
                    System.out.println(ex);
                    ex.printStackTrace();
                }
                uframe.dispose();
                field.setText(null);
            }
        }

        if(e.getSource()==exit1){
            frame.dispose();
            uframe.dispose();
        }
        if(e.getSource()==exit) {
            this.dispose();
        }
    }
}
