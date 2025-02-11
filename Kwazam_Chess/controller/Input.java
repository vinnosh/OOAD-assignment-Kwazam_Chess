package Kwazam_Chess.controller;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import Kwazam_Chess.model.Board;
import Kwazam_Chess.model.Piece;
import Kwazam_Chess.model.Move;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class Input extends MouseAdapter {
    private Board board;

    public Input(Board board) {
        this.board = board;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Calculate column and row based on mouse position, adjusted for rotation
        int col = calculateColumn(e.getX());
        int row = calculateRow(e.getY());

        Piece pieceXY = board.getPiece(col, row);
        if (pieceXY != null) {
            board.selectedPiece = pieceXY;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (board.selectedPiece != null) {
            // Adjust dragging position based on whether the board is rotated
            if (isBoardRotated()) {
                board.draggedX = board.getWidth() - e.getX() - board.tileSize / 2;
                board.draggedY = board.getHeight() - e.getY() - board.tileSize / 2;
            } else {
                board.draggedX = e.getX() - board.tileSize / 2;
                board.draggedY = e.getY() - board.tileSize / 2;
            }
            board.repaint(); // Repaint to reflect dragging updates
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (board.selectedPiece != null) {
            // Calculate column and row based on the dropped position, adjusted for rotation
            int col = calculateColumn(e.getX());
            int row = calculateRow(e.getY());
    
            // Ensure the column and row are within valid bounds
            if (col >= 0 && col < board.cols && row >= 0 && row < board.rows) {
                Move move = new Move(board, board.selectedPiece, col, row);
    
                if (board.isValidMove(move)) {
                    board.makeMove(move); // Make the move if valid
                } else {
                    // Reset piece's position to the original grid location
                    board.selectedPiece.updatePixelPosition();
                }
            } else {
                // Reset piece's position if dropped outside the board
                board.selectedPiece.updatePixelPosition();
            }
        }
    
        // Clear the selected piece and reset drag variables
        board.selectedPiece = null;
        board.draggedX = -1;
        board.draggedY = -1;
        board.repaint();
    }

    // Calculate column, adjusted for rotation
    private int calculateColumn(int mouseX) {
        int col = (mouseX - board.xOffset) / board.tileSize;
        if (isBoardRotated()) {
            col = board.cols - 1 - col; // Reverse column for rotation
        }
        return col;
    }

    // Calculate row, adjusted for rotation
    private int calculateRow(int mouseY) {
        int row = (mouseY - board.yOffset) / board.tileSize;
        if (isBoardRotated()) {
            row = board.rows - 1 - row; // Reverse row for rotation
        }
        return row;
    }

    // Check if the board is currently rotated
    private boolean isBoardRotated() {
        // Use the GameData instance from the board
        return board.getGameData().getCurrentTurn() % 2 == 0; // even turns: rotated (Player 2(red)'s perspective)
    }
}
