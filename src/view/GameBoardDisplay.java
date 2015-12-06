package view;

public interface GameBoardDisplay{
    CellSet getCellSet();
    void execute();
    void press(int row, int column);
    void flag (int row, int column);
}
