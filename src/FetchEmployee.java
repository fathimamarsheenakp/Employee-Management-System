import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FetchEmployee {

    JFrame frame;
    private JTextField textField;
    private JLabel lblGetName, lblGetSalary, lblGetPhone, lblGetEmail, lblGetAddress;
    private String name, email, address;
    private double salary;
    private long phone;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FetchEmployee window = new FetchEmployee();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public FetchEmployee() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fetch Employee");
        frame.getContentPane().setLayout(new BorderLayout());

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 60));
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        JLabel lblEmployeeDetails = new JLabel("Employee Details");
        lblEmployeeDetails.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblEmployeeDetails.setForeground(Color.WHITE);
        headerPanel.add(lblEmployeeDetails);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // ID Label and Text Field
        JLabel lblId = new JLabel("Enter Employee ID:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(lblId, gbc);

        textField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(textField, gbc);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(0, 128, 128));
        btnSearch.setForeground(Color.WHITE);
        gbc.gridx = 2;
        mainPanel.add(btnSearch, gbc);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter the ID to search!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    fetchEmployee();
                    textField.setText("");
                }
            }
        });

        // Employee Details Labels
        lblGetName = createLabel("Name:", "", gbc, 0, 1, mainPanel);
        lblGetSalary = createLabel("Salary:", "", gbc, 0, 2, mainPanel);
        lblGetPhone = createLabel("Phone:", "", gbc, 0, 3, mainPanel);
        lblGetEmail = createLabel("Email:", "", gbc, 0, 4, mainPanel);
        lblGetAddress = createLabel("Address:", "", gbc, 0, 5, mainPanel);

        // Bottom Panel for Back Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(0, 128, 128));
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Back");
        btnBack.setPreferredSize(new Dimension(120, 40));
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(new Color(0, 128, 128));
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 16));
        bottomPanel.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    private JLabel createLabel(String labelText, String value, GridBagConstraints gbc, int gridx, int gridy, JPanel panel) {
        JLabel label = new JLabel(labelText);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        JLabel valueLabel = new JLabel(value);
        gbc.gridx = gridx + 1;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(valueLabel, gbc);

        return valueLabel;
    }

    private void fetchEmployee() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection(); // Assume DatabaseUtil handles database connection
            String query = "SELECT * FROM employee WHERE id = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, Integer.parseInt(textField.getText().trim()));
            rs = pstmt.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
                salary = rs.getDouble("salary");
                phone = rs.getLong("phone");
                email = rs.getString("email");
                address = rs.getString("address");

                lblGetName.setText("Name: " + name);
                lblGetSalary.setText("Salary: " + salary);
                lblGetPhone.setText("Phone: " + phone);
                lblGetEmail.setText("Email: " + email);
                lblGetAddress.setText("Address: " + address);
            } else {
                JOptionPane.showMessageDialog(frame, "No employee found with the given ID!", "No Results", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching employee details!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
