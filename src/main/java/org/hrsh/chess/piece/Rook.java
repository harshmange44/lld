package org.hrsh.chess.piece;

import org.hrsh.chess.Board;
import org.hrsh.chess.Cell;
import org.hrsh.chess.Color;
import org.hrsh.chess.Piece;

public class Rook extends Piece {
    public Rook(Cell cell, String name, Color color) {
        super(cell, name, color);
    }

    @Override
    public boolean canMove(Board board, Cell srcCell, Cell destCell) {
//        01 06
//        01
//        51

        // validating if moving vertically or horizontally
        if (srcCell.getRow() != destCell.getRow() || srcCell.getCol() != destCell.getCol()) return false;

        if (isMovingCols(srcCell, destCell)) {
            // validating if any blocker piece on the horizontal path
            for (int i = srcCell.getCol(); i < destCell.getCol(); i++) {
                if (board.getCells()[srcCell.getRow()][i].getPiece() != null) return false;
            }

            // validating if piece on the destination cell has same color of current player color
            if (board.getCells()[srcCell.getRow()][destCell.getCol()].getPiece() != null &&
                    board.getCells()[srcCell.getRow()][destCell.getCol()].getPiece().getColor() == getColor()) {
                return false;
            }
        } else if (isMovingRows(srcCell, destCell)) {
            // validating if any blocker piece on the vertical path
            for (int i = srcCell.getRow(); i < destCell.getRow(); i++) {
                if (board.getCells()[i][srcCell.getCol()].getPiece() != null) return false;
            }

            // validating if piece on the destination cell has same color of current player color
            if (board.getCells()[destCell.getRow()][srcCell.getCol()].getPiece() != null &&
                    board.getCells()[destCell.getRow()][srcCell.getCol()].getPiece().getColor() == getColor()) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    // check if path is horizontal
    private boolean isMovingCols(Cell srcCell, Cell destCell) {
        return srcCell.getRow() == destCell.getRow() && srcCell.getCol() != destCell.getCol();
    }

    // check if path is vertical
    private boolean isMovingRows(Cell srcCell, Cell destCell) {
        return srcCell.getCol() == destCell.getCol() && srcCell.getRow() == destCell.getRow();
    }
}
