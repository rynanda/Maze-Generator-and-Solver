package database;

import javax.swing.*;
import java.sql.*;
import java.util.Set;
import java.util.TreeSet;
import java.sql.Timestamp;

/**
 * Class for retrieving data from Maze program of mazes created and necessary details.
 */

// Some code adapted from CAB302 week 6 practical.
public class JDBCMazeDataSource implements MazeDataSource {
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                    + "title VARCHAR(30),"
                    + "length INT,"
                    + "height INT,"
                    + "author VARCHAR(30),"
                    + "created_date DATETIME DEFAULT CURRENT_TIMESTAMP," // DATETIME DEFAULT CURRENT_TIMESTAMP
                    + "updated_date DATETIME DEFAULT CURRENT_TIMESTAMP" // TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
                    + ");";

    private static final String INSERT_MAZE = "INSERT INTO maze (title, length, height, author, created_date, updated_date)" +
            "VALUES (?, ?, ?, ?, ?, ?)";

    private static final String GET_TITLES = "SELECT title FROM maze";

    private static final String GET_MAZE = "SELECT * FROM maze WHERE title=?";

    private static final String DELETE_MAZE = "DELETE FROM maze WHERE title=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM maze";

    private static final String EDIT_MAZE = "UPDATE maze SET title=?, length=?, height=? WHERE title=?";

    private Connection connection;

    private PreparedStatement addMaze;

    private PreparedStatement getTitleList;

    private PreparedStatement getMaze;

    private PreparedStatement deleteMaze;

    private PreparedStatement rowCount;

    private PreparedStatement editMaze;

    JFrame frame = new JFrame();

    public JDBCMazeDataSource () {
        connection = DBConnection.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addMaze = connection.prepareStatement(INSERT_MAZE);
            getTitleList = connection.prepareStatement(GET_TITLES);
            getMaze = connection.prepareStatement(GET_MAZE);
            deleteMaze = connection.prepareStatement(DELETE_MAZE);
            rowCount = connection.prepareStatement(COUNT_ROWS);
            editMaze = connection.prepareStatement(EDIT_MAZE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred. " +
                    "Please sign out or exit then try again.", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * @see database.MazeDataSource#addMaze(database.Maze)
     * @param m Maze to add
     */
    public void addMaze(Maze m) {
        long now = System.currentTimeMillis();
        Timestamp sqlTimestamp = new Timestamp(now);
        try {
            addMaze.setString(1, m.getTitle());
            addMaze.setInt(2, m.getLength());
            addMaze.setInt(3, m.getHeight());
            addMaze.setString(4, m.getAuthor());
            addMaze.setString(5, String.valueOf(sqlTimestamp));
            addMaze.setString(6, String.valueOf(sqlTimestamp));
            addMaze.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @see database.MazeDataSource#getMaze(java.lang.String)
     * @param title The title as a String to search for.
     * @return the Maze object to create from user input.
     */
    public Maze getMaze(String title) {
        Maze m = new Maze();
        ResultSet rs = null;
        try{
            getMaze.setString(1, title);
            rs = getMaze.executeQuery();
            rs.next();
            m.setLength(rs.getInt("length"));
            m.setHeight(rs.getInt("height"));
            m.setTitle(rs.getString("title"));
            m.setAuthor(rs.getString("author"));
            m.setCreatedDate(rs.getString("created_date"));
            m.setEditDate(rs.getString("updated_date"));
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return m;
    }

    /**
     * @see MazeDataSource#titleSet()
     */
    public Set<String> titleSet() {
        Set<String> titles = new TreeSet<String>();
        ResultSet rs = null;

        try {
            rs = getTitleList.executeQuery();
            while (rs.next()) {
                titles.add(rs.getString("title"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return titles;
    }

    /**
     * @see database.MazeDataSource#deleteMaze(java.lang.String)
     * @param title The title to delete from the maze database.
     */
    public void deleteMaze(String title) {
        try {
            deleteMaze.setString(1, title);
            deleteMaze.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     * @see database.MazeDataSource#editMaze(String, Integer, Integer, String)
     * @param newTitle The new title to replace the old maze title.
     * @param newLength The new length to replace the old maze length.
     * @param newHeight The new height to replace the old maze height.
     * @param title The title of the maze to edit from the maze database.
     */
    public void editMaze(String newTitle, Integer newLength, Integer newHeight, String title) {
        try {
            /* deleteMaze.setString(1, title); */
            /* deleteMaze.executeUpdate(); */
            deleteMaze(title);
            editMaze.setString(1, newTitle);
            editMaze.setInt(2, newLength);
            editMaze.setInt(3, newHeight);
            editMaze.setString(4, title);
            editMaze.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
     * @see MazeDataSource#getSize()
     */
    public int getSize() {
        ResultSet rs = null;
        int rows = 0;

        try {
            rs = rowCount.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rows;
    }

    /**
     * @see MazeDataSource#close()
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
