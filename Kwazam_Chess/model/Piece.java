package Kwazam_Chess.model;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method

import java.awt.Image;
import java.awt.Graphics2D;

public abstract class Piece {
    protected int col, row; // Position of the piece on the board
    protected int xPos, yPos; // Pixel position for dragging
    protected boolean isRed; // Team color
    protected Board board; // Reference to the board
    protected String name; // Name of the piece
    protected Image sprite; // Visual representation of the piece
    protected int turns = 0; // Number of turns this piece has taken

    public Piece(Board board) {
        this.board = board;
        this.xPos = 0; // Default initialization
        this.yPos = 0; // Default initialization
        updatePixelPosition();
    }
    
    public void updatePixelPosition() {
        this.xPos = col * board.tileSize;
        this.yPos = row * board.tileSize;
    }
    
    public void paint(Graphics2D g2d) {
        if (sprite != null) {
            int renderXPos = board.xOffset + xPos;
            int renderYPos = board.yOffset + yPos;
            g2d.drawImage(sprite, renderXPos, renderYPos, board.tileSize, board.tileSize, null);
        }
    }

    public abstract boolean isValidMovement(int col, int row);

    public boolean MoveCollidesWithPiece(int col, int row) {
        return false; // Default implementation, can be overridden in subclasses
    }

    public boolean sameTeam(Piece otherPiece) {
        return otherPiece != null && this.isRed == otherPiece.isRed;
    }

    public Image getSprite() {
        return sprite;
    }
    
    public int getTurns() {
        return turns;
    }

    public void resetTurns() {
        turns = 0;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    // Getter for xPos
    public int getXPos() {
        return xPos;
    }

    // Getter for yPos
    public int getYPos() {
        return yPos;
    }

    // Setter for xPos
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    // Setter for yPos
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
}
