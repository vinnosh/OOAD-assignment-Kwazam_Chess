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

public class Tor extends Piece {
    public Tor(Board board, int col, int row, boolean isRed) {
        super(board);

        this.col = col;
        this.row = row;
        this.isRed = isRed;
        this.name = "Tor";

        // Load the specific image for this Tor piece
        String filePath = isRed
                ? "Kwazam_Chess\\Image\\rtor.png"
                : "Kwazam_Chess\\Image\\btor.png";

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
        // Tor can move any number of squares, but only orthogonally (like a rook in chess)
        return this.col == col || this.row == row;
    }

    @Override
    public boolean MoveCollidesWithPiece(int col, int row) {
        // Check for obstacles in the path
        if (this.col == col) {
            // Vertical movement
            int direction = Integer.compare(row, this.row);
            for (int r = this.row + direction; r != row; r += direction) {
                if (board.getPiece(this.col, r) != null) {
                    return true; // Path is obstructed
                }
            }
        } else if (this.row == row) {
            // Horizontal movement
            int direction = Integer.compare(col, this.col);
            for (int c = this.col + direction; c != col; c += direction) {
                if (board.getPiece(c, this.row) != null) {
                    return true; // Path is obstructed
                }
            }
        }
        return false; // No pieces in the way
    }

    public void transformToXor() {
        // Replace Tor with Xor
        Piece xor = new Xor(board, col, row, isRed);
        board.replacePiece(this, xor); // Replace Tor with Xor
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
