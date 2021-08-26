import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class showdatabases extends JFrame implements ActionListener {

    private JButton button, Done, cd, dd;
    private JTextField field;
    private JLabel label;
    private JPanel panel;
    private JTable table;
    private DefaultTableModel model;
    private String sql;
    public String db;

    showdatabases() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new FlowLayout());

        panel = new JPanel();
        panel.setOpaque(true);
        panel.setPreferredSize(new Dimension(355, 200));

        label = new JLabel("Enter Database: ");
        label.setPreferredSize(new Dimension(95, 50));

        button = new JButton("Show databases");
        button.setPreferredSize(new Dimension(250, 30));
        button.addActionListener(this);

        Done = new JButton("Use");
        Done.setPreferredSize(new Dimension(250, 30));
        Done.addActionListener(this);

        field = new JTextField(null);
        field.setPreferredSize(new Dimension(250, 50));

        cd = new JButton("Create databases");
        cd.setPreferredSize(new Dimension(250, 30));
        cd.addActionListener(this);

        dd = new JButton("Delete databases");
        dd.setPreferredSize(new Dimension(250, 30));
        dd.addActionListener(this);

        Object[][] data = {{}};
        String[] col = {"Databases"};
        model = new DefaultTableModel(data, col);
        table = new JTable(model);
        table.setPreferredSize(new Dimension(300, 300));


        panel.add(label);
        panel.add(field);
        panel.add(cd);
        panel.add(dd);
        panel.add(button);
        panel.add(Done);
        this.add(panel);
        this.add(new JScrollPane(table));

        this.setVisible(true);
        this.pack();
        db = field.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {
            model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            try{
                CONNEKT obj = new CONNEKT();
                Statement st = obj.KON("").createStatement();
                st.executeQuery("show databases;");
                ResultSet rs = st.executeQuery("show databases;");
                while (rs.next()) {
                    //data will be added until finish
                    int row = 0;
                    String[] tbData = {rs.getString("Database")};
                    model = (DefaultTableModel) table.getModel();
                    model.insertRow(row, tbData);
                    row++;
                }
            } catch (SQLException ex) {
                System.out.println(ex);
                ex.printStackTrace();
            }
        }

        if (e.getSource() == cd) {
            if (field.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Enter Database","Fields empty", JOptionPane.ERROR_MESSAGE);
            } else {

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
                    Statement statement = connection.createStatement();
                    statement.executeUpdate("create database " + field.getText() + ";");
                    JOptionPane.showMessageDialog(null, "" + field.getText() + " Database created");
                } catch (SQLException ex) {
                    System.out.println(ex);
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        }

            if (e.getSource() == dd) {
                if (field.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Database","Fields empty", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "root");
                        Statement statement = connection.createStatement();
                        statement.executeUpdate("drop database " + field.getText() + ";");
                        JOptionPane.showMessageDialog(null, "" + field.getText() + " Database deleted");
                    } catch (SQLException ex) {
                        System.out.println(ex);
                        ex.printStackTrace();
                    }
                }
            }

            if (e.getSource() == Done) {
                if (field.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter Database","Fields empty", JOptionPane.ERROR_MESSAGE);
                } else {
                    db = field.getText();
                    sql = "use " + db + ";";
                    dispose();
                    new createTable(db);
                }
            }
    }

}
