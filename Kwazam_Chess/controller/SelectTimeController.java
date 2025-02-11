package Kwazam_Chess.controller;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import Kwazam_Chess.view.SelectTimeView;
import Kwazam_Chess.view.MainMenuView;
import Kwazam_Chess.view.GameView;
import Kwazam_Chess.model.GameModel;
import Kwazam_Chess.model.GameData;

public class SelectTimeController {
    private SelectTimeView selectTimeView;

    public SelectTimeController(SelectTimeView selectTimeView) {
        this.selectTimeView = selectTimeView;

        // Add listeners to buttons in the view
        this.selectTimeView.addStandardListener(e -> startGameWithTime(10));  // Standard game - 10 minutes
        this.selectTimeView.addRapidListener(e -> startGameWithTime(5));     // Rapid game - 5 minutes
        this.selectTimeView.addBlitzListener(e -> startGameWithTime(3));     // Blitz game - 3 minutes
        this.selectTimeView.addBackListener(e -> back());
    }

    private void startGameWithTime(int selectedTime) {
    GameData gameData = new GameData();
    
    gameData.setSelectedTimeInMinutes(selectedTime); // Set the time directly
    GameModel gameModel = new GameModel(gameData);  // Pass GameData to GameModel
    GameView gameView = new GameView(gameData);     // Pass GameData to GameView

    GameController gameController = new GameController(gameView, gameModel);

    selectTimeView.dispose(); // Close the current view
    }

    private void back() {
        // Create the MainMenuView (View)
        MainMenuView mainMenuView = new MainMenuView();

        // Create the MenuController (Controller) and pass the View to it
        MainMenuController menuController = new MainMenuController(mainMenuView);

        // Close the current SelectTimeView
        selectTimeView.dispose();
    }
}
