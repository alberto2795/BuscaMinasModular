package model;

public class FlagCell implements Cell{
    private final int closedMines;

    public FlagCell(int closedMines) {
        this.closedMines = closedMines;
    }
    
    @Override
    public int getClosedMines() {
        return closedMines;
    }
    
}
