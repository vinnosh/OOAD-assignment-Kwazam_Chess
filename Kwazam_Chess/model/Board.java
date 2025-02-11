package Kwazam_Chess.model;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import Kwazam_Chess.view.MainMenuView;
import Kwazam_Chess.view.GameView;
import Kwazam_Chess.controller.MainMenuController;
import Kwazam_Chess.controller.Input;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.Timer;

public class Board extends JPanel {
    public int tileSize = 82;
    public int cols = 5;
    public int rows = 8;
    public int xOffset;
    public int yOffset;
    private Timer gameTimer;
    private GameView gameView;
    private Sau redKing;
    Input input = new Input(this);
    
    // Reference to GameData for game state management
    private GameData gameData;

    public Piece selectedPiece;
    public int draggedX = -1; // Temporary X position for dragging
    public int draggedY = -1; // Temporary Y position for dragging

    // No-argument constructor (will initialize with default GameData)
    public Board(GameView gameView, GameData gameData) {
        this.gameView = gameView;
        this.gameData = gameData; // Initialize with default game data
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPieces();
        
        // Timer to check
        gameTimer = new Timer(1000, e -> checkTimerAndEndGame());

        gameTimer.start();
    }

    // Constructor with GameData (used when GameData is already available)
    public Board(GameData gameData) {
        this.setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPieces();
    }

    public void addPieces() {
        gameData.getPieceList().clear(); // Clear current pieces
    
        // Use the pieceData from GameData to load pieces
        String[] pieceData = gameData.getPieceData();
        if (pieceData == null || pieceData.length == 0) {
            System.err.println("No piece data found to load.");
            return;
        }
    
        List<Piece> pieces = PieceSetup.loadPiecesFromData(this, pieceData);
        gameData.getPieceList().addAll(pieces);
    }

    public Piece getPiece(int col, int row) {
        for (Piece piece : gameData.getPieceList()) {
            if (piece.col == col && piece.row == row) {
                return piece;
            }
        }
        return null;
    }
    
    public void makeMove(Move move) {
        // Update the game state
        move.piece.col = move.newCol;
        move.piece.row = move.newRow;
    
        capture(move);
        gameData.incrementTurn(); // Update turn in GameData
        updateAllPieces();
        // Notify the GameView to update the UI
        
        if (gameView != null) {
            gameView.updateTurnAndPlayer();
        }
    
        repaint(); // Ensure the board updates
    }
    
    // Update the state of all Tor and Xor pieces
    public void updateAllPieces() {
        List<Piece> toUpdate = new ArrayList<>(gameData.getPieceList()); // Avoid concurrent modification
        for (Piece piece : toUpdate) {
            if (piece instanceof Tor) {
                // Transform Tor to Xor every 2 turns(counting one blue move and one red move as one turn)
                if (gameData.getCurrentTurn() % 4 == 0) {
                    ((Tor) piece).transformToXor();
                }
            }
            if (piece instanceof Xor) {
                // Transform Xor to Tor every 2 turns (counting one blue move and one red move as one turn)
                if (gameData.getCurrentTurn() % 4 == 0) {
                    ((Xor) piece).transformToTor();
                }
            }
        }
    }

    public void capture(Move move) {
        if (move.capture != null) {
        gameData.removePiece(move.capture);
        if (move.capture.isRed) {
                gameData.incrementBlueKills();
            } else {
                gameData.incrementRedKills();
        }
        // Check if a Sau piece was captured
        if (move.capture instanceof Sau) {
            showCheckmatePopup(move.capture.isRed ? "Blue" : "Red");
        }
        }
        
        if (move.piece instanceof Ram) {  // Ensure move.piece is an instance of Ram
            Ram ram = (Ram) move.piece;
            if ((ram.isRed && ram.row == rows - 1) || (!ram.isRed && ram.row == 0) || (!ram.isRed && ram.row == rows - 1) || (ram.isRed && ram.row == 0)) {
                ram.turnAround();  // Turn around if at the end
            }
        }       
    }

    public boolean isValidMove(Move move) {
        // Ensure the move stays within the bounds of the board
        if (move.newCol < 0 || move.newCol >= cols || move.newRow < 0 || move.newRow >= rows) {
            return false;
        }
    
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }
    
        // Ensure only the current player's pieces can move
        boolean isRedTurn = gameData.getCurrentTurn() % 2 == 0; // even: blue's turn, odd: red's turn
        if (move.piece.isRed != isRedTurn) {
            return false;
        }
    
