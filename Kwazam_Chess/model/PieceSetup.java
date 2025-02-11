package Kwazam_Chess.model;

//-----Project Information-----
// Project Title: Kwazam_Chess
// Design Pattern: MVC / Factory Method


import java.util.List;
import java.util.ArrayList;

public class PieceSetup {
    // Load default setup using predefined piece configuration
    public static List<Piece> getDefaultSetup(Board board, GameData gameData) {
        return loadPiecesFromData(board, gameData.getPieceData());
    }

    // Create pieces from save file or custom setup
    public static List<Piece> getCustomSetup(Board board, List<Piece> savedPieces) {
        return new ArrayList<>(savedPieces);
    }

    // Helper method to dynamically load pieces from configuration
    public static List<Piece> loadPiecesFromData(Board board, String[] pieceData) {
        List<Piece> pieces = new ArrayList<>();
    
        for (String data : pieceData) {
            if (data.trim().isEmpty()) {
                // Skip empty lines
                continue;
            }
        
            String[] parts = data.split("\\s+"); // Split by whitespace
        
            // Check if data is valid
            if (parts.length < 4) {
                System.err.println("Invalid piece data: " + data);
                continue; // Skip invalid entries
            }
        
            String type = parts[0];               // Piece type
            int col = Integer.parseInt(parts[1]); // Column
            int row = Integer.parseInt(parts[2]); // Row
            boolean isRed = Boolean.parseBoolean(parts[3]); // Team
        
            // Default movingForward value for non-Ram pieces
            boolean movingForward = true;
        
            if (type.equals("Ram") && parts.length == 5) {
                movingForward = Boolean.parseBoolean(parts[4]);
            }
        
            // Add piece based on its type
            switch (type) {
                case "Ram":
                    Ram ram = new Ram(board, col, row, isRed);
                    ram.setMovingForward(movingForward); // Restore direction state
                    pieces.add(ram);
                    break;
                case "Xor":
                    pieces.add(new Xor(board, col, row, isRed));
                    break;
                case "Biz":
                    pieces.add(new Biz(board, col, row, isRed));
                    break;
                case "Tor":
                    pieces.add(new Tor(board, col, row, isRed));
                    break;
                case "Sau":
                    pieces.add(new Sau(board, col, row, isRed));
                    break;
                default:
                    System.err.println("Unknown piece type: " + type);
            }
        }
        return pieces;
    }
}