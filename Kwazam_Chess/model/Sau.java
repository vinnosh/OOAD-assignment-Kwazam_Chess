package Kwazam_Chess.model;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sau extends Piece {
    public Sau(Board board, int col, int row, boolean isRed) {
        super(board);

        this.col = col;
        this.row = row;
        this.isRed = isRed;
        this.name = "Sau";

        try {
            // Load the specific image for this Sau piece
            String filePath = isRed
                    ? "Kwazam_Chess\\Image\\rsau.png"
                    : "Kwazam_Chess\\Image\\bsau.png";
            BufferedImage image = ImageIO.read(new File(filePath));
            this.sprite = image; // Store raw image for dynamic scaling
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading Sau piece image.");
        }
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        // The King can move one square in any direction (vertically, horizontally, or diagonally)
        return Math.abs(this.col - col) <= 1 && Math.abs(this.row - row) <= 1;
    }

    @Override
    public boolean MoveCollidesWithPiece(int col, int row) {
        // The Sau cannot move to a square occupied by a piece of the same team
        Piece targetPiece = board.getPiece(col, row);
        return targetPiece != null && sameTeam(targetPiece);
    }

    public boolean canCapture(int col, int row) {
        // Sau captures similarly to its movement (one square away in any direction)
        Piece targetPiece = board.getPiece(col, row);
        return Math.abs(this.col - col) <= 1 && Math.abs(this.row - row) <= 1 &&
                targetPiece != null && !sameTeam(targetPiece);
    }

    @Override
    public void paint(Graphics2D g2d) {
        if (sprite != null) {
            // Dynamically scale the sprite based on the board's tile size
            Image scaledImage = sprite.getScaledInstance(board.tileSize, board.tileSize, Image.SCALE_SMOOTH);

            int dynamicXPos = board.xOffset + col * board.tileSize;
            int dynamicYPos = board.yOffset + row * board.tileSize;

            g2d.drawImage(scaledImage, dynamicXPos, dynamicYPos, null);
        }
    }
}
