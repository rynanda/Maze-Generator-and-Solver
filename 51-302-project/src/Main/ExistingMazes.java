package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExistingMazes implements ActionListener {
    JFrame frame = new JFrame();
    JButton returnHome = new JButton();
    JTable existingMaze = new JTable();


    ExistingMazes(){

        returnHome.setBounds(100, 400, 200, 50);
        returnHome.setText("Return To Home Page");
        returnHome.setFocusable(false);
        returnHome.setFont(new Font("DM Sans",Font.BOLD,12));
        returnHome.setForeground(Color.WHITE);
        returnHome.setBackground(Color.RED);
        returnHome.addActionListener(this);

        JLabel title = new JLabel();
        title.setText("Existing Mazes"); //creates label for main login screen
        title.setHorizontalAlignment(JLabel.LEFT);
        title.setVerticalAlignment(JLabel.TOP);
        title.setForeground(new Color(0xCF2A27));
        title.setFont(new Font("DM Sans",Font.BOLD,72));
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
        frame.add(returnHome);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==returnHome) {
            frame.dispose();
            HomePage homePage = new HomePage();

        }

    }
}

