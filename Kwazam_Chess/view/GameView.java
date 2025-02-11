package Kwazam_Chess.view;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import Kwazam_Chess.model.Board;
import Kwazam_Chess.model.GameData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameView {
    GameData gameData = new GameData();
    
    private JButton mainButton;
    private JButton saveButton;
    private JLabel playerLabel;
    private JLabel turnLabel;
    private JLabel timeLabel;
    private JPanel boardWrapper;
    private JFrame frame;

    public GameView(GameData gameData) {
        this.gameData = gameData; // Use the shared GameData instance
        frame = new JFrame("Kwazam Chess");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(484, 800);
        frame.setLayout(new BorderLayout());
        
        // Set the Window icon
        Image windowIcon = Toolkit.getDefaultToolkit().getImage("Kwazam_Chess/Image/Window_Icon.png");
        frame.setIconImage(windowIcon);

        // Create the top panel for player info and other details
        JPanel topPanel = new JPanel(new GridLayout(1, 5));
        topPanel.setPreferredSize(new Dimension(frame.getWidth(), 45));
        topPanel.setBackground(Color.GRAY);
        
        mainButton = createButton("Main", Color.GRAY, Color.WHITE);
        saveButton = createButton("Save", Color.GRAY, Color.WHITE);
        playerLabel = createLabel("Player ", Color.WHITE, Color.BLUE);
        turnLabel = createLabel("Turn 1", Color.WHITE, Color.BLACK);
        timeLabel = createLabel("00:00", Color.WHITE, Color.BLACK);

        // Add components to the top panel
        topPanel.add(mainButton);
        topPanel.add(playerLabel);
        topPanel.add(turnLabel);
        topPanel.add(timeLabel);
        topPanel.add(saveButton);

        // Create the custom game board
        Board customBoard = new Board(this,gameData);
        customBoard.setBackground(Color.GRAY);

        // Add a bezel (decorative border) around the game board
        boardWrapper = new JPanel(new BorderLayout());
        boardWrapper.setBorder(BorderFactory.createLineBorder(Color.BLUE, 30));
        boardWrapper.add(customBoard, BorderLayout.CENTER);

        // Add components to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(boardWrapper, BorderLayout.CENTER);

        frame.setVisible(true);
        updateTurnAndPlayer();
    }
    
    // Countdown Timer
    public void updateTimeLabel(int timeRemaining) {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
    
    // Update Turn
    public void updateTurnAndPlayer() {
        int currentTurn = gameData.getCurrentTurn();
        if (currentTurn % 2 == 0) {
            playerLabel.setText("Player 2");
            playerLabel.setForeground(Color.RED);
            turnLabel.setText("Turn " + currentTurn);
            boardWrapper.setBorder(BorderFactory.createLineBorder(Color.RED, 30));
        } else {
            playerLabel.setText("Player 1");
            playerLabel.setForeground(Color.BLUE);
            turnLabel.setText("Turn " + currentTurn);
            boardWrapper.setBorder(BorderFactory.createLineBorder(Color.BLUE, 30));
        }
    }

    public void addMainListener(ActionListener listener) {
        mainButton.addActionListener(listener);
    }

    public void addSaveListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void dispose() {
        if (frame != null) {
            frame.dispose();
        }
    }
    
    public JFrame getFrame() {
        return frame;
    }
    
    // ButtonSetup ((Additional Design Pattern: Factory Method))
    private static JButton createButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY);
                button.setForeground(Color.BLACK);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
                button.setForeground(fgColor);
            }
        });

        return button;
    }
    
    // Label Setup (Additional Design Pattern: Factory Method)
    private static JLabel createLabel(String text, Color bgColor, Color fgColor) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setForeground(fgColor);
        return label;
    }
}
