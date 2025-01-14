import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateEmployee {

    JFrame frame;
    private JTextField textField, nameField, salaryField, phoneField, emailField, addressField;
    private JCheckBox chkName, chkSalary, chkPhone, chkEmail, chkAddress;
    private JButton btnSet, btnUpdate;
    private String name, email, address;
    private double salary;
    private long phone;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UpdateEmployee window = new UpdateEmployee();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public UpdateEmployee() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Update Employee");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Open the frame in maximized state

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        JLabel lblEmployeeDetails = new JLabel("Update Employee Details");
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
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(textField, gbc);

        // Search Button
        JButton btnSearch = new JButton("Search");
        btnSearch.setBackground(new Color(0, 128, 128));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridx = 2;
        mainPanel.add(btnSearch, gbc);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter the ID to search!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    fetchEmployee();
                    textField.setText("");
                    showSetButtonAndCheckboxes();
                }
            }
        });

        // Employee Details Editable Fields (Initially Disabled)
        nameField = createTextField(gbc, 0, 2, mainPanel);
        salaryField = createTextField(gbc, 0, 3, mainPanel);
        phoneField = createTextField(gbc, 0, 4, mainPanel);
        emailField = createTextField(gbc, 0, 5, mainPanel);
        addressField = createTextField(gbc, 0, 6, mainPanel);

        // Checkbox Panel for selecting editable fields (Initially Hidden)
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(1, 5));
        checkboxPanel.setBackground(Color.WHITE);

        chkName = new JCheckBox("Name");
        chkSalary = new JCheckBox("Salary");
        chkPhone = new JCheckBox("Phone");
        chkEmail = new JCheckBox("Email");
        chkAddress = new JCheckBox("Address");

        checkboxPanel.add(chkName);
        checkboxPanel.add(chkSalary);
        checkboxPanel.add(chkPhone);
        checkboxPanel.add(chkEmail);
        checkboxPanel.add(chkAddress);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(checkboxPanel, gbc);

        // Set Button (Initially Hidden)
        btnSet = new JButton("Set");
        btnSet.setBackground(new Color(0, 128, 128));
        btnSet.setForeground(Color.WHITE);
        btnSet.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridx = 3;
        gbc.gridy = 7;
        mainPanel.add(btnSet, gbc);
        btnSet.setVisible(false); // Hidden initially

        // Update Button (Initially Hidden)
        btnUpdate = new JButton("Update");
        btnUpdate.setBackground(new Color(0, 128, 128));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        btnUpdate.setVisible(false); // Hidden initially
        mainPanel.add(btnUpdate, gbc);

        btnSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setEditableFields();
                btnUpdate.setVisible(true); // Show Update button after Set is clicked
            }
        });

        // Bottom Panel for Back Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(0, 128, 128));
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        JButton btnBack = new JButton("Back");
        btnBack.setPreferredSize(new Dimension(120, 40));
        btnBack.setBackground(Color.WHITE);
        btnBack.setForeground(new Color(0, 128, 128));
        btnBack.setFont(new Font("Tahoma", Font.BOLD, 18));
        bottomPanel.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }

    private JTextField createTextField(GridBagConstraints gbc, int gridx, int gridy, JPanel panel) {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField.setEditable(false); // Initially disabled
        gbc.gridx = gridx + 1;
        gbc.gridy = gridy;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(textField, gbc);
        return textField;
    }

    private void fetchEmployee() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
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

                // Populate text fields with employee data
                nameField.setText(name);
                salaryField.setText(String.valueOf(salary));
                phoneField.setText(String.valueOf(phone));
                emailField.setText(email);
                addressField.setText(address);
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

    private void showSetButtonAndCheckboxes() {
        // Show the checkboxes and set button after fetching the employee data
        chkName.setEnabled(true);
        chkSalary.setEnabled(true);
        chkPhone.setEnabled(true);
        chkEmail.setEnabled(true);
        chkAddress.setEnabled(true);
        btnSet.setVisible(true);
    }

    private void setEditableFields() {
        // Enable the fields based on selected checkboxes
        if (chkName.isSelected()) {
            nameField.setEditable(true);
        }
        if (chkSalary.isSelected()) {
            salaryField.setEditable(true);
        }
        if (chkPhone.isSelected()) {
            phoneField.setEditable(true);
        }
        if (chkEmail.isSelected()) {
            emailField.setEditable(true);
        }
        if (chkAddress.isSelected()) {
            addressField.setEditable(true);
        }
    }
}
