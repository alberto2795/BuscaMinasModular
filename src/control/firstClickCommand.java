package control;

import model.Cell;
import model.MineCell;
import model.UnpulsatedCell;
import view.CellSet;
import view.GameBoardDisplay;

public class firstClickCommand implements CellCommand {
    private final GameBoardDisplay display;
    private final Cell[][] cells;
    private int rowPulsated, columnPulsated;

    public firstClickCommand(GameBoardDisplay display) {
        this.display = display;
        cells = new Cell[display.getRows()][display.getColumns()];
    }

    @Override
    public void exectute(int row, int column) {
        this.rowPulsated = row;
        this.columnPulsated = column;
        putMines();
        fill();
        display.setCellSet (new CellSet(cells));
    }
    
    private void putMines() {
        int rows = display.getRows();
        int column = display.getColumns();
        int minesLeft = display.getMines();
        while (minesLeft>0){
            int rowIndex = (int) (Math.random()*rows);
            int columnIndex = (int) (Math.random()*column);
            if(!(cells[rowIndex][columnIndex] instanceof MineCell || (rowIndex==rowPulsated && columnIndex==columnPulsated))){
                cells[rowIndex][columnIndex] = new MineCell();
                minesLeft--;
            }
        }
    }
    
    private void fill() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if(!(cells[i][j] instanceof MineCell))
                cells[i][j] = new UnpulsatedCell(numberOfNearMines(i, j));
            }
            
        }
    }

    private int numberOfNearMines(int row, int column) {
        int mines = 0;
            for (int i = row-1; i <= row+1; i++) {
                for (int j = column-1; j <= column+1; j++) {
                    if(!(i==row && j==column) && checkIfIsMine(i, j)) mines++;
                }
        }
        return mines;
    }

    private boolean checkIfIsMine(int row, int column) {
        return (row>=0 && row<cells.length && column>=0 && column<cells[row].length && cells[row][column] instanceof MineCell);
    }
}
