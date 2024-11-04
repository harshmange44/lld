package org.hrsh.chess.piece;

import org.hrsh.chess.Board;
import org.hrsh.chess.Cell;
import org.hrsh.chess.Color;
import org.hrsh.chess.Piece;

public class Pawn extends Piece {
    public Pawn(Cell cell, String name, Color color) {
        super(cell, name, color);
    }

    @Override
    public boolean canMove(Board board, Cell srcCell, Cell destCell) {
        return false;
    }
}
