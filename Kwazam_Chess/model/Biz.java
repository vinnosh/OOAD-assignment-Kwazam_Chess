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

public class Biz extends Piece {
    public Biz(Board board, int col, int row, boolean isRed) {
        super(board); // Call the Piece constructor

        this.col = col;
        this.row = row;
        this.isRed = isRed;
        this.name = "Biz";

        // Load the specific image for this Biz piece
        String filePath = isRed
                ? "Kwazam_Chess\\Image\\rbiz.png"
                : "Kwazam_Chess\\Image\\bbiz.png";

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
        // The Biz moves in an "L" shape (similar to a knight in chess)
        return Math.abs(col - this.col) * Math.abs(row - this.row) == 2;
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
