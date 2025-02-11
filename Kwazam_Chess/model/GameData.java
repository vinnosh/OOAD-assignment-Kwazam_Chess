package Kwazam_Chess.model;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import java.util.ArrayList;

public class GameData {
    private Board board;
    private int timeRemaining;
    private int currentTurn;
    private boolean gameRunning;
    private int selectedTimeInMinutes;
    private ArrayList<Piece> pieceList;
    private int redKills;
    private int blueKills;

    private String[] pieceData = {
        "Ram 0 1 true",
        "Ram 0 6 false",
        "Ram 1 1 true",
        "Ram 1 6 false",
        "Ram 2 1 true",
        "Ram 2 6 false",
        "Ram 3 1 true",
        "Ram 3 6 false",
        "Ram 4 1 true",
        "Ram 4 6 false",
        "Xor 4 0 true",
        "Xor 0 7 false",
        "Biz 1 0 true",
        "Biz 3 0 true",
        "Biz 1 7 false",
        "Biz 3 7 false",
        "Tor 0 0 true",
        "Tor 4 7 false",
        "Sau 2 0 true",
        "Sau 2 7 false"
    };
    
    // Default constructor
    public GameData() {
        this.currentTurn = 1;
        this.timeRemaining = 1;
        this.gameRunning = true;
        this.pieceList = new ArrayList<>();
        this.redKills = 0;
        this.blueKills = 0;
    }

    public String[] getPieceData() {
        return pieceData;
    }

    public void setSelectedTimeInMinutes(int selectedTimeInMinutes) {
        this.selectedTimeInMinutes = selectedTimeInMinutes;
        this.timeRemaining = selectedTimeInMinutes * 60;
    }
    
    // Getter for selectedTimeInMinutes
    public int getSelectedTimeInMinutes() {
        return selectedTimeInMinutes;
    }
    
    public void setPieceData(String[] pieceData) {
        this.pieceData = pieceData;
    }
    // Getters and Setters
    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
        // gameView.updateTurnAndPlayer();
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public ArrayList<Piece> getPieceList() {
        return pieceList;
    }

    public void setPieceList(ArrayList<Piece> pieceList) {
        this.pieceList = pieceList;
    }

    // Helper methods for managing turns and pieces
    public void incrementTurn() {
        this.currentTurn++;
    }

    public void incrementBlueKills() {
        this.blueKills++;
    }

    public void incrementRedKills() {
        this.redKills++;
    }

    public int getBlueKills() {
        return blueKills;
    }

    public int getRedKills() {
        return redKills;
    }

    public void setBlueKills(int loadBlueKill) {
        this.blueKills = loadBlueKill;
    }

    public void setRedKills(int loadRedKill) {
        this.redKills = loadRedKill;
    }

    public void addPiece(Piece piece) {
        this.pieceList.add(piece);
    }

    public void removePiece(Piece piece) {
        this.pieceList.remove(piece);
    }

    public void clearPieces() {
        this.pieceList.clear();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
