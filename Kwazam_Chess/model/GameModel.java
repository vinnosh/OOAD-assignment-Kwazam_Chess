package Kwazam_Chess.model;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
import java.util.ArrayList;

public class GameModel {
    private GameData gameData;
    private Timer gameTimer;
    private TimeListener timeListener;

    public GameModel(GameData gameData) {
        this.gameData = gameData;
        this.gameTimer = new Timer();
    }

    // Save the game state to a text file
    public void saveData() {
        String filePath = "Kwazam_Chess//saveFile.txt";
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Save GameData (turn, time remaining, etc.)
            writer.write("Current Turn: " + gameData.getCurrentTurn() + "\n");
            writer.write("Time Remaining: " + gameData.getTimeRemaining() + "\n");
            writer.write("Red Kills: " + gameData.getRedKills() + "\n");
            writer.write("Blue Kills: " + gameData.getBlueKills() + "\n\n");
            writer.write("Piece Col Row Team[True = Red/ False = Blue] Direction[True = Forward/ False = Backward]\n");
            // Save each piece on the board
            for (Piece piece : gameData.getPieceList()) {
                if (piece instanceof Ram) {
                    Ram ram = (Ram) piece;
                    writer.write(piece.getClass().getSimpleName() + "   " 
                                 + piece.col + "   " 
                                 + piece.row + "   " 
                                 + piece.isRed + "                           " 
                                 + ram.isMovingForward() + "\n");
                } else {
                    writer.write(piece.getClass().getSimpleName() + "   " 
                                 + piece.col + "   " 
                                 + piece.row + "   " 
                                 + piece.isRed + "\n");
                }
            }
    
        } catch (IOException iOE) {
            System.err.println("Error writing to file: " + iOE.getMessage());
        }
    }

    // Load the game state from a text file
    public void loadGame(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {  
            // Variables to store updated data
            ArrayList<String> loadedPieceData = new ArrayList<>();
            gameData.clearPieces(); // Clear the existing pieces
    
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Current Turn: ")) {
                    int loadCurrentTurn = Integer.parseInt(line.split(": ")[1].trim());
                    gameData.setCurrentTurn(loadCurrentTurn);
                } else if (line.startsWith("Time Remaining: ")) {
                    int loadTimeRemaining = Integer.parseInt(line.split(": ")[1].trim());
                    gameData.setTimeRemaining(loadTimeRemaining);
                } else if (line.startsWith("Red Kills: ")) {
                    int loadRedkill = Integer.parseInt(line.split(": ")[1].trim());
                    gameData.setRedKills(loadRedkill);
                } else if (line.startsWith("Blue Kills: ")) {
                    int loadBluekill = Integer.parseInt(line.split(": ")[1].trim());
                    gameData.setBlueKills(loadBluekill);
                } else if (line.startsWith("Piece")) {
                    // Pass (Category header no need to load only for easy reading in textfile)
                } else {
                    // Treat as piece data
                    loadedPieceData.add(line.trim());
                }
            }
    
            // Replace the pieceData array with the loaded data
            String[] newPieceData = loadedPieceData.toArray(new String[0]);
            gameData.setPieceData(newPieceData);
            
        } catch (IOException e) {
            System.err.println("File I/O error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void startTimer() {
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (gameData.isGameRunning()) {
                    gameData.setTimeRemaining(gameData.getTimeRemaining() - 1);
                    if (timeListener != null) {
                        timeListener.onTimeUpdate(gameData.getTimeRemaining());
                    }
                    if (gameData.getTimeRemaining() <= 0) {
                        endGame();
                    }
                }
            }
        }, 0, 1000); // Run every second
    }
    
    public void stopTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

    public void addTimeListener(TimeListener listener) {
        this.timeListener = listener;
    }
    
    public void endGame() {
        gameData.setGameRunning(false);
    }

    // Listener interface for time updates
    public interface TimeListener {
        void onTimeUpdate(int timeRemaining);
    }
}
