package application;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class CellPanel extends JPanel {

    private final Image[] images;
    private int numberOfImg;
    
    public CellPanel(Image[] img) {
        this.images = img;
        numberOfImg = 10;
    }
    
    public void paintOf(int number){
        numberOfImg = number;
        if (number == -1) numberOfImg=9;
        this.repaint();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, 30, 30);
        g.drawImage(images[numberOfImg], 0, 0, null);
    }
}
