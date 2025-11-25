package org.hrsh.chess;

import org.hrsh.chess.piece.*;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Cell[][] cells = new Cell[8][8];
    private final List<Move> logs;

    public Board() {
        this.logs = new ArrayList<>();
    }

    public void init() {
        cells[0][0] = new Cell(0, 0, new Rook(cells[0][0], "ROOK", Color.WHITE));
        cells[0][1] = new Cell(0, 1, new Knight(cells[0][1], "KNIGHT", Color.WHITE));
        cells[0][2] = new Cell(0, 2, new Bishop(cells[0][2], "BISHOP", Color.WHITE));
        cells[0][3] = new Cell(0, 3, new King(cells[0][3], "KING", Color.WHITE));
        cells[0][4] = new Cell(0, 4, new Queen(cells[0][4], "QUEEN", Color.WHITE));
        cells[0][5] = new Cell(0, 5, new Bishop(cells[0][5], "BISHOP", Color.WHITE));
        cells[0][6] = new Cell(0, 6, new Knight(cells[0][6], "KNIGHT", Color.WHITE));
        cells[0][7] = new Cell(0, 7, new Rook(cells[0][7], "ROOK", Color.WHITE));

        for (int col = 0; col < 8; col++) {
            cells[1][col] = new Cell(1, col, new Pawn(cells[1][col], "PAWN", Color.WHITE));
        }

        cells[7][0] = new Cell(7, 0, new Rook(cells[7][0], "ROOK", Color.BLACK));
        cells[7][1] = new Cell(7, 1, new Knight(cells[7][1], "KNIGHT", Color.BLACK));
        cells[7][2] = new Cell(7, 2, new Bishop(cells[7][2], "BISHOP", Color.BLACK));
        cells[7][3] = new Cell(7, 3, new King(cells[7][3], "KING", Color.BLACK));
        cells[7][4] = new Cell(7, 4, new Queen(cells[7][4], "QUEEN", Color.BLACK));
        cells[7][5] = new Cell(7, 5, new Bishop(cells[7][5], "BISHOP", Color.BLACK));
        cells[7][6] = new Cell(7, 6, new Knight(cells[7][6], "KNIGHT", Color.BLACK));
        cells[7][7] = new Cell(7, 7, new Rook(cells[7][7], "ROOK", Color.BLACK));

        for (int col = 0; col < 8; col++) {
            cells[6][col] = new Cell(6, col, new Pawn(cells[6][col], "PAWN", Color.BLACK));
        }

        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                cells[row][col] = new Cell(row, col, null);
            }
        }
    }

    public boolean playMove(Move move) {
        if (isValidMove(move)) {
            logs.add(new Move(move.getPiece(), move.getSrcCell(), move.getDestCell()));

            Piece piece = move.getPiece();
            Cell srcCell = move.getSrcCell();
            Cell destCell = move.getDestCell();

            destCell.setPiece(piece);
            srcCell.setPiece(null);
            return true;
        }

        return false;
    }

    private boolean isValidMove(Move move) {
        if (move == null || move.getPiece() == null || move.getSrcCell() == null || move.getDestCell() == null) {
            return false;
        }
        
        // Verify source cell has this piece
        if (move.getSrcCell().getPiece() != move.getPiece()) {
            return false;
        }

        if (move.getDestCell().getRow() < 0 || move.getDestCell().getCol() < 0 ||
                move.getDestCell().getRow() > 7 || move.getDestCell().getCol() > 7) return false;

        if (move.getDestCell().getPiece() != null &&
                move.getPiece().getColor() == move.getDestCell().getPiece().getColor()) return false;

        return move.getPiece().canMove(this, move.getSrcCell(), move.getDestCell());
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isCheckmate(Player player) {
        Color playerColor = player.getColor();
        
        // Checkmate = King is in check AND no legal moves available
        if (!isKingInCheck(playerColor)) {
            return false; // Not in check, so not checkmate
        }
        
        // King is in check, now check if any legal move exists
        return !hasAnyLegalMove(playerColor);
    }

    public boolean isStalemate(Player player) {
        Color playerColor = player.getColor();
        
        // Stalemate = King is NOT in check BUT no legal moves available
        if (isKingInCheck(playerColor)) {
            return false; // In check, so not stalemate (could be checkmate)
        }
        
        // King not in check, but check if any legal move exists
        return !hasAnyLegalMove(playerColor);
    }

    /**
     * Helper method to find the king of a given color
     */
    private Cell findKing(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Cell cell = cells[row][col];
                Piece piece = cell.getPiece();
                if (piece != null && 
                    piece.getName().equals("KING") && 
                    piece.getColor() == color) {
                    return cell;
                }
            }
        }
        return null; // Should never happen in valid game
    }

    /**
     * Check if the king of given color is under attack
     */
    private boolean isKingInCheck(Color color) {
        Cell kingCell = findKing(color);
        if (kingCell == null) return false;
        
        Color opponentColor = (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
        
        // Check if any opponent piece can attack the king's position
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Cell cell = cells[row][col];
                Piece piece = cell.getPiece();
                
                if (piece != null && piece.getColor() == opponentColor) {
                    // Check if this opponent piece can move to king's position
                    if (piece.canMove(this, cell, kingCell)) {
                        return true; // King is under attack
                    }
                }
            }
        }
        
        return false;
    }

    /**
     * Check if player has any legal move available
     * A legal move is one that doesn't leave own king in check
     */
    private boolean hasAnyLegalMove(Color color) {
        // Try all pieces of this color
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Cell srcCell = cells[row][col];
                Piece piece = srcCell.getPiece();
                
                if (piece != null && piece.getColor() == color) {
                    // Try all possible destination cells
                    for (int destRow = 0; destRow < 8; destRow++) {
                        for (int destCol = 0; destCol < 8; destCol++) {
                            Cell destCell = cells[destRow][destCol];
                            
                            // Check if this is a valid move
                            Move move = new Move(piece, srcCell, destCell);
                            if (isValidMove(move)) {
                                // Try the move and check if it leaves king in check
                                if (isMoveLegal(move, color)) {
                                    return true; // Found at least one legal move
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return false; // No legal moves found
    }

    /**
     * Check if a move is legal (doesn't leave own king in check)
     * This requires making the move temporarily, checking, then undoing it
     */
    private boolean isMoveLegal(Move move, Color playerColor) {
        // Save the state
        Piece destPiece = move.getDestCell().getPiece();
        Piece srcPiece = move.getSrcCell().getPiece();
        
        // Make the move temporarily
        move.getDestCell().setPiece(move.getPiece());
        move.getSrcCell().setPiece(null);
        
        // Check if this leaves the king in check
        boolean kingStillInCheck = isKingInCheck(playerColor);
        
        // Undo the move (restore state)
        move.getSrcCell().setPiece(srcPiece);
        move.getDestCell().setPiece(destPiece);
        
        // Move is legal if it doesn't leave king in check
        return !kingStillInCheck;
    }
}
