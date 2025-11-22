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
        return false;
    }

    public boolean isStalemate(Player player) {
        return false;
    }
}
