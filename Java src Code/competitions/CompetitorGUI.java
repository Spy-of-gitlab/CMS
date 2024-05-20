package competitions;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.table.TableColumn;

public class CompetitorGUI extends JFrame {

    private CompetitorList competitorList;

    public CompetitorGUI() {
        this.competitorList = new CompetitorList();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Competitor Management");

        // Create buttons with improved aesthetics
        JButton viewCompetitorsButton = createStyledButton("Display Competitors");
        JButton viewAndAlterScoresButton = createStyledButton("Display and Alter Scores");
        JButton viewTableSortedButton = createStyledButton("Display Table Sorted");
        JButton viewDetailsButton = createStyledButton("Display Details");
        JButton viewFullDetailsButton = createStyledButton("Display Full Details");
        JButton editDetailsButton = createStyledButton("Edit Details");
        JButton removeCompetitorButton = createStyledButton("Delete Competitor");

        // Add action listeners
        viewCompetitorsButton.addActionListener(e -> displayCompetitors());
        viewAndAlterScoresButton.addActionListener(e -> displayAndAlterScores());
        viewTableSortedButton.addActionListener(e -> displayTableSorted());
        viewDetailsButton.addActionListener(e -> diplayDetails());
        viewFullDetailsButton.addActionListener(e -> displayFullDetails());
        editDetailsButton.addActionListener(e -> editDetails());
        removeCompetitorButton.addActionListener(e -> deleteCompetitor());

        // Create layout with improved aesthetics
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10)); // Added some padding between components
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Added padding to the panel
        panel.setBackground(Color.lightGray); // Set background color

        panel.add(viewCompetitorsButton);
        panel.add(viewAndAlterScoresButton);
        panel.add(viewTableSortedButton);
        panel.add(viewDetailsButton);
        panel.add(viewFullDetailsButton);
        panel.add(editDetailsButton);
        panel.add(removeCompetitorButton);

        getContentPane().add(panel);

        pack();
        setLocationRelativeTo(null); // Center the frame
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.blue); // Set background color
        button.setForeground(Color.white); // Set text color
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set font
        return button;
    }

    private void displayCompetitors() {
        // Read competitors from CSV file
        competitorList.readCompetitorDetailsFromFile("RunCompetitor.csv");

        // Get the list of competitors from CompetitorList
        List<Competitor> competitors = competitorList.getCompetitors();

        // Create a 2D array to store data for the JTable
        Object[][] data = new Object[competitors.size()][8]; // Assuming 8 columns

        for (int i = 0; i < competitors.size(); i++) {
            Competitor competitor = competitors.get(i);
            data[i][0] = competitor.getCompetitorNumber();
            data[i][1] = competitor.getName();
            data[i][2] = competitor.getDateOfBirth();
            data[i][3] = competitor.getGender();
            data[i][4] = competitor.getCountry();
            data[i][5] = competitor.getScores()[0];
            data[i][6] = competitor.getScores()[1];
            data[i][7] = competitor.getScores()[2];
            // Add more columns if needed
        }

        // Create column names
        String[] columnNames = {"Competitor Number", "Name", "Age", "Gender", "Country", "Score 1", "Score 2", "Score 3"};

        // Create a DefaultTableModel with the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Create a JTable with improved aesthetics
        JTable table = new JTable(tableModel);
        table.setAutoCreateRowSorter(true); // Enable sorting

        // Set table row height for better visibility
        table.setRowHeight(30);

        // Create a JScrollPane with improved aesthetics
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Create a new JFrame to display the table with improved aesthetics
        JFrame tableFrame = new JFrame("Competitor Table");
        tableFrame.getContentPane().setBackground(Color.white); // Set background color
        tableFrame.getContentPane().setLayout(new BorderLayout());

        // Create a JPanel for additional components (if needed)
        JPanel panel = new JPanel();
        panel.setBackground(Color.white); // Set background color
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Add additional components to the panel if needed
        // Add the panel and scrollPane to the tableFrame
        tableFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        tableFrame.getContentPane().add(panel, BorderLayout.SOUTH);

        tableFrame.setSize(800, 400);
        tableFrame.setLocationRelativeTo(null); // Center the frame
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close to avoid closing the main frame
        tableFrame.setVisible(true);
    }

    private void displayAndAlterScores() {
        // Read competitors from CSV file
        competitorList.readCompetitorDetailsFromFile("RunCompetitor.csv");

        // Get the list of competitors from CompetitorList
        List<Competitor> competitors = competitorList.getCompetitors();

        // Create an array of competitor names for the user to choose
        String[] competitorNames = new String[competitors.size()];
        for (int i = 0; i < competitors.size(); i++) {
            competitorNames[i] = competitors.get(i).getName();
        }

        // Create a custom JPanel to enhance the input dialog
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a label to display the instruction
        JLabel instructionLabel = new JLabel("Choose a competitor to view and alter scores:");
        inputPanel.add(instructionLabel, BorderLayout.NORTH);

        // Create a JComboBox to display the competitor names
        JComboBox<String> competitorComboBox = new JComboBox<>(competitorNames);
        inputPanel.add(competitorComboBox, BorderLayout.CENTER);

        // Show the input dialog with the custom panel
        int option = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                "View and Alter Scores",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Check if the user clicked "OK"
        if (option == JOptionPane.OK_OPTION) {
            // Find the selected competitor
            String selectedCompetitor = (String) competitorComboBox.getSelectedItem();
            Competitor selectedCompetitorObj = competitors.stream()
                    .filter(competitor -> competitor.getName().equals(selectedCompetitor))
                    .findFirst()
                    .orElse(null);

            if (selectedCompetitorObj != null) {
                // Display the current scores
                StringBuilder scoresMessage = new StringBuilder("Current Scores:\n");
                for (int score : selectedCompetitorObj.getScores()) {
                    scoresMessage.append(score).append("\n");
                }

                // Create a panel to hold the input components
                JPanel inputComponentsPanel = new JPanel(new GridLayout(0, 2, 5, 5));

                // Add labels and text fields for each score
                for (int i = 0; i < selectedCompetitorObj.getScores().length; i++) {
                    JLabel scoreLabel = new JLabel("Score " + (i + 1) + ":");
                    JTextField scoreField = new JTextField(String.valueOf(selectedCompetitorObj.getScores()[i]));

                    inputComponentsPanel.add(scoreLabel);
                    inputComponentsPanel.add(scoreField);
                }

                // Show the input dialog with the custom panel
                int alterOption = JOptionPane.showConfirmDialog(
                        this,
                        inputComponentsPanel,
                        "Alter Scores",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE);

                // Check if the user clicked "OK" in the alter dialog
                if (alterOption == JOptionPane.OK_OPTION) {
                    // Update the competitor's scores based on user input
                    for (int i = 0; i < selectedCompetitorObj.getScores().length; i++) {
                        JTextField scoreField = (JTextField) inputComponentsPanel.getComponent(i * 2 + 1);
                        try {
                            int newScore = Integer.parseInt(scoreField.getText().trim());
                            selectedCompetitorObj.getScores()[i] = newScore;
                        } catch (NumberFormatException e) {
                            // Handle invalid input
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Invalid input for scores. Please enter valid integers.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    // Inform the user about the update
                    JOptionPane.showMessageDialog(
                            this,
                            "Scores updated successfully!",
                            "Scores Updated",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // Handle if the selected competitor is not found
                JOptionPane.showMessageDialog(
                        this,
                        "Selected competitor not found.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayTableSorted() {
        // Read competitors from CSV file
        competitorList.readCompetitorDetailsFromFile("RunCompetitor.csv");

        // Get the list of competitors from CompetitorList
        List<Competitor> competitors = competitorList.getCompetitors();

        // Create an array of sorting options for the user to choose
        String[] sortingOptions = {"Competitor Number", "Name", "Overall Score"};

        // Display a dialog for the user to choose a sorting option
        String selectedSortingOption = (String) JOptionPane.showInputDialog(
                this,
                "Choose a sorting option:",
                "View Table Sorted",
                JOptionPane.QUESTION_MESSAGE,
                null,
                sortingOptions,
                sortingOptions[0]);

        if (selectedSortingOption != null) {
            // Sort the competitors based on the selected option
            switch (selectedSortingOption) {
                case "Competitor Number":
                    competitors.sort(Comparator.comparingInt(Competitor::getCompetitorNumber));
                    break;
                case "Name":
                    competitors.sort(Comparator.comparing(Competitor::getName));
                    break;
                case "Overall Score":
                    competitors.sort(Comparator.comparingDouble(Competitor::getOverallScore));
                    break;
                // Add more cases for additional sorting options
            }

            // Create a 2D array to store data for the JTable
            Object[][] data = new Object[competitors.size()][8]; // Assuming 8 columns

            for (int i = 0; i < competitors.size(); i++) {
                Competitor competitor = competitors.get(i);
                // Populate the data array based on the sorting option
                switch (selectedSortingOption) {
                    case "Competitor Number":
                        data[i][0] = competitor.getCompetitorNumber();
                        break;
                    case "Name":
                        data[i][1] = competitor.getName();
                        break;
                    case "Overall Score":
                        data[i][7] = competitor.getOverallScore();
                        break;
                    // Add more cases for additional columns
                }
                // Populate other common columns
                data[i][2] = competitor.getDateOfBirth();
                data[i][3] = competitor.getLevel();
                data[i][4] = competitor.getCountry();
                data[i][5] = competitor.getScores()[0];
                data[i][6] = competitor.getScores()[1];
            }

            // Create column names
            String[] columnNames = {"Competitor Number", "Name", "Age", "Gender", "Country", "Score 1", "Score 2", "Overall Score"};

            // Create a DefaultTableModel with the data and column names
            DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

            // Create a JTable with the DefaultTableModel
            JTable table = new JTable(tableModel);

            // Create a sorting panel with buttons
            JPanel sortingPanel = new JPanel();
            JButton sortAscendingButton = new JButton("Sort Ascending");
            JButton sortDescendingButton = new JButton("Sort Descending");

            // Add icons to the buttons for a more intuitive interface
            ImageIcon ascendingIcon = new ImageIcon("ascending_icon.png");
            ImageIcon descendingIcon = new ImageIcon("descending_icon.png");
            sortAscendingButton.setIcon(ascendingIcon);
            sortDescendingButton.setIcon(descendingIcon);

            // Add action listeners to the sorting buttons using lambda expressions
            sortAscendingButton.addActionListener(e -> {
                // Implement ascending sorting logic
                Collections.sort(competitors, Comparator.comparing(Competitor::getCompetitorNumber));
            });

            sortDescendingButton.addActionListener(e -> {
                // Implement descending sorting logic
                Collections.sort(competitors, Comparator.comparing(Competitor::getCompetitorNumber).reversed());
            });

            sortingPanel.add(sortAscendingButton);
            sortingPanel.add(sortDescendingButton);

            // Create a JScrollPane to add the table to, in case there are many rows
            JScrollPane scrollPane = new JScrollPane(table);

            // Create a new JFrame to display the sorted table
            JFrame sortedTableFrame = new JFrame("Sorted Competitor Table");
            sortedTableFrame.setLayout(new BorderLayout());
            sortedTableFrame.add(sortingPanel, BorderLayout.NORTH);
            sortedTableFrame.add(scrollPane, BorderLayout.CENTER);
            sortedTableFrame.setSize(800, 400);
            sortedTableFrame.setVisible(true);
        }

    }

    private void diplayDetails() {
        // Read competitors from CSV file
        competitorList.readCompetitorDetailsFromFile("RunCompetitor.csv");

        // Create an array of competitor numbers for the user to choose
        List<Competitor> competitors = competitorList.getCompetitors();
        String[] competitorNumbers = competitors.stream()
                .map(Competitor::getCompetitorNumber)
                .map(String::valueOf)
                .toArray(String[]::new);

        // Create a custom JPanel to enhance the input dialog
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a label to display the instruction
        JLabel instructionLabel = new JLabel("Choose Competitor Number:");
        inputPanel.add(instructionLabel, BorderLayout.NORTH);

        // Create a JComboBox to display the competitor numbers
        JComboBox<String> competitorComboBox = new JComboBox<>(competitorNumbers);
        inputPanel.add(competitorComboBox, BorderLayout.CENTER);

        // Show the input dialog with the custom panel
        int option = JOptionPane.showConfirmDialog(
                this,
                inputPanel,
                "View Competitor Details",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        // Check if the user clicked "OK"
        if (option == JOptionPane.OK_OPTION) {
            // Find the selected competitor
            String selectedCompetitorNumber = (String) competitorComboBox.getSelectedItem();
            try {
                int competitorNumber = Integer.parseInt(selectedCompetitorNumber);
                Competitor selectedCompetitor = competitors.stream()
                        .filter(comp -> comp.getCompetitorNumber() == competitorNumber)
                        .findFirst()
                        .orElse(null);

                if (selectedCompetitor != null) {
                    // Display the full details of the selected competitor
                    String details = selectedCompetitor.getFullDetails();
                    JOptionPane.showMessageDialog(
                            this,
                            details,
                            "Competitor Details",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Competitor not found.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        this,
                        "Invalid competitor number. Please enter a valid number.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayFullDetails() {
        // Read competitors from CSV file
        competitorList.readCompetitorDetailsFromFile("RunCompetitor.csv");

        // Get the list of competitors from CompetitorList
        List<Competitor> competitors = competitorList.getCompetitors();

        // Create column names
        String[] columnNames = {"Competitor Number", "Name", "Age", "Gender", "Country", "Score 1", "Score 2", "Score 3", "Overall Score"};

        // Create a 2D array to store data for the JTable
        Object[][] data = new Object[competitors.size()][columnNames.length];

        // Populate the data array
        for (int i = 0; i < competitors.size(); i++) {
            Competitor competitor = competitors.get(i);
            data[i][0] = competitor.getCompetitorNumber();
            data[i][1] = competitor.getName();
            data[i][2] = competitor.getDateOfBirth();
            data[i][3] = competitor.getGender();
            data[i][4] = competitor.getCountry();
            data[i][5] = competitor.getScores()[0];
            data[i][6] = competitor.getScores()[1];
            data[i][7] = competitor.getScores()[2];
            data[i][8] = competitor.getOverallScore();
        }

        // Create a DefaultTableModel with the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(tableModel);

        // Create a JScrollPane to add the table to, in case there are many rows
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a new JFrame to display the table
        JFrame tableFrame = new JFrame("Full Competitor Details");
        tableFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        tableFrame.setSize(800, 400);
        tableFrame.setVisible(true);
    }

    private void editDetails() {
        // Read competitors from CSV file
        competitorList.readCompetitorDetailsFromFile("RunCompetitor.csv");

        // Get the list of competitors from CompetitorList
        List<Competitor> competitors = competitorList.getCompetitors();

        // Create column names
        String[] columnNames = {"Competitor Number", "Name", "Date of Birth", "Gender", "Country", "Score 1", "Score 2", "Score 3"};

        // Create a DefaultTableModel with the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate the table model with data from competitors
        for (Competitor competitor : competitors) {
            Object[] rowData = {
                competitor.getCompetitorNumber(),
                competitor.getName(),
                competitor.getDateOfBirth(),
                competitor.getGender(),
                competitor.getCountry(),
                competitor.getScores()[0],
                competitor.getScores()[1],
                competitor.getScores()[2]
            };
            tableModel.addRow(rowData);
        }

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(tableModel);

        // Make specific columns editable
        int[] editableColumns = {2, 3, 4}; // Assuming you want to make columns 2, 3, and 4 editable
        for (int columnIndex : editableColumns) {
            TableColumn column = table.getColumnModel().getColumn(columnIndex);
            column.setCellEditor(new DefaultCellEditor(new JTextField()));
        }

        // Create a JScrollPane to add the table to, in case there are many rows
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a new JFrame to display the editable table
        JFrame tableFrame = new JFrame("Edit Competitor Details");

        // Show a confirmation dialog
        int result = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                "Edit Competitor Details",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Update competitor details based on the edited table
            updateCompetitorDetails(tableModel, competitors);
        }
    }

    private void updateCompetitorDetails(DefaultTableModel tableModel, List<Competitor> competitors) {

        // You might want to perform validation and error handling here
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Competitor competitor = competitors.get(i);
            competitor.setName((String) tableModel.getValueAt(i, 1));
            competitor.setDateOfBirth((String) tableModel.getValueAt(i, 2));
//            competitor.setAge((int) tableModel.getValueAt(i, 2));
            competitor.setGender((String) tableModel.getValueAt(i, 3));
            competitor.setLevel((String) tableModel.getValueAt(i, 4));
            int[] scores = {
                (int) tableModel.getValueAt(i, 5),
                (int) tableModel.getValueAt(i, 6),
                (int) tableModel.getValueAt(i, 7)
            };
            competitor.setScores(scores);
        }

    }

    private void deleteCompetitor() {
        // Read competitors from CSV file
        competitorList.readCompetitorDetailsFromFile("RunCompetitor.csv");

        // Get the list of competitors from CompetitorList
        List<Competitor> competitors = competitorList.getCompetitors();

        // Create column names
        String[] columnNames = {"Competitor Number", "Name", "Date of Birth", "Gender", "Country", "Score 1", "Score 2", "Score 3"};

        // Create a DefaultTableModel with the data and column names
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Populate the table model with data from competitors
        for (Competitor competitor : competitors) {
            Object[] rowData = {
                competitor.getCompetitorNumber(),
                competitor.getName(),
                competitor.getDateOfBirth(),
                competitor.getGender(),
                competitor.getCountry(),
                competitor.getScores()[0],
                competitor.getScores()[1],
                competitor.getScores()[2]
            };
            tableModel.addRow(rowData);
        }

        // Create a JTable with the DefaultTableModel
        JTable table = new JTable(tableModel);

        // Allow selection in the JTable
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create a JScrollPane to add the table to, in case there are many rows
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a new JFrame to display the table
        JFrame tableFrame = new JFrame("Remove Competitor");

        // Show a confirmation dialog
        int result = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                "Remove Competitor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            // Remove the selected competitor from the list
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                int confirmResult = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to remove the selected competitor?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    competitors.remove(selectedRow);
                    JOptionPane.showMessageDialog(this, "Competitor removed successfully.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No competitor selected. Please select a competitor to remove.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CompetitorGUI().setVisible(true));
    }
}
