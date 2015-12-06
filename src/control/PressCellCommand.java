package control;

import model.Cell;
import model.FlagCell;
import model.MineCell;
import model.PulsatedCell;
import model.UnpulsatedCell;
import view.CellSet;
import view.GameBoardDisplay;

public class PressCellCommand implements CellCommand {

    private final GameBoardDisplay display;

    public PressCellCommand(GameBoardDisplay display) {
        this.display = display;
    }

    @Override
    public void exectute(int row, int column) {
        CellSet set = display.getCellSet();
        Cell oldCell = set.getCell(row, column);
        int closedMines = oldCell.getClosedMines();
        if (oldCell instanceof FlagCell || oldCell instanceof UnpulsatedCell) {
            set.put(row, column, new PulsatedCell(closedMines));
            if(closedMines==0) extendPulsation(row, column);
        }
        display.press(row, column);
    }

    private void extendPulsation(int row, int column) {
        CellSet set = display.getCellSet();
        for (int i = row-1; i <= row+1; i++) {
            for (int j = column-1; j <= column+1; j++) {
                if(i==row && j==column) continue;
                if(set.getCell(i, j) != null) exectute(i, j);
            }
            
        }
    }

}
