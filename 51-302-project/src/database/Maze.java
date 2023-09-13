package database;

import java.io.Serializable;

/**
 * Stores maze details.
 */

// Some code adapted from CAB302 week 6 practical.
public class Maze implements Comparable<Maze>, Serializable {

    private static final long serialVersionUID = -7092701502990374424L;
    private int mazeLength;
    private int mazeHeight;
    private String mazeTitle;
    private String authorName;
    private String mazeCreationDate;
    private String mazeEditDate;

    /**
     * A Maze object is instantiated and this constructor method is called to create a new maze.
     * Constructor to set values for Maze details.
     * @param newMazeLength number of cells length ways (x-axis).
     * @param newMazeHeight number of cells high (y-axis).
     * @param newMazeTitle title of the maze.
     * @param newAuthorName name of the author of the maze.
     * @param newMazeCreationDate date that the maze was created.
     * @param newMazeEditDate date that the maze was last edited.
     */
    public Maze(int newMazeLength, int newMazeHeight, String newMazeTitle,
                String newAuthorName, String newMazeCreationDate, String newMazeEditDate) {
        mazeLength = newMazeLength;
        mazeHeight = newMazeHeight;
        mazeTitle = newMazeTitle;
        authorName = newAuthorName;
        mazeCreationDate = newMazeCreationDate;
        mazeEditDate = newMazeEditDate;
    }

    /**
     * No args constructor.
     */
    public Maze() {
    }

    /**
     * @return the maze title.
     */
    public String getTitle() { return mazeTitle; }

    /**
     * @return the maze length.
     */
    public int getLength() { return mazeLength; }

    /**
     * @return the maze height.
     */
    public int getHeight() { return mazeHeight; }

    /**
     * @return the maze author
     */
    public String getAuthor() { return authorName; }

    /**
     * @return the maze creation date.
     */
    public String getCreatedDate() { return mazeCreationDate; }

    /**
     * @return the maze updated date.
     */
    public String getUpdatedDate() { return mazeEditDate; }

    /**
     * @param title the title to set.
     */
    public void setTitle(String title) { this.mazeTitle = title; }

    /**
     * @param length the length to set.
     */
    public void setLength(int length) { this.mazeLength = length; }

    /**
     * @param height the height to set.
     */
    public void setHeight(int height) { this.mazeHeight = height; }

    /**
     * @param author the author to set.
     */
    public void setAuthor(String author) { this.authorName = author; }

    /**
     * @param created the creation date to set.
     */
    public void setCreatedDate(String created) { this.mazeCreationDate = created; }

    /**
     * @param updated the updated date to set.
     */
    public void setEditDate(String updated) { this.mazeEditDate = updated; }

    /**
     * Compares this object with the specified object for order. Returns a
     * negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is
     * less than, equal to, or greater than the specified object.
     * @throws ClassCastException if the specified object's type prevents it from
     * being compared to this object.
     */
    @Override
    public int compareTo(Maze other) {
        return this.mazeTitle.compareTo(other.mazeTitle);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return mazeTitle + " " + mazeLength + ", " + mazeHeight
                + " " + authorName + " " + mazeCreationDate + " " + mazeEditDate;
    }
}
