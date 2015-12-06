package model;

public class UnpulsatedCell implements Cell{
    private final int closedMines;

    public UnpulsatedCell(int closedMines) {
        this.closedMines = closedMines;
    }
    
    @Override
    public int getClosedMines() {
        return closedMines;
    }
    
}
