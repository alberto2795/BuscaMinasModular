package application;

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

public class SwingGameBoardDisplay extends JFrame implements GameBoardDisplay{
    private final CellSet set;
    private MinePanel[][] panels;
    private Map<String, CellCommand> commands = new HashMap();


    public SwingGameBoardDisplay(CellSet set) {
        this.set = set;
        this.deployCommands();
        this.deployUI();
    }

    @Override
    public CellSet getCellSet() {
        return set;
    }

    @Override
    public void execute() {
        this.setVisible(true);
    }

    @Override
    public void press(int row, int column) {
        Cell cell = set.getCell(row, column);
        if(cell != null){
            panels[row][column].paintOf(cell.getClosedMines());
            if(cell instanceof MineCell) lose();
        }
    }

    @Override
    public void flag(int row, int column) {
        Cell cell = set.getCell(row, column);
        if(cell instanceof FlagCell) panels[row][column].paintOf(11);
        if(cell instanceof UnpulsatedCell || cell instanceof MineCell) panels[row][column].paintOf(10);
    }
    
    private void lose() {
        int answer = JOptionPane.showConfirmDialog(this,"Has perdido\n ¿Otra?", "Perdiste", JOptionPane.YES_NO_OPTION);
        if(answer==0){
            this.dispose();
            new Application();
        }
    }

    private void deployCommands() {
        commands.put("press", new PressCellCommand(this));
        commands.put("flag", new PutAFlagCommand(this));
    }
    
    private void deployUI() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(gameBoard(), BorderLayout.CENTER);
        this.setColocation();
    }

    private JPanel gameBoard() {
        JPanel panel = new JPanel(new GridLayout(set.getRows(), set.getColums()));
        fillPanel(panel);
        return panel;
    }

    private void fillPanel(JPanel panel) {
        Image[] images = loadImages();
        panels = new MinePanel[set.getRows()][set.getColums()];
        
        Cell[][] cells = set.getCells();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                MinePanel temporalPanel = new MinePanel(images);
                temporalPanel.addMouseListener(mouseListener(i,j));
                panel.add(temporalPanel);
                panels[i][j]=temporalPanel;
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

    private void setColocation() {
        this.setSize(new Dimension(set.getColums()*33, set.getRows()*36));
        this.setResizable(false);
        this.setLocationRelativeTo(null);
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
                if(buttonPressed==MouseEvent.BUTTON1){
                    commands.get("press").exectute(row, column);
                }else if(buttonPressed==MouseEvent.BUTTON3){
                    commands.get("flag").exectute(row, column);
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
