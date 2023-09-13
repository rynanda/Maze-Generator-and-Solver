package drawmaze;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawCell extends JPanel {

    private BufferedImage img;
    private Graphics2D g2;
    private int frameLength;
    private int frameHeight;
    private int mazeLength;
    private int mazeHeight;
    private int x;
    private int y;
    private int cellSize;
    private boolean[] walls = {true, true, true, true};

    public DrawCell(int newFrameLength, int newFrameHeight, int newMazeLength, int newMazeHeight, int newCellSize) {
        frameLength = newFrameLength;
        frameHeight = newFrameHeight;
        mazeLength = newMazeLength;
        mazeHeight = newMazeHeight;
        cellSize = newCellSize;
        img = new BufferedImage(mazeLength, mazeHeight, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) img.getGraphics();
    }

    public void drawMazeBackground() {
        g2.setColor(Color.CYAN);
        g2.fillRect(0,0,mazeLength,mazeHeight);
    }

    public void drawMazeCell(int newCellRow, int newCellCol, boolean[] wallVisibility) {
        x = newCellRow * cellSize;
        y = newCellCol * cellSize;
        walls = wallVisibility;

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        if (walls[0]) { //Top wall
            g2.drawLine(x, y, (x + cellSize), y);
        }
        if (walls[1]) { //Right wall
            g2.drawLine((x + cellSize), y, (x + cellSize), (y + cellSize));
        }
        if (walls[2]) { //Bottom wall
            g2.drawLine((x + cellSize), (y + cellSize), x, (y + cellSize));
        }
        if (walls[3]) { //Left wall
            g2.drawLine(x, (y + cellSize), x, y);
        }
    }

    public void drawSolution(int fromCellRow, int fromCellCol, int toCellRow, int toCellCol, int numRows, int numCols) {
        int x1 = (fromCellRow * cellSize) + (cellSize /2); //+ (cellSize/2) so that in middle of cell.
        int y1 = (fromCellCol * cellSize) + (cellSize /2);
        int x2 = (toCellRow * cellSize) + (cellSize /2);
        int y2 = (toCellCol * cellSize) + (cellSize /2);

        g2.setColor(Color.RED);
        g2.setStroke(new BasicStroke(5));
        g2.drawLine(x1, y1, x2, y2);
        if (toCellRow == 0 && toCellCol == 0) {
            g2.drawLine(x2, y2, x2, 0);
        }
        else if (fromCellRow == numRows - 1 && fromCellCol == numCols - 1) {
            g2.drawLine(x1, y1, x1, numCols * cellSize);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(frameLength, frameHeight);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 15, 15, null);
    }
}