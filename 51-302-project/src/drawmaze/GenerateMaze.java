package drawmaze;

import database.MazeDB;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Code adapted from and inspired by YouTube creator, Codamy:
 * Maze generation: https://www.youtube.com/watch?v=zsG2ceOlY6I&t=3s&ab_channel=Codamy
 * Maze solve: https://www.youtube.com/watch?v=_KEzW50Nhyw&ab_channel=Codamy
 */
public class GenerateMaze implements ActionListener {

    //Setup & maze generation fields
    private int frameLength;
    private int frameHeight;
    private int mazeLength = 800;
    private int mazeHeight = 800;
    private int numRows;
    private int numCols;
    private boolean isChildMaze;
    private JFrame frame;
    private DrawCell panel;
    private JLabel childStartLbl;
    private JLabel childEndLbl;
    private JLabel exploredPercent;
    private JLabel deadEndPercent;
    private int cellSize; //default size for a standard maze cell which will only change if a child maze
    private Cell[][] cells;
    private Cell currentCell;
    private ArrayList<Cell> stack = new ArrayList<>();
    private ArrayList<Cell> generationPath = new ArrayList<>();
    //Maze solve fields
    private JButton solveButton;
    private JButton downloadButton;
    private JButton saveButton;
    private Cell startSearchCell;
    private Cell currentSearchCell;
    private Cell endSearchCell;
    private ArrayList<Cell> searchPath = new ArrayList<>();
    private ArrayList<Cell> openSet = new ArrayList<>();
    private boolean pathFound = false;

    /**NEED TO UPDATE*******
     * Constructor that sets the Swing frame with reference to the maze length and height that was determined
     * via the two parameters. The constructor also finds the number of rows and columns in the maze to be used
     * in other code**** (i.e. the maze length and height in terms of a number of cells), instantiates a 2D
     * array of maze cells, which _____, and importantly, it sets up the maze GUI.
     * @param inputtedRowNum the number of rows for the maze the user has selected.
     * @param inputtedColNum the number of columns for the maze the user has selected.
     */
    public GenerateMaze(int inputtedRowNum, int inputtedColNum, boolean mazeType) throws IOException {
        numRows = inputtedRowNum;
        numCols = inputtedColNum;

        //This is done so that the window scaling is with regard to the number of rows. This allows the panel
        //background to be updated with regard to the new window height.
        if (numRows > numCols) {
            cellSize = mazeLength / numRows;
        }
        //Else if the number of columns is greater than the number of rows, or they are equal, the window will scale
        //according to the number of columns. The window must be resized vertically so that all of the columns fit
        //in the panel.
        else {
            cellSize = mazeLength / numCols; //mazeLength and mazeHeight have the same value, so doesn't matter which is used.
        }
        mazeHeight = numCols * cellSize;
        mazeLength = numRows * cellSize;

        frameLength = mazeLength + 300; //+ 300 to allow room for buttons
        frameHeight = mazeHeight + 30; //+ 30 because maze starts at (15,15)
        isChildMaze = mazeType;
        cells = new Cell[numRows][numCols];
        setUpGUI();
    }

    private void setUpGUI() throws IOException {
        frame = new JFrame("Maze Generator");
        panel = new DrawCell(frameLength, frameHeight, mazeLength, mazeHeight, cellSize);
        panel.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        panel.drawMazeBackground();
        setUpButton();
        saveButton();
        downloadButton();
        panel.add(solveButton);
        panel.add(downloadButton);
        panel.add(saveButton);
        identifyCells();
        identifyNeighbours();
        currentCell = cells[0][0];
        currentCell.setVisitStatus(true);
        startSearchCell = cells[0][0];
        endSearchCell = cells[numRows - 1][numCols - 1]; //Bottom right cell
        definePath();
        if (isChildMaze) {
            setUpChildPics();
            panel.add(childStartLbl);
            panel.add(childEndLbl);
        }
        drawMaze();
        setUpDeadEndPercent();
        panel.add(deadEndPercent);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
    private void downloadButton() {
        downloadButton = new JButton();
        downloadButton.setLayout(null);
        downloadButton.setBounds(mazeLength + 100, 135, 150, 50);
        downloadButton.setText("Download Maze");
        downloadButton.setFocusable(false);
        downloadButton.setFont(new Font("DM Sans",Font.BOLD,12));
        downloadButton.setForeground(Color.WHITE);
        downloadButton.setBackground(Color.RED);
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color frameBackgroundColor = frame.getBackground();

                // Maze image export code references
                // https://stackoverflow.com/questions/4725320/how-to-save-window-contents-as-an-image.
                try
                {
                    String path = JOptionPane.showInputDialog(frame, "Directory of where you want to export the maze:");
                    String name = JOptionPane.showInputDialog(frame, "Name of the file:",
                            "Insert title of the maze here");
                    BufferedImage image = new BufferedImage(mazeLength + 47, mazeHeight + 77, BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics2D = image.createGraphics();
                    graphics2D.setPaint(frameBackgroundColor);
                    graphics2D.fillRect(0,0,1000, 1000);
                    frame.paint(graphics2D);
                    ImageIO.write(image,"jpeg", new File(path + "/" + name + ".jpeg"));
                    JOptionPane.showMessageDialog(frame, "This maze has been successfully exported.");
                } catch(Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Please ensure that the correct directory is linked. " +
                                    "A sample directory is: C:\\Users\\User\\Documents.\n\n" +
                                    "Also ensure that the file name has no illegal characters: \\ / : * \" < > | ",
                            "Error Occurred", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        }

    private void saveButton() {
        saveButton = new JButton();
        saveButton.setLayout(null);
        saveButton.setBounds(mazeLength + 100, 75, 100, 50);
        saveButton.setText("Save Maze");
        saveButton.setFocusable(false);
        saveButton.setFont(new Font("DM Sans", Font.BOLD, 12));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBackground(Color.RED);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeDB.main(null);
            }
        });
    }

