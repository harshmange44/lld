package org.hrsh.chess;

public class Move {
    private Piece piece;
    private Cell srcCell;
    private Cell destCell;

    public Move(Piece piece, Cell srcCell, Cell destCell) {
        this.piece = piece;
        this.srcCell = srcCell;
        this.destCell = destCell;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Cell getSrcCell() {
        return srcCell;
    }

    public void setSrcCell(Cell srcCell) {
        this.srcCell = srcCell;
    }

    public Cell getDestCell() {
        return destCell;
    }

    public void setDestCell(Cell destCell) {
        this.destCell = destCell;
    }
}
