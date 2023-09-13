package database;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 * Uses a MazeDataSource and its methods to retrieve data.
 */

// Some code adapted from CAB302 week 6 practical.
public class MazeData {

    DefaultListModel listModel;

    MazeDataSource mazeData;

    /**
     * Constructor initializes the list model the holds title as Strings
     * and attempts to read any data saved from previous invocations of the application
     */
    public MazeData() {
        listModel = new DefaultListModel<>();
        mazeData = new JDBCMazeDataSource();

        for (String title : mazeData.titleSet()) {
            listModel.addElement(title);
        }
    }

    /**
     * Adds a maze to the database.
     * @param m A Maze to add to the database.
     */
    public void add(Maze m) {
        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(m.getTitle())) {
            listModel.addElement(m.getTitle());
            mazeData.addMaze(m);
        }
    }

    /**
     * Based on the title of the maze, delete the maze.
     *
     * @param key
     */
    public void remove(Object key) {

        // remove from both list and map
        listModel.removeElement(key);
        mazeData.deleteMaze((String) key);
    }

    /**
     * Saves the maze data using a persistence
     * mechanism.
     */
    public void persist() { mazeData.close(); }

    /**
     * Retrieves Maze details from the model.
     *
     * @param key the title to retrieve.
     * @return the Maze object related to the title.
     */
    public Maze get(Object key) {
        return mazeData.getMaze((String) key);
    }

    /**
     * Accessor for the list model.
     *
     * @return the listModel to display.
     */
    public ListModel getModel() {
        return listModel;
    }

    /**
     * @return the number of titles in the Maze.
     */
    public int getSize() {
        return mazeData.getSize();
    }
}
