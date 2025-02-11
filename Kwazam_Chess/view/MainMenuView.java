package Kwazam_Chess.view;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuView {
    private JFrame frame;
    private JButton startButton;
    private JButton loadButton;
    private JButton exitButton;

    public MainMenuView() {
        // Create the frame
        frame = new JFrame("Kwazam Chess - Main Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Set the Window icon
        Image WindowIcon = Toolkit.getDefaultToolkit().getImage("Kwazam_Chess/Image/Window_Icon.png");
        frame.setIconImage(WindowIcon);

        // Set the layout for the frame (4 rows, 1 column)
        frame.setLayout(new GridLayout(4, 1));

        // First row: JLabel with black background, white font, and bold text
        JLabel label = new JLabel("Kwazam Chess", JLabel.CENTER);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setOpaque(true); // Make the background color visible
        frame.add(label);

        // Initialize buttons
        startButton = createButton("Start");
        loadButton = createButton("Load");
        exitButton = createButton("Exit");

        // Add buttons to the frame
        frame.add(startButton);
        frame.add(loadButton);
        frame.add(exitButton);

        // Set the size of the frame
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
    
    public void dispose() {
        frame.dispose(); // Dispose of the JFrame
    }
    
    // Add a listener to button
    public void addStartListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }
    
    public void addLoadListener(ActionListener listener) {
        loadButton.addActionListener(listener);
    }
    
    public void addExitListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
    
    // ButtonSetup ((Additional Design Pattern: Factory Method))
    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false); // Remove focus outline
        button.setOpaque(true);
        button.setBorderPainted(false); // Border highlight

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
}

