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

public class Xor extends Piece {
    public Xor(Board board, int col, int row, boolean isRed) {
        super(board);

        this.col = col;
        this.row = row;
        this.isRed = isRed;
        this.name = "Xor";

        // Load the specific image for this Xor piece
        String filePath = isRed
                ? "Kwazam_Chess\\Image\\rxor.png"
                : "Kwazam_Chess\\Image\\bxor.png";

        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            this.sprite = image; // Store raw image for dynamic scaling
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading image: " + filePath);
        }
    }

    @Override
    public boolean isValidMovement(int col, int row) {
        // Valid if moving only diagonally
        return Math.abs(this.col - col) == Math.abs(this.row - row);
    }

    @Override
    public boolean MoveCollidesWithPiece(int col, int row) {
        // Diagonal movement, check for pieces in the path
        int xDirection = Integer.compare(col, this.col);
        int yDirection = Integer.compare(row, this.row);
        int x = this.col + xDirection;
        int y = this.row + yDirection;

        while (x != col && y != row) {
            if (board.getPiece(x, y) != null) {
                return true; // Path is obstructed
            }
            x += xDirection;
            y += yDirection;
        }

        return false; // No pieces in the way
    }

    public void transformToTor() {
        // Transform this Xor piece into a Tor piece
        Piece tor = new Tor(board, col, row, isRed);
        board.replacePiece(this, tor); // Replace Xor with Tor
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
