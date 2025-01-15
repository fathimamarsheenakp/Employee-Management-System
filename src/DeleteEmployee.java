import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DeleteEmployee {

    JFrame frame;
    private JTextField textField;
    private JLabel lblGetName, lblGetSalary, lblGetPhone, lblGetEmail, lblGetAddress, lblDeleteMessage;
    private String name, email, address;
    private double salary;
    private long phone;
    private JButton btnDelete;
    private int employeeId;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DeleteEmployee window = new DeleteEmployee();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public DeleteEmployee() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fetch Employee");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        JLabel lblEmployeeDetails = new JLabel("EMPLOYEE DETAILS");
        lblEmployeeDetails.setFont(new Font("Tahoma", Font.BOLD, 30));
        lblEmployeeDetails.setForeground(Color.WHITE);
        headerPanel.add(lblEmployeeDetails, BorderLayout.CENTER);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        // ID Label and Text Field
        JLabel lblId = new JLabel("Employee ID:");
        lblId.setFont(new Font("Tahoma", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(lblId, gbc);

        textField = new JTextField(20);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        gbc.gridx = 1;
        mainPanel.add(textField, gbc);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(0, 128, 128));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridx = 2;
        mainPanel.add(btnSearch, gbc);

        btnSearch.addActionListener(e -> {
            if (textField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter the ID to search!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                fetchEmployee();
                textField.setText("");
            }
        });

        // Employee Details Labels
        lblGetName = createLabel("", gbc, 0, 1, mainPanel);
        lblGetSalary = createLabel("", gbc, 0, 2, mainPanel);
        lblGetPhone = createLabel("", gbc, 0, 3, mainPanel);
        lblGetEmail = createLabel("", gbc, 0, 4, mainPanel);
        lblGetAddress = createLabel("", gbc, 0, 5, mainPanel);

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridx = 1;
        gbc.gridy = 6;
        mainPanel.add(btnDelete, gbc);
        btnDelete.setEnabled(false);
        btnDelete.setVisible(false);

        btnDelete.addActionListener(e -> {
            int confirmation = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to delete this employee?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                deleteEmployee();
            }
        });

        // Delete Message Label
        lblDeleteMessage = new JLabel("");
        lblDeleteMessage.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblDeleteMessage.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 7; // Position below other components
        gbc.gridwidth = 3; // Span across columns
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(lblDeleteMessage, gbc);

        // Bottom Panel for Back Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(0, 128, 128));
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

     // Create Back button
        JPanel bottomPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnBack.setBackground(new Color(0, 128, 128));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setPreferredSize(new Dimension(150, 40));
        btnBack.addActionListener(e -> frame.dispose()); // Close the window
        bottomPanel1.add(btnBack);
        frame.getContentPane().add(bottomPanel1, BorderLayout.SOUTH);
    }

    private JLabel createLabel(String value, GridBagConstraints gbc, int gridx, int gridy, JPanel panel) {
        JLabel label = new JLabel();
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        gbc.gridx = gridx + 1;
        panel.add(valueLabel, gbc);

        return valueLabel;
    }

    private void fetchEmployee() {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM employee WHERE id = ?")) {

            employeeId = Integer.parseInt(textField.getText().trim()); // Store the ID
            pstmt.setInt(1, employeeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("name");
                    salary = rs.getDouble("salary");
                    phone = rs.getLong("phone");
                    email = rs.getString("email");
                    address = rs.getString("address");

                    lblGetName.setText("<html><b>Name:</b>&nbsp;&nbsp;" + name + "</html>");
                    lblGetSalary.setText("<html><b>Salary:</b>&nbsp;&nbsp;" + salary + "</html>");
                    lblGetPhone.setText("<html><b>Phone:</b>&nbsp;&nbsp;" + phone + "</html>");
                    lblGetEmail.setText("<html><b>Email:</b>&nbsp;&nbsp;" + email + "</html>");
                    lblGetAddress.setText("<html><b>Address:</b>&nbsp;&nbsp;" + address + "</html>");
                    btnDelete.setEnabled(true);
                    btnDelete.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(frame, "No employee found with the given ID!", "No Results", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching employee details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteEmployee() {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM employee WHERE id = ?")) {

            pstmt.setInt(1, employeeId); // Use the stored ID
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                lblGetName.setText("");
                lblGetSalary.setText("");
                lblGetPhone.setText("");
                lblGetEmail.setText("");
                lblGetAddress.setText("");
                btnDelete.setEnabled(false);
                btnDelete.setVisible(false);

                // Show the deletion message
                lblDeleteMessage.setText("Employee with ID " + employeeId + " deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Failed to delete the employee!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error deleting employee details!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
