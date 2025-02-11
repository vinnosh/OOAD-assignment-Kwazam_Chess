package Kwazam_Chess.view;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SelectTimeView {
    private JFrame frame;
    private JButton standardButton;
    private JButton rapidButton;
    private JButton blitzButton;
    private JButton backButton;
    
    public SelectTimeView() {
        // Create the frame
        frame = new JFrame("Kwazam Chess - Select Time");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the Window icon
        Image WindowIcon = Toolkit.getDefaultToolkit().getImage("Kwazam_Chess/Image/Window_Icon.png");
        frame.setIconImage(WindowIcon);

        // Set the layout for the frame (5 rows, 1 column)
        frame.setLayout(new GridLayout(5, 1));

        // First row: JLabel with black background, white font, and bold text
        JLabel label = new JLabel("Choose your Game time", JLabel.CENTER);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setOpaque(true); // Make the background color visible
        frame.add(label);

        // Utility method for button setup
        standardButton = createButton("Standard - 10 minute");
        rapidButton = createButton("Rapid - 5 minute");
        blitzButton = createButton("Blitz - 3 minute");
        backButton = createButton2("Back");


        // Add buttons to the frame
        frame.add(standardButton);
        frame.add(rapidButton);
        frame.add(blitzButton);
        frame.add(backButton);

        // Set the size of the frame
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    
    public void dispose() {
        frame.dispose(); // Dispose of the JFrame
    }
    
    // Add a listener to button
    public void addStandardListener(ActionListener listener) {
        standardButton.addActionListener(listener);
    }
    
    public void addRapidListener(ActionListener listener) {
        rapidButton.addActionListener(listener);
    }
    
    public void addBlitzListener(ActionListener listener) {
        blitzButton.addActionListener(listener);
    }
    
    public void addBackListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    
    // ButtonSetup ((Additional Design Pattern: Factory Method))
    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false); // Remove focus outline
        button.setOpaque(true); // None transparent
        button.setBorderPainted(false); //Border highlight

        // Common hover effect using a single MouseAdapter
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY);
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.WHITE);
                button.setFont(new Font("Arial", Font.PLAIN, 20));
                button.setForeground(Color.BLACK);
            }
        });

        return button;
    }
    
    // ButtonSetup2 (Different MouseAdapter Effect) ((Additional Design Pattern: Factory Method))
    private static JButton createButton2(String text) { // For back button
        JButton button = new JButton(text);
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false); // Remove outline
        button.setOpaque(true); // None transparent
        button.setBorderPainted(false); //Border highlight

        // Common hover effect using a single MouseAdapter
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY);
                button.setFont(new Font("Arial", Font.BOLD, 20));
                button.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Color.GRAY);
                button.setFont(new Font("Arial", Font.PLAIN, 20));
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }
}

