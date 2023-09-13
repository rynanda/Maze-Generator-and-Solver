package Main;

import drawmaze.GenerateMaze;

import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ChildrensMazeGenerator implements ActionListener {
    JFrame frame = new JFrame();
    JButton returnHome = new JButton();
    JButton createMaze = new JButton();

    ChildrensMazeGenerator(){

        JCheckBox logoCheck = new JCheckBox();
        logoCheck.setText("Include Logo");
        logoCheck.setFocusable(false);
        logoCheck.setBounds(100, 475,100,30);
        logoCheck.setFont(new Font("DM Sans",Font.PLAIN,12));
        logoCheck.setBackground(new Color(162,196,201));

        JCheckBox dateTimeCheck = new JCheckBox();
        dateTimeCheck.setText("Include Date & Time of Creation");
        dateTimeCheck.setFocusable(false);
        dateTimeCheck.setBounds(100, 500,300,30);
        dateTimeCheck.setFont(new Font("DM Sans",Font.PLAIN,12));
        dateTimeCheck.setBackground(new Color(162,196,201));

        JTextField widthField = new JTextField();
        widthField.setPreferredSize(new Dimension(200,40));
        widthField.setBounds(240, 250, 100, 30);
        widthField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });

        JTextField heightField = new JTextField();
        heightField.setPreferredSize(new Dimension(200,40));
        heightField.setBounds(240, 300, 100, 30);
        heightField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });

        createMaze.setBounds(100, 525, 200, 50);
        createMaze.setText("Create Maze");
        createMaze.setFocusable(false);
        createMaze.setFont(new Font("DM Sans",Font.BOLD,12));
        createMaze.setForeground(Color.WHITE);
        createMaze.setBackground(Color.RED);
        createMaze.addActionListener(this);

        JLabel insertWidth = new JLabel();
        insertWidth.setText("Insert Width:"); //creates label for main login screen
        insertWidth.setHorizontalAlignment(JLabel.LEFT);
        insertWidth.setVerticalAlignment(JLabel.TOP);
        insertWidth.setForeground(new Color(0xCF2A27));
        insertWidth.setFont(new Font("DM Sans",Font.BOLD,20));
        insertWidth.setBounds (100, 250, 300, 250);

        JLabel insertHeight = new JLabel();
        insertHeight.setText("Insert Height:"); //creates label for main login screen
        insertHeight.setHorizontalAlignment(JLabel.LEFT);
        insertHeight.setVerticalAlignment(JLabel.TOP);
        insertHeight.setForeground(new Color(0xCF2A27));
        insertHeight.setFont(new Font("DM Sans",Font.BOLD,20));
        insertHeight.setBounds(100, 300, 300, 250);

        returnHome.setBounds(100, 600, 200, 50);
        returnHome.setText("Return To Home Page");
        returnHome.setFocusable(false);
        returnHome.setFont(new Font("DM Sans",Font.BOLD,12));
        returnHome.setForeground(Color.WHITE);
        returnHome.setBackground(Color.RED);
        returnHome.addActionListener(this);

        JLabel title = new JLabel();
        title.setText("Children Maze Generator"); //creates label for main login screen
        title.setHorizontalAlignment(JLabel.LEFT);
        title.setVerticalAlignment(JLabel.TOP);
        title.setForeground(new Color(0xCF2A27));
        title.setFont(new Font("DM Sans",Font.BOLD,72));
        title.setBounds(100, 100, 1000, 250);

        frame.setSize(1225,1000);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setTitle("Maze Generator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exits completely on application
        ImageIcon image = new ImageIcon("qut logo.png");
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(162,196,201));
        frame.add(title);
        frame.add(returnHome);
        frame.add(widthField);
        frame.add(heightField);
        frame.add(logoCheck);
        frame.add(dateTimeCheck);
        frame.add(createMaze);
        frame.add(insertWidth);
        frame.add(insertHeight);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==returnHome) {
            frame.dispose();
            HomePage homePage = new HomePage();

        }

        if(e.getSource()==createMaze) {
            frame.dispose();
            try {
                GenerateMaze newMaze = new GenerateMaze(50,50,true);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }


    }
}