    private double numberOfDeadEnds() {
        double numberOfCells = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                int numberOfDrawnWalls = 0;
                for (int k = 0; k < cells[i][j].getWalls().length; k++) {
                    if (cells[i][j].getWalls()[k] == true) {
                        numberOfDrawnWalls++;
                    }
                }
                if (numberOfDrawnWalls == 3) {
                    numberOfCells++;
                }
            }
        }
        return numberOfCells;
    }

    private String calculateDeadEndPercent() {
        double percentageOfDeadEnds = (numberOfDeadEnds()/(numRows * numCols)) * 100;
        int intPercentage = (int)percentageOfDeadEnds;
        String text = String.valueOf(intPercentage);
        return text;
    }

    private void setUpDeadEndPercent() {
        deadEndPercent = new JLabel();
        deadEndPercent.setText("% of dead ends: " + calculateDeadEndPercent() + "%");
        deadEndPercent.setLayout(null);
        Dimension size = deadEndPercent.getPreferredSize();
        deadEndPercent.setBounds(mazeLength + 20, mazeHeight - 60, size.width, size.height);
    }

    private String calculateExploredPercent() {
        double numOfCellsSearched = searchPath.size();
        double percentageOfSearched = (numOfCellsSearched/(numRows * numCols)) * 100;
        int intPercentage = (int)percentageOfSearched;
        String text = String.valueOf(intPercentage);
        return text;
    }

    private void setUpExploredPercent() {
        exploredPercent = new JLabel();
        exploredPercent.setText("% of cells explored in solution: " + calculateExploredPercent() + "%");
        exploredPercent.setLayout(null);
        Dimension size = exploredPercent.getPreferredSize();
        exploredPercent.setBounds(mazeLength + 20, mazeHeight - 20, size.width, size.height);
    }

    private void setUpButton() {
        solveButton = new JButton();
        solveButton.setLayout(null);
        solveButton.setBounds(mazeLength + 100, 15, 100, 50);
        solveButton.setText("Solve Maze");
        solveButton.setFocusable(false);
        solveButton.setFont(new Font("DM Sans",Font.BOLD,12));
        solveButton.setForeground(Color.WHITE);
        solveButton.setBackground(Color.RED);
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identifyMazePathNeighbours();
                solveMaze();
                panel.repaint();
                setUpExploredPercent();
                panel.add(exploredPercent);
            }
        });
    }

    private void setUpChildPics() throws IOException {
        //Start image for child maze
        BufferedImage childStartImg = ImageIO.read(GenerateMaze.class.getResource("/resources/images/dog.png"));
        Image resizedChildStartImg = childStartImg.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
        childStartLbl = new JLabel(new ImageIcon(resizedChildStartImg));
        childStartLbl.setLayout(null);
        childStartLbl.setBounds(15, 15, cellSize, cellSize);
        //End image for child maze
        BufferedImage childEndImg = ImageIO.read(GenerateMaze.class.getResource("/resources/images/bone.png"));
        Image resizedChildEndImg = childEndImg.getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
        childEndLbl = new JLabel(new ImageIcon(resizedChildEndImg));
        childEndLbl.setLayout(null);
        childEndLbl.setBounds((cells[numRows - 1][numCols - 1].getRowNum() * cellSize) + 15,
                (cells[numRows - 1][numCols - 1].getColNum() * cellSize) + 15, cellSize, cellSize);
    }

    private void identifyCells() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cells[i][j] = new Cell(i,j);
            }
        }
    }

    private void identifyNeighbours() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                cells[i][j].addNeighbours(cells, numRows, numCols);
            }
        }
    }

    private void removeWalls(Cell current, Cell next) {
        int xDist = current.getRowNum() - next.getRowNum();
        int yDist = current.getColNum() - next.getColNum();
        if (xDist == -1) {
            current.getWalls()[1] = false;
            next.getWalls()[3] = false;
        }
        else if (xDist == 1) {
            current.getWalls()[3] = false;
            next.getWalls()[1] = false;
        }
        if (yDist == -1) {
            current.getWalls()[2] = false;
            next.getWalls()[0] = false;
        }
        else if (yDist == 1) {
            current.getWalls()[0] = false;
            next.getWalls()[2] = false;
        }
    }

    private void definePath() {
        boolean mazeFinished = false;
        while (!mazeFinished) {
            if (currentCell.checkUnvisitedNeighbours()) {
                Cell nextCell = currentCell.chooseNeighbour();
                stack.add(currentCell);
                removeWalls(currentCell, nextCell);
                generationPath.add(currentCell);
                currentCell = nextCell;
            }
            else if (stack.size() > 0) {
                Cell nextCell = stack.get(stack.size() - 1);
                stack.remove(nextCell);
                generationPath.add(currentCell);
                currentCell = nextCell;
            }
            else {
                mazeFinished = true;
            }
        }
    }

    private void drawMaze() {
        for (int i = 0; i < generationPath.size(); i++) {
            Cell cell = generationPath.get(i);
            if (cell.getRowNum() == 0 && cell.getColNum() == 0) {
                cell.getWalls()[0] = false;
            }
            else if (cell.getRowNum() == numRows - 1 && cell.getColNum() == numCols - 1) {
                cell.getWalls()[2] = false;
            }
            panel.drawMazeCell(cell.getRowNum(), cell.getColNum(), cell.getWalls());
        }
    }

    private void identifyMazePathNeighbours() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                //a simple alternative to having to write extensive code in the 'addMazePathNeighbours' method, which
                //would require checking to see if it is either the first or last cell in the maze, and if so, making
                //the border walls of the maze for those particular cells to be true instead of false like the others.
                if (i == 0 && j == 0) {
                    cells[i][j].getWalls()[0] = true;
                }
                else if (i == (numRows - 1) && j == (numCols - 1)) {
                    cells[i][j].getWalls()[2] = true;
                }
                cells[i][j].addMazePathNeighbours(cells);
                //set it back to what it was.
                if (i == 0 && j == 0) {
                    cells[i][j].getWalls()[0] = false;
                }
                else if (i == (numRows - 1) && j == (numCols - 1)) {
                    cells[i][j].getWalls()[2] = false;
                }
            }
        }
    }

    private void solveMaze() {
        boolean readyToSolve = false;
        startSearchCell.setGScore(0);
        startSearchCell.setFScore(heuristic(startSearchCell, endSearchCell));
        openSet.add(startSearchCell);
        while (!readyToSolve) {
            if (openSet.size() > 0) {
                currentSearchCell = findLowestF();
                if (currentSearchCell == endSearchCell) {
                    pathFound = true;
                    findSolutionPath();
                    for (int i = 0; i < searchPath.size() - 1; i++) {
                        panel.drawSolution(searchPath.get(i).getRowNum(), searchPath.get(i).getColNum(),
                                searchPath.get(i + 1).getRowNum(), searchPath.get(i + 1).getColNum(), numRows, numCols);
                    }
                    readyToSolve = true;
                }
                if (!pathFound) {
                    openSet.remove(currentSearchCell);
                    for (Cell neighbour: currentSearchCell.getMazePathNeighbours()) {
                        double tentativeGScore = currentSearchCell.getGScore() + 1;
                        if (tentativeGScore < neighbour.getGScore()) {
                            neighbour.setPreviousNeighbour(currentSearchCell);
                            neighbour.setGScore(tentativeGScore);
                            neighbour.setFScore(neighbour.getGScore() + heuristic(neighbour, endSearchCell));
                            if (!openSet.contains(neighbour)) {
                                openSet.add(neighbour);
                            }
                        }
                    }
                }
            }
        }
    }

    private ArrayList findSolutionPath() { //constructs path backwards to starting cell.
        Cell current = currentSearchCell;
        while (current != startSearchCell) {
            searchPath.add(current);
            current = current.getPreviousNeighbour();
        }
        current = startSearchCell;
        searchPath.add(current);
        return searchPath;
    }

    private double heuristic(Cell from, Cell to) {
        double distance;
        distance = Math.hypot(from.getRowNum() - to.getRowNum(), from.getColNum() - to.getColNum());
        return distance;
    }

    private Cell findLowestF() {
        Cell lowestFScore = openSet.get(0);
        for (Cell node: openSet) {
            if (node.getFScore() < lowestFScore.getFScore()) {
                lowestFScore = node;
            }
        }
        return lowestFScore;
    }

    public static void main(String[] args) throws IOException {
        GenerateMaze maze = new GenerateMaze(8, 8, false);
        //child maze min = 5x5 and max = 8x8
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}