        if (move.piece.MoveCollidesWithPiece(move.newCol, move.newRow)) {
            return false;
        }
    
        return move.piece.isValidMovement(move.newCol, move.newRow);
    }

    public boolean sameTeam(Piece p1, Piece p2) {
        return p1 != null && p2 != null && p1.isRed == p2.isRed;
    }
    
    public void checkTimerAndEndGame() {
        // Check if the window is still valid
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame == null || !topFrame.isShowing()) {
            if (gameTimer != null) {
                gameTimer.stop(); // Stop the timer if the window is closed
            }
            return; // Exit the method
        }
    
        if (gameData.getTimeRemaining() <= 0) {
            int redKills = gameData.getRedKills();
            int blueKills = gameData.getBlueKills();
    
            String winner;
            if (redKills > blueKills) {
                winner = "Red";
            } else if (blueKills > redKills) {
                winner = "Blue";
            } else {
                winner = "No one! It's a tie.";
            }
    
            // Show the popup and return to the main menu
            JOptionPane.showMessageDialog(
                this,
                "Time's up!\nWinner: " + winner + "\nRed Kills: " + redKills + "\nBlue Kills: " + blueKills,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
            );
            returnToMenu();
        }
    }

    public void showCheckmatePopup(String winningTeam) {
        int option = JOptionPane.showOptionDialog(
                this,
                winningTeam + " wins by checkmate!\nReturn to menu?",
                "Checkmate!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"OK"},
                "OK"
        );

        if (option == 0) { 
            returnToMenu();
        }
    }

    public void returnToMenu() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        // Stop the timer first to avoid any potential background processing
        if (gameTimer != null) {
            gameTimer.stop();
        }
    
        if (topFrame != null) {
            MainMenuView mainMenuView = new MainMenuView();
            MainMenuController menuController = new MainMenuController(mainMenuView);
            topFrame.dispose();
        }
    }
    
    public void replacePiece(Piece oldPiece, Piece newPiece) {
        // Remove the old piece from the board
        gameData.getPieceList().remove(oldPiece);
        
        // Add the new piece to the board
        gameData.addPiece(newPiece);
        
        // Update the new piece's position (this is important for tracking it on the board)
        newPiece.col = oldPiece.col;
        newPiece.row = oldPiece.row;
        }
        
    public GameData getGameData() {
        return this.gameData;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
    
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        tileSize = Math.min(panelWidth / cols, panelHeight / rows);
        xOffset = (panelWidth - (tileSize * cols)) / 2;
        yOffset = (panelHeight - (tileSize * rows)) / 2;
    
        // Determine if the board should be flipped
        boolean isRedTurn = gameData.getCurrentTurn() % 2 == 0; // even turns: Player 1 (Blue)
    
        if (isRedTurn) {
            g2d.translate(panelWidth, panelHeight); // Move the origin to the bottom-right corner
            g2d.rotate(Math.toRadians(180));       // Rotate 180 degrees
        }
    
        // Draw the board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                g2d.setColor((c + r) % 2 == 0 ? Color.DARK_GRAY : Color.WHITE);
                g2d.fillRect(xOffset + c * tileSize, yOffset + r * tileSize, tileSize, tileSize);
            }
        }
    
        // Highlight valid moves if a piece is selected
        if (selectedPiece != null) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Move move = new Move(this, selectedPiece, c, r);
                    if (isValidMove(move)) {
                        g2d.setColor(new Color(68, 180, 57, 190));
                        g2d.fillRect(xOffset + c * tileSize, yOffset + r * tileSize, tileSize, tileSize);
                    }
                }
            }
        }
        
        // Draw all pieces
        for (Piece piece : gameData.getPieceList()) {
            if (piece == selectedPiece && draggedX != -1 && draggedY != -1) {
                continue;
            }
            piece.paint(g2d);
        }
    
        // Draw the selected piece at the drag position if dragging
        if (selectedPiece != null && draggedX != -1 && draggedY != -1) {
            g2d.drawImage(selectedPiece.getSprite(), draggedX, draggedY, tileSize, tileSize, null);
        }
    
        // Reset the transformation if the board was flipped
        if (isRedTurn) {
            g2d.rotate(Math.toRadians(-180)); // Undo rotation
            g2d.translate(-panelWidth, -panelHeight); // Undo translation
        }
    }
}
