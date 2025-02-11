package Kwazam_Chess.model;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Ram extends Piece {
    private boolean movingForward = true; // Track direction: true = forward, false = backward

    public Ram(Board board, int col, int row, boolean isRed) {
        super(board);

        this.col = col;
        this.row = row;
        this.isRed = isRed;
        this.name = "Ram";

        // Load the image for the Ram piece
        String filePath = isRed
                ? "Kwazam_Chess\\Image\\rram.png"
                : "Kwazam_Chess\\Image\\bram.png";

        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            this.sprite = image; // Store the raw image, no need to scale it here
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading image: " + filePath);
        }
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        // Determine movement direction based on movingForward
        int direction = movingForward ? 1 : -1;

        if (isRed) {
            // Red Ram moves forward or captures diagonally
            if (this.row + direction == row) {
                // Regular forward move
                if (this.col == col && board.getPiece(col, row) == null) {
                    return true;
                }
                // Diagonal capture
                if (Math.abs(this.col - col) == 1) {
                    Piece targetPiece = board.getPiece(col, row);
                    return targetPiece != null && !sameTeam(targetPiece);
                }
            }
        } else {
            // Blue Ram moves forward or captures diagonally
            if (this.row - direction == row) {
                // Regular forward move
                if (this.col == col && board.getPiece(col, row) == null) {
                    return true;
                }
                // Diagonal capture
                if (Math.abs(this.col - col) == 1) {
                    Piece targetPiece = board.getPiece(col, row);
                    return targetPiece != null && !sameTeam(targetPiece);
                }
            }
        }
        return false; // Default: invalid move
    }
    
    public boolean isMovingForward() {
        return movingForward;
    }
    
    public void setMovingForward(boolean movingForward) {
        this.movingForward = movingForward;
    }
    
    public void turnAround() {
        // Change movement direction when triggered
        movingForward = !movingForward;
    }

    @Override
    public boolean MoveCollidesWithPiece(int col, int row) {
        // Allow diagonal captures
        if (Math.abs(this.col - col) == 1 && Math.abs(this.row - row) == 1) {
            Piece targetPiece = board.getPiece(col, row);
            return targetPiece != null && sameTeam(targetPiece);
        }

        // Block regular moves if the path is obstructed
        return board.getPiece(col, row) != null;
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (sprite != null) {
            // Dynamically scale the image based on the current board's tile size
            Image scaledImage = sprite.getScaledInstance(board.tileSize, board.tileSize, Image.SCALE_SMOOTH);

            int dynamicXPos = board.xOffset + col * board.tileSize;
            int dynamicYPos = board.yOffset + row * board.tileSize;

            g2d.drawImage(scaledImage, dynamicXPos, dynamicYPos, null);
        }
    }
}