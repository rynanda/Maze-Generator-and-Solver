package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditChildrensMazeGenerator implements ActionListener {
    JFrame frame = new JFrame();
    JButton returnHome = new JButton();

    JButton saveMaze = new JButton();

    JButton solution = new JButton();

    EditChildrensMazeGenerator(){

        returnHome.setBounds(100, 700, 200, 50);
        returnHome.setText("Return To Home Page");
        returnHome.setFocusable(false);
        returnHome.setFont(new Font("DM Sans",Font.BOLD,12));
        returnHome.setForeground(Color.WHITE);
        returnHome.setBackground(Color.RED);
        returnHome.addActionListener(this);

        saveMaze.setBounds(100, 650, 200, 50);
        saveMaze.setText("Save Maze");
        saveMaze.setFocusable(false);
        saveMaze.setFont(new Font("DM Sans",Font.BOLD,12));
        saveMaze.setForeground(Color.WHITE);
        saveMaze.setBackground(Color.RED);
        saveMaze.addActionListener(this);

        JLabel optimalSolution = new JLabel();
        optimalSolution.setText("Requires Exploring %:"); //creates label for main login screen
        optimalSolution.setHorizontalAlignment(JLabel.LEFT);
        optimalSolution.setVerticalAlignment(JLabel.TOP);
        optimalSolution.setForeground(new Color(0xCF2A27));
        optimalSolution.setFont(new Font("DM Sans",Font.BOLD,20));
        optimalSolution.setBounds (100, 250, 300, 250);

        JLabel showOptimalSolution = new JLabel();
        showOptimalSolution.setText(""); //creates label for main login screen
        showOptimalSolution.setHorizontalAlignment(JLabel.LEFT);
        showOptimalSolution.setVerticalAlignment(JLabel.TOP);
        showOptimalSolution.setForeground(new Color(0xCF2A27));
        showOptimalSolution.setFont(new Font("DM Sans",Font.BOLD,20));
        showOptimalSolution.setBounds (140, 250, 300, 250);

        solution.setBounds(100, 600, 200, 50);
        solution.setText("Show Solution");
        solution.setFocusable(false);
        solution.setFont(new Font("DM Sans",Font.BOLD,12));
        solution.setForeground(Color.WHITE);
        solution.setBackground(Color.RED);
        solution.addActionListener(this);


        JLabel title = new JLabel();
        title.setText("Edit Children Maze"); //creates label for main login screen
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
        frame.add(saveMaze);
        frame.add(optimalSolution);
        frame.add(showOptimalSolution);
        frame.add(solution);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==returnHome) {
            frame.dispose();
            HomePage homePage = new HomePage();

        }

    }
}