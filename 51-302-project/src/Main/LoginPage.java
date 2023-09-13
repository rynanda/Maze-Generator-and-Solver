package Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class LoginPage implements ActionListener {
    JButton signIn = new JButton();
    JFrame frame = new JFrame();
    JLabel title = new JLabel();

    LoginPage(){
        Border border = BorderFactory.createLineBorder(Color.RED,3);


        signIn.setBounds(550, 400, 100, 50);
        signIn.setText("Enter");
        signIn.setFocusable(false);
        signIn.setFont(new Font("DM Sans",Font.BOLD,12));
        signIn.setForeground(Color.WHITE);
        signIn.setBackground(Color.RED);
        signIn.addActionListener(this);


        title.setText("Maze Generator"); //creates label for main login screen
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setForeground(new Color(0xCF2A27));
        title.setFont(new Font("DM Sans",Font.BOLD,72));
        title.setBorder(border);
        title.setBounds(100, 100, 1000, 250);


        frame.setSize(1225,1000);

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("Maze Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits completely on application
        ImageIcon image = new ImageIcon("qut logo.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(162,196,201));
        frame.add(title);
        frame.add(signIn);



    }
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==signIn) {
            frame.dispose();
            HomePage homePage = new HomePage();

        }

    }
}
