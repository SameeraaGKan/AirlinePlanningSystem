package src;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.*;

public class FlightPlannerGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FlightPlannerGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Flight Planner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        // Layout manager
        frame.setLayout(new BorderLayout());

        // === TOP PANEL: Inputs ===
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        JTextField originField = new JTextField();
        JTextField destinationField = new JTextField();
        JRadioButton timeButton = new JRadioButton("Sort by Time");
        JRadioButton costButton = new JRadioButton("Sort by Cost");
        ButtonGroup group = new ButtonGroup();
        group.add(timeButton);
        group.add(costButton);
        timeButton.setSelected(true);

        inputPanel.add(new JLabel("Origin:"));
        inputPanel.add(originField);
        inputPanel.add(new JLabel("Destination:"));
        inputPanel.add(destinationField);
        inputPanel.add(timeButton);
        inputPanel.add(costButton);

        // === CENTER: Output ===
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // === BOTTOM: Button ===
        JButton searchButton = new JButton("Search Flights");
        searchButton.addActionListener(e -> {
            String origin = originField.getText().trim();
            String destination = destinationField.getText().trim();
            char sortBy = timeButton.isSelected() ? 'T' : 'C';

            if (origin.isEmpty() || destination.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter both cities.");
                return;
            }

            // Call your backend logic here
            List<String> results = FlightPlannerBackend.searchFlights(origin, destination, sortBy);
            outputArea.setText(String.join("\n", results));
        });

        // Add to frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(searchButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
