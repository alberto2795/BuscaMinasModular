package view;

public interface GameBoardDisplay{
    CellSet getCellSet();
    void execute();
    void press(int row, int column);
    void flag (int row, int column);
    int getRows();
    int getColumns();
    int getMines();

    public void setCellSet(CellSet cellSet);
}
