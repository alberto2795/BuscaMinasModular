package view;

import model.Cell;

public class CellSet {

    private final Cell[][] cells;

    public CellSet(Cell[][] cells) {
        this.cells = cells;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void put(int row, int column, Cell cell) {
        cells[row][column] = cell;
    }

    public Cell getCell(int row, int column) {
        if (row < cells.length && row >= 0 && column < cells[row].length && column >= 0) {
            return cells[row][column];
        }
        return null;
    }
    
    public int getRows(){
        return cells.length;
    }
    
    public int getColums(){
        return cells[0].length;
    }
}
