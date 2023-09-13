package database;

import java.util.Set;

/**
 * Provides functionality needed by any data source for the Maze application.
 */

// Some code adapted from CAB302 week 6 practical.
public interface MazeDataSource {
    /**
     * Adds a Maze to the maze database, if such maze does not already exist.
     * @param m Maze to add
     */
    void addMaze(Maze m);

    /**
     * Extracts all details of a Maze from the maze database based of the title passed in.
     *
     * @param title The title as a String to search for.
     * @return all details in a Maze object for the title.
     */
    Maze getMaze(String title);

    /**
     * Gets the number of mazes in the maze database.
     * @return
     */
    int getSize();

    /**
     * Deletes a Maze from the maze database.
     * @param title The title to delete from the maze database.
     */
    void deleteMaze(String title);

    /**
     * Edits a Maze's title from the maze database.
     * @param title The title of the maze to edit from the maze database.
     */
    void editMaze(String newTitle, Integer newLength, Integer newHeight, String title);

    /**
     * Finalizes any resources used by the data source and ensures data is persisted.
     */
    void close();

    /**
     * Retrieves a set of titles from the data source that are used
     * in the title list.
     * @return the set of titles.
     */
    Set<String> titleSet();
}
