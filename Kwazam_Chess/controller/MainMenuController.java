package Kwazam_Chess.controller;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import Kwazam_Chess.view.MainMenuView;
import Kwazam_Chess.view.SelectTimeView;
import Kwazam_Chess.view.GameView;
import Kwazam_Chess.model.GameData;
import Kwazam_Chess.model.GameModel;

public class MainMenuController {
    private MainMenuView mainMenuView;

    public MainMenuController(MainMenuView mainMenuView) {
        this.mainMenuView = mainMenuView;
        this.mainMenuView.addStartListener(e -> openSelectTime());
        this.mainMenuView.addLoadListener(e -> openSelectLoad());
        this.mainMenuView.addExitListener(e -> exit());
    }

    private void openSelectTime() { // Start button
        // Create the selectTimeView (View)
        SelectTimeView selectTimeView = new SelectTimeView();
        
        // Create the selectTimeController (Controller) and pass the View to it
        SelectTimeController selectTimeController = new SelectTimeController(selectTimeView);
        mainMenuView.dispose(); // Close current view
    }
    
    private void openSelectLoad() { // Load button
        try {
            // Specify the file name for loading the game
            String fileName = "Kwazam_Chess//saveFile.txt"; // Replace with file selection logic if needed
            
            // Load the game data
            GameData gameData = new GameData();
            GameModel gameModel = new GameModel(gameData);
            gameModel.loadGame(fileName);  // This will load the saved game state, including currentTurn

            // Create a new GameView with the loaded game data
            GameView gameView = new GameView(gameData);
            
            // Create a new GameController for the loaded game
            GameController gameController = new GameController(gameView, gameModel);
            
            // Ensure the view updates correctly
            gameView.updateTurnAndPlayer(); // This will update the turn and player labels in the view

            // Close the main menu
            mainMenuView.dispose();
        } catch (Exception e) {
            System.err.println("Error loading game: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void exit() { // Exit button
        mainMenuView.dispose(); // Close current view
    }
}

