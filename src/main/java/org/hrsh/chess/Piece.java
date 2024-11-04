package org.hrsh.chess;

public abstract class Piece {
    private Cell cell;
    private String name;
    private Color color;

    public Piece(Cell cell, String name, Color color) {
        this.cell = cell;
        this.name = name;
        this.color = color;
    }

    public abstract boolean canMove(Board board, Cell srcCell, Cell destCell);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
