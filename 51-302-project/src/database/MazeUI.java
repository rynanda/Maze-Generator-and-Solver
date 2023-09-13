package database;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Creates and handles the database UI.
 */

// Code mostly adapted from CAB302 week 6 practical.
public class MazeUI extends JFrame {
    private static final long serialVersionUID = -5050538890770582361L;

    private JList titleList;

    private JTextField title;

    private JTextField length;

    private JTextField height;

    private JTextField author;

    private JTextField created;

    private JTextField updated;

    private JButton newButton;

    private JButton saveButton;

    private JButton editButton;

    private JButton deleteButton;

    JFrame frame = new JFrame();

    MazeData data;

    /**
     * Constructor sets up user interface, adds listeners and displays.
     *
     * @param data The underlying data/model class the UI needs.
     */
    public MazeUI(MazeData data) {
        this.data = data;
        initUI();
        checkListSize();

        // add listeners to interactive components
        addButtonListeners(new ButtonListener());
        addTitleListListener(new TitleListListener());

        // decorate the frame and make it visible
        setTitle("Maze Database");
        setMinimumSize(new Dimension(400, 300));
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    /**
     * Places the detail panel and the button panel in a box layout with vertical
     * alignment and puts a 20 pixel gap between the components and the top and
     * bottom edges of the frame.
     */
    private void initUI() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeDetailsPanel());
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());
        contentPane.add(Box.createVerticalStrut(20));
    }

    /**
     * Makes a JPanel consisting of (1) the list of titles and (2) the maze
     * fields in a box layout with horizontal alignment and puts a 20 pixel gap
     * between the components and the left and right edges of the panel.
     *
     * @return the detail panel.
     */
    private JPanel makeDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.add(makeTitleListPane());
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.add(makeMazeFieldsPanel());
        detailsPanel.add(Box.createHorizontalStrut(20));
        return detailsPanel;
    }

    /**
     * Makes a JScrollPane that holds a JList for the list of titles in the
     * maze database.
     *
     * @return the scrolling title list panel
     */
    private JScrollPane makeTitleListPane() {
        titleList = new JList(data.getModel());
        titleList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(titleList);
        scroller.setViewportView(titleList);
        scroller
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        return scroller;
    }

    /**
     * Makes a JPanel containing labels and text fields for each of the pieces of
     * data that are to be recorded for each address. The labels and fields are
     * laid out using a GroupLayout, with the labels vertically grouped, the
     * fields vertically grouped and each label/group pair horizontally grouped.
     *
     * @return a panel containing the maze fields
     */
    private JPanel makeMazeFieldsPanel() {
        JPanel mazePanel = new JPanel();
        GroupLayout layout = new GroupLayout(mazePanel);
        mazePanel.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        JLabel titleLabel = new JLabel("Title");
        JLabel lengthLabel = new JLabel("Length");
        JLabel heightLabel = new JLabel("Height");
        JLabel authorLabel = new JLabel("Author");
        JLabel createdLabel = new JLabel("Created Date");
        JLabel updatedLabel = new JLabel("Updated Date");

        title = new JTextField(20);
        length = new JTextField(20);
        height = new JTextField(20);
        author = new JTextField(20);
        created = new JTextField(20);
        updated = new JTextField(20);
        setFieldsEditable(false);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(titleLabel)
                .addComponent(lengthLabel).addComponent(heightLabel).addComponent(
                        authorLabel).addComponent(createdLabel).addComponent(updatedLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(title)
                .addComponent(length).addComponent(height).addComponent(author)
                .addComponent(created).addComponent(updated));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        // The sequential group contains five parallel groups that align
        // the contents along the baseline. The first parallel group contains
        // the first label and text field, and the second parallel group contains
        // the second label and text field etc. By using a sequential group
        // the labels and text fields are positioned vertically after one another.
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(titleLabel).addComponent(title));

        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(lengthLabel).addComponent(length));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(heightLabel).addComponent(height));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(authorLabel).addComponent(author));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(createdLabel).addComponent(created));
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(updatedLabel).addComponent(updated));
        layout.setVerticalGroup(vGroup);

        return mazePanel;
    }

    /**
     * Adds the New, Save and Delete buttons to a panel
     */
    private JPanel makeButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        newButton = new JButton("New");
        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        deleteButton = new JButton("Delete");
        editButton = new JButton("Edit");
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(newButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Adds a listener to the new, save and delete buttons
     */
    private void addButtonListeners(ActionListener listener) {
        newButton.addActionListener(listener);
        saveButton.addActionListener(listener);
        editButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the title list
     */
    private void addTitleListListener(ListSelectionListener listener) {
        titleList.addListSelectionListener(listener);
    }

    /**
     * Adds a listener to the JFrame
     */
    private void addClosingListener(WindowListener listener) {
        addWindowListener(listener);
    }

    /**
     * Sets the text in the maze text fields to the empty string.
     */
    private void clearFields() {
        title.setText("");
        length.setText("");
        height.setText("");
        author.setText("");
        created.setText("");
        updated.setText("");
    }

    /**
     * Sets whether the maze fields are editable.
     */
    private void setFieldsEditable(boolean editable) {
        title.setEditable(editable);
        length.setEditable(editable);
        height.setEditable(editable);
        author.setEditable(editable);
        created.setEditable(editable);
        updated.setEditable((editable));
    }

    /**
     * Displays the details of a Maze in the maze fields.
     * @param maze the Maze to display.
     */
    private void display(Maze maze) {
        if (maze != null) {
            title.setText(maze.getTitle());
            length.setText(String.valueOf(maze.getLength()));
            height.setText(String.valueOf(maze.getHeight()));
            author.setText(maze.getAuthor());
            created.setText(maze.getCreatedDate());
            updated.setText(maze.getUpdatedDate());
        }
    }
    /**
     * Checks size of data/model and enables/disables the delete button
     *
     */
    private void checkListSize() {
        deleteButton.setEnabled(data.getSize() != 0);
        editButton.setEnabled(data.getSize() != 0);
    }

    /**
     * Handles events for the three buttons on the UI.
     */
    private class ButtonListener implements ActionListener {

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            int size = data.getSize();

            JButton source = (JButton) e.getSource();
            if (source == newButton) {
                newPressed();
            } else if (source == saveButton) {
                savePressed();
            } else if (source == editButton) {
                editPressed();
            } else if (source == deleteButton) {
                deletePressed();
            }
        }
    }
    /**
     * When the new button is pressed, clear the field display, make them
     * editable and enable the save button.
     */
    private void newPressed() {
        clearFields();
        setFieldsEditable(true);
        saveButton.setEnabled(true);
    }

    /**
     * When the save button is pressed, check that the title field contains
     * something. If it does, create a new Maze object and attempt to add it
     * to the data model. Change the fields back to not editable and make the
     * save button inactive.
     *
     * Check the list size to see if the delete button should be enabled.
     */
    private void savePressed() {
        try{
            if (title.getText() != null && !title.getText().equals("")) {
                Maze m = new Maze(Integer.parseInt(length.getText()), Integer.parseInt(height.getText()),
                        title.getText(), author.getText(), created.getText(), updated.getText());
                data.add(m);
            }
        } catch (NumberFormatException ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Please only enter numbers into the length and height fields.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        setFieldsEditable(false);
        saveButton.setEnabled(false);
        clearFields();
        checkListSize();
    }

    private void editPressed() {
        setFieldsEditable((true));
        saveButton.setEnabled(true);
    }

    /**
     * When the delete button is pressed remove the selected title from the
     * data model.
     *
     * Clear the fields that were displayed and check to see if the delete
     * button should be displayed.
     *
     * The index here handles cases where the first element of the list is
     * deleted.
     */
    private void deletePressed() {
        int index = titleList.getSelectedIndex();
        data.remove(titleList.getSelectedValue());
        clearFields();
        index--;
        if (index == -1) {
            if (data.getSize() != 0) {
                index = 0;
            }
        }
        titleList.setSelectedIndex(index);
        checkListSize();
    }

    /**
     * Implements a ListSelectionListener for making the UI respond when a
     * different title is selected from the list.
     */
    private class TitleListListener implements ListSelectionListener {

        /**
         * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            if (titleList.getSelectedValue() != null
                    && !titleList.getSelectedValue().equals("")) {
                display(data.get(titleList.getSelectedValue()));
            }
        }
    }
}
