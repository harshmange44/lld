package org.hrsh.chess;

public class Cell {
    private int row;
    private int col;
    private String label;
    private Piece piece;

    public Cell(int row, int col, Piece piece) {
        this.row = row;
        this.col = col;
        this.piece = piece;

        assignLabel();
    }

    private void assignLabel() {
        char[] num = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

        setLabel(String.valueOf(chars[row]) + num[col]);
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
