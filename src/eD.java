import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class eD implements ActionListener {

    JFrame frame;
    JLabel Name;
    JLabel Roll;
    JPanel p1;
    JTextField fName;
    JTextField fRoll;
    JPanel p2;
    JButton Add;
    JButton Update;
    JButton Delete;
    JButton Clear;

    Connection con;
    String sql ="";
    String name;
    Statement st;
    int roll;

    eD(){

        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "root");
            st = con.createStatement();
//            int res = st.executeUpdate(sql);
        } catch (SQLException ex){
            System.out.println(ex);
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Add,ex);
        }

        frame = new JFrame();
        Name = new JLabel("Name");
        Roll = new JLabel("Roll No.");
        fName = new JTextField();
        fRoll = new JTextField();
        p1 = new JPanel();
        p2 = new JPanel();
        Add =  new JButton("ADD");
        Update =  new JButton("Update");
        Delete =  new JButton("Delete");
        Clear =  new JButton("Clear");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(new GridLayout(4,4));

        p1.setLayout(new GridLayout(2,2,10,10));
        p1.setAlignmentY((JPanel.BOTTOM_ALIGNMENT));
        p1.setOpaque(true);

        Add.addActionListener(this);
        Update.addActionListener(this);
        Delete.addActionListener(this);







        p1.add(Name);
        p1.add(fName);
        p1.add(Roll);
        p1.add(fRoll);
        p2.add(Add);
        p2.add(Update);
        p2.add(Delete);
        p2.add(Clear);
        frame.add(p1);
        frame.add(p2);
        frame.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Add){
            try {
                name = fName.getText();
                roll = Integer.parseInt(fRoll.getText());
                sql = "insert into stud values (" + roll + ", '" + name + "');";
                int res = st.executeUpdate(sql);
            } catch (SQLException ex){
                System.out.println(ex);
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Add,ex);
                System.out.println(sql);
            }
            JOptionPane.showMessageDialog(Add, "Record added");
        }

        if(e.getSource()==Update){
            try{
                name = fName.getText();
                roll = Integer.parseInt(fRoll.getText());
                sql = "update stud set Name='"+ name + "' where rollno =" +roll +";";
                JOptionPane.showMessageDialog(Update,"Record Updated");
                st.executeUpdate(sql);
                System.out.println(sql);
            }catch (SQLException e1){
                System.out.println();
                e1.printStackTrace();
                JOptionPane.showMessageDialog(Update,e1);
            }
        }
        if(e.getSource()==Delete){
            try{
                name = fName.getText();
                roll = Integer.parseInt(fRoll.getText());
                sql = "delete from stud where rollno =" + roll +";";
                JOptionPane.showMessageDialog(Update,"Record Deleted");
                st.executeUpdate(sql);
                System.out.println(sql);
            }catch (SQLException e1){
                System.out.println();
                e1.printStackTrace();
                JOptionPane.showMessageDialog(Update,e1);
            }
        }

    }
}
