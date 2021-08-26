import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class createTable extends JFrame implements ActionListener {
    JFrame frame;
    JPanel panel;
    JLabel label,Title,tName;
    private JTable table;
    private DefaultTableModel model;
    private String sql;
    JTextField field;
    JButton cT, dT, sT,Done, rb;
    String feel;
    String name ;
    String db;

    public createTable(String database){

        db = database;
        System.out.println(db);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());

        panel = new JPanel();
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(355, 250));

        label = new JLabel("Enter Table: ");
        label.setPreferredSize(new Dimension(95, 50));

        sT = new JButton("Show Tables");
        sT.setPreferredSize(new Dimension(250, 30));
        sT.addActionListener(this);

        Done = new JButton("Select");
        Done.setPreferredSize(new Dimension(250, 30));
        Done.addActionListener(this);

        field = new JTextField(null);
        field.setText(null);
        field.setPreferredSize(new Dimension(250, 50));

        cT = new JButton("Create Tables");
        cT.setPreferredSize(new Dimension(250, 30));
        cT.addActionListener(this);

        dT = new JButton("Delete Tables");
        dT.setPreferredSize(new Dimension(250, 30));
        dT.addActionListener(this);

        rb = new JButton("Return");
        rb.setPreferredSize(new Dimension(250, 30));
        rb.addActionListener(this);

        Object[][] data = {{}};
        String[] col = {"Tables"};
        model = new DefaultTableModel(data, col);
        table = new JTable(model);
        table.setPreferredSize(new Dimension(300, 300));

        panel.add(label);
        panel.add(field);
        panel.add(cT);
        panel.add(dT);
        panel.add(sT);
        panel.add(Done);
        panel.add(rb);
        this.add(panel);
        this.add(new JScrollPane(table));

        this.setVisible(true);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sT) {

            JOptionPane.showMessageDialog(null,"If you are creating a TABLE make sure that doesn't already exist or you will get an error");
            model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("show tables;");
                while (rs.next()) {
                    //data will be added until finish
                    int row = 0;
                    String[] tbData = {rs.getString("Tables_in_"+db)};
                    model = (DefaultTableModel) table.getModel();
                    model.insertRow(row, tbData);
                    row++;
                }

            } catch (SQLException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        }

        if (e.getSource() == cT) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Table","Fields empty", JOptionPane.ERROR_MESSAGE);
            } else {
                name = field.getText();
                new fields(db, name);
                JOptionPane.showMessageDialog(null, "Add fields in " + name);
                dispose();
            }
        }

        if (e.getSource() == dT) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Table","Fields empty", JOptionPane.ERROR_MESSAGE);
            } else {

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db, "root", "root");
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("drop table " + field.getText() + ";");
                    JOptionPane.showMessageDialog(null, "" + field.getText() + " Table deleted");
                } catch (SQLException ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                }
                field.setText(null);
            }

        }

        if(e.getSource() == Done) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Table","Fields empty", JOptionPane.ERROR_MESSAGE);
            } else {
                name = field.getText();
                this.dispose();
                JOptionPane.showMessageDialog(null, "Now You are working on  " + field.getText(), "Done", JOptionPane.PLAIN_MESSAGE);
                System.out.println("Table name: " + name);
                new useTable(db, name);
            }
        }
        if(e.getSource() == rb){
            this.dispose();
            new showdatabases();
        }
    }

}


