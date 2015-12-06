package model;

public class PulsatedCell implements Cell{
    private final int closedMines;

    public PulsatedCell(int closedMines) {
        this.closedMines = closedMines;
    }
    
    @Override
    public int getClosedMines() {
        return closedMines;
    }
    
}
