package application;

import control.firstClickCommand;
import control.CellCommand;
import control.PressCellCommand;
import control.PutAFlagCommand;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Cell;
import model.FlagCell;
import model.MineCell;
import model.UnpulsatedCell;
import view.CellSet;
import view.GameBoardDisplay;

public class SwingGameBoardDisplay extends JFrame implements GameBoardDisplay {

    private CellSet set;
    private boolean firtClick = true;
    private CellPanel[][] panels;
    private final Map<String, CellCommand> commands = new HashMap();
    private final int rows, columns, mines;
    private int correctMines;

    public SwingGameBoardDisplay(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        this.deployCommands();
        this.deployUI();
    }

    @Override
    public CellSet getCellSet() {
        return set;
    }

    @Override
    public void setCellSet(CellSet cellSet) {
        this.set = cellSet;
    }

    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public int getMines() {
        return mines;
    }

    @Override
    public void execute() {
        this.setVisible(true);
    }

    @Override
    public void press(int row, int column) {
        Cell cell = set.getCell(row, column);
        if (cell != null) {
            panels[row][column].paintOf(cell.getClosedMines());
            if (cell instanceof MineCell) {
                lose();
            }
        }
    }

    @Override
    public void flag(int row, int column) {
        Cell cell = set.getCell(row, column);
        if (cell instanceof FlagCell) {
            panels[row][column].paintOf(11);
            if (cell.getClosedMines() == -1) {
                correctMines++;
                if (correctMines == mines) {
                    win();
                }
            }
        }
        if (cell instanceof UnpulsatedCell || cell instanceof MineCell) {
            panels[row][column].paintOf(10);
            if (cell.getClosedMines() == -1) {
                correctMines--;
            }
        }
    }

    private void deployCommands() {
        commands.put("press", new PressCellCommand(this));
        commands.put("flag", new PutAFlagCommand(this));
        commands.put("firstClick", new firstClickCommand(this));
    }

    private void deployUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(gameBoard(), BorderLayout.CENTER);
        this.setColocation();
    }

    private JPanel gameBoard() {
        JPanel panel = new JPanel(new GridLayout(rows, columns));
        fillPanel(panel);
        return panel;
    }

    private void setColocation() {
        this.setSize(new Dimension(columns * 33, rows * 36));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    private void fillPanel(JPanel panel) {
        Image[] images = loadImages();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                CellPanel temporalPanel = new CellPanel(images);
                temporalPanel.addMouseListener(mouseListener(i, j));
                panel.add(temporalPanel);
                panels[i][j] = temporalPanel;
            }
        }
    }

    private Image[] loadImages() {
        Image[] images = new Image[13];
        for (int i = 0; i < images.length; i++) {
            String path = "src\\img\\j" + i + ".gif";
            images[i] = new ImageIcon(path).getImage();
        }
        return images;
    }

    private void win() {
        int answer = JOptionPane.showConfirmDialog(this, "¡GANASTE!\n ¿Otra?", "Ganaste", JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            this.dispose();
            new Application();
        }
    }

    private void lose() {
        int answer = JOptionPane.showConfirmDialog(this, "Has perdido\n ¿Otra?", "Perdiste", JOptionPane.YES_NO_OPTION);
        if (answer == 0) {
            this.dispose();
            new Application();
        }
    }

    private MouseListener mouseListener(int row, int column) {
        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int buttonPressed = e.getButton();
                if (!firtClick) {
                    if (buttonPressed == MouseEvent.BUTTON1) {
                        commands.get("press").exectute(row, column);
                    } else if (buttonPressed == MouseEvent.BUTTON3) {
                        commands.get("flag").exectute(row, column);
                    }
                } else {
                    if (buttonPressed == MouseEvent.BUTTON1) {
                        commands.get("firstClick").exectute(row, column);
                        firtClick = false;
                        commands.get("press").exectute(row, column);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
    }
}
