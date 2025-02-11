//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method

package Kwazam_Chess;

import Kwazam_Chess.view.MainMenuView;
import Kwazam_Chess.controller.MainMenuController;

public class KwazamChess_Main {
    public static void main(String[] args) {
        // Create the MainMenuView (View)
        MainMenuView mainMenuView = new MainMenuView();
        
        // Create the MenuController (Controller) and pass the View to it
        MainMenuController menuController = new MainMenuController(mainMenuView);
    }
}

