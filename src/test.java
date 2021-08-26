import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test extends JFrame implements ActionListener {

    String field ;
    int num;
    JTextField textField;
    JTextField intField;

    test(){
        ImageIcon icon = new ImageIcon("");
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        textField = new JTextField();
        textField.setBounds(50,50,250,50);

        intField = new JTextField();
        intField.setBounds(50,50,250,50);

        JButton button = new JButton("DONE");
        button.setBounds(50,100,50,40);
        button.addActionListener(this);

        field = textField.getText();

        System.out.println(field);

        this.add(intField);
        this.add(button);
        this.setVisible(true);
    }

    public static void main(String[] args) {

        new test();
//        System.out.println(field);



    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        field = textField.getText();
//        System.out.println(field);
        num = Integer.parseInt(intField.getText());

        System.out.println(num);
    }
}
