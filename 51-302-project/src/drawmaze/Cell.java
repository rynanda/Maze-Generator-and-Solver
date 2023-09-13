package drawmaze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Adapted from code done by YouTube creator, Codamy:
 * Maze generation: https://www.youtube.com/watch?v=zsG2ceOlY6I&t=3s&ab_channel=Codamy
 * Maze solve: https://www.youtube.com/watch?v=_KEzW50Nhyw&ab_channel=Codamy
 */
public class Cell {

    private int rowNum;
    private int colNum;
    private int totalRows;
    private int totalCols;
    private boolean[] walls = new boolean[] {true, true, true, true};
    private ArrayList<Cell> neighbouringCells = new ArrayList<>();
    private boolean visited = false;
    private Random randomGenerator = new Random();

    private boolean visitedBySearch = false; //*
    private ArrayList<Cell> mazePathNeighbours = new ArrayList<>();; //* //these are the neighbouring cell in the maze path.
    private double gScore = Double.MAX_VALUE; //* //A* search requires gScore to have a default value of Infinity.
    private double hScore = Double.MAX_VALUE; //* //A* search requires gScore to have a default value of Infinity.
    private double fScore; //*
    private Cell previousNeighbour; //*

    public Cell(int newRowNum, int newColNum) {
        rowNum = newRowNum;
        colNum = newColNum;
    }

    public void addNeighbours(Cell[][] cells, int numRows, int numCols) {
        totalRows = numRows;
        totalCols = numCols;
        if (rowNum > 0) {
            neighbouringCells.add(cells[rowNum - 1][colNum]);
        }
        if (colNum <  totalCols - 1) {
            neighbouringCells.add(cells[rowNum][colNum + 1]);
        }
        if (rowNum < totalRows - 1) {
            neighbouringCells.add(cells[rowNum + 1][colNum]);
        }
        if (colNum > 0) {
            neighbouringCells.add(cells[rowNum][colNum - 1]);
        }
    }

    public boolean checkUnvisitedNeighbours() {
        for (Cell neighbour : neighbouringCells) {
            if (!neighbour.visited) {
                return true;
            }
        }
        return false;
    }

    public Cell chooseNeighbour() {
        int index = randomGenerator.nextInt(neighbouringCells.size());
        Cell chosenNeighbour = neighbouringCells.get(index);
        while (chosenNeighbour.visited) {
            neighbouringCells.remove(chosenNeighbour);
            index = randomGenerator.nextInt(neighbouringCells.size());
            chosenNeighbour = neighbouringCells.get(index);
        }
        chosenNeighbour.visited = true;
        neighbouringCells.remove(chosenNeighbour);
        return chosenNeighbour;
    }

    public void addMazePathNeighbours(Cell[][] cells) {
        if (!walls[3]) {
            mazePathNeighbours.add(cells[rowNum - 1][colNum]);
        }
        if (!walls[2]) {
            mazePathNeighbours.add(cells[rowNum][colNum + 1]);
        }
        if (!walls[1]) {
            mazePathNeighbours.add(cells[rowNum + 1][colNum]);
        }
        if (!walls[0]) {
            mazePathNeighbours.add(cells[rowNum][colNum - 1]);
        }
    }

    public void setVisitStatus(boolean updatedStatus) {
        visited = updatedStatus;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public boolean[] getWalls() {
        return walls;
    }

    //A* search algorithm getters and setters.
    public double getGScore() {
        return gScore;
    }

    public void setGScore(double newGScore) {
        gScore = newGScore;
    }

    public void setHScore(double newHScore) {
        hScore = newHScore;
    }

    public double getFScore() {
        return fScore;
    }

    public void setFScore(double newFScore) {
        fScore = newFScore;
    }

    public void setPreviousNeighbour(Cell newPreviousNeighbour) {
        previousNeighbour = newPreviousNeighbour;
    }

    public Cell getPreviousNeighbour() {
        return previousNeighbour;
    }

    public ArrayList<Cell> getMazePathNeighbours() {
        return mazePathNeighbours;
    }
}