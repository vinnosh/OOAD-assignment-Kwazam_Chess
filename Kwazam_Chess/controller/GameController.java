package Kwazam_Chess.controller;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import Kwazam_Chess.view.GameView;
import Kwazam_Chess.model.GameModel;
import Kwazam_Chess.view.MainMenuView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameController {
    private GameView gameView;
    private GameModel gameModel;

    // Constructor accepting both GameView and GameModel
    public GameController(GameView view, GameModel model) {
        this.gameView = view;
        this.gameModel = model;

    // Add action listener to the Main button
    view.addMainListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Stop the game timer before navigating to the main menu
            gameModel.stopTimer();  // Stop the game timer in GameModel

            // Create the MainMenuView (View)
            MainMenuView mainMenuView = new MainMenuView();
            // Create the MenuController (Controller) and pass the View to it
            MainMenuController menuController = new MainMenuController(mainMenuView);

            gameView.dispose(); // Close the current view
        }
    });

    // Add action listener to the Save button
    view.addSaveListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Get the file
        String filePath = "Kwazam_Chess//saveFile.txt";  // File path

        try {
            // Call the saveGame method from the GameModel class, passing the file path
            gameModel.saveData();  // Ensure gameModel is accessible
            
            // Show success message
            JOptionPane.showMessageDialog(
                gameView.getFrame(),
                "Game saved successfully!",
                "Save Confirmation",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            // Handle exceptions and show an error message
            System.err.println("Error saving the game: " + ex.getMessage());
            ex.printStackTrace();
            
            JOptionPane.showMessageDialog(
                gameView.getFrame(),
                "An error occurred while saving the game.\n" + ex.getMessage(),
                "Save Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    });

    // Add a listener to the GameModel to update the GameView whenever the time changes
    gameModel.addTimeListener(view::updateTimeLabel);
        
    // Start the game timer
    gameModel.startTimer();
    }
}