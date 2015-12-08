package control;

import model.Cell;
import model.FlagCell;
import model.MineCell;
import model.PulsatedCell;
import model.UnpulsatedCell;
import view.CellSet;
import view.GameBoardDisplay;

public class PutAFlagCommand implements CellCommand {

    private final GameBoardDisplay display;
    
    public PutAFlagCommand(GameBoardDisplay display) {
        this.display = display;
    }
    
    @Override
    public void exectute(int row, int column) {
        CellSet set = display.getCellSet();
        Cell oldCell = set.getCell(row, column);
        if(oldCell != null){
            int closedMines = oldCell.getClosedMines();
            if (oldCell instanceof UnpulsatedCell || oldCell instanceof MineCell) {
                set.put(row, column, new FlagCell(closedMines));
            } else if (oldCell instanceof FlagCell) {
                if (closedMines == -1) set.put(row, column, new MineCell());
                else set.put(row, column, new UnpulsatedCell(closedMines));
            }
            display.flag(row, column);
        }
    }
    
}
