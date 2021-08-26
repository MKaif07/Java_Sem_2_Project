import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class tb extends JFrame implements ActionListener {

    private JButton button;
    private JTable table;
    private DefaultTableModel model;
    private String sql;
    private ResultSetMetaData rst;

    tb(){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(new FlowLayout());

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
            Statement statement = connection.createStatement();
            sql = "select * from stud;";
            ResultSet rs = statement.executeQuery(sql);
            rst = rs.getMetaData();
            int row = 0;
            while(rs.next()){
                //data will be added until finish
                String rollno = String.valueOf(rs.getInt("rollno"));
                String Name = rs.getString("Name");
                String []tbData = {rollno, Name};
                model = (DefaultTableModel)table.getModel();
                model.insertRow(row, tbData);
                row++;

            }

            connection.close();
            System.out.println("Done");
        } catch (SQLException ex){
            System.out.println(ex);
            ex.printStackTrace();
        }

        button = new JButton("Show Data");
        button.setPreferredSize(new Dimension(250,50));
        Object [][] data = {{null}};
        try {
            for(int i=0; i<rst.getColumnCount(); i++) {
                String[] col = { rst.getColumnName(i+1) };
            }
        }catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
//        model = new DefaultTableModel(data, col);
        table = new JTable(model);
        table.setPreferredSize(new Dimension(300,300));

        button.addActionListener(this);


        this.add(button);
        this.add(new JScrollPane(table));
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Running");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
            Statement statement = connection.createStatement();
            sql = "select * from stud;";
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rst = rs.getMetaData();
            int row = 0;
            while(rs.next()){
                //data will be added until finish
                String rollno = String.valueOf(rs.getInt("rollno"));
                String Name = rs.getString("Name");
                String []tbData = {rollno, Name};
                model = (DefaultTableModel)table.getModel();
                model.insertRow(row, tbData);
                row++;

            }

            connection.close();
            System.out.println("Done");
        } catch (SQLException ex){
            System.out.println(ex);
            ex.printStackTrace();
        }

        Object [][] data = {{}};
        String [] col = {"rollno", "Name"};
        model = new DefaultTableModel(data, col);
        table = new JTable(model);
        table.setPreferredSize(new Dimension(300,300));


    }
}
