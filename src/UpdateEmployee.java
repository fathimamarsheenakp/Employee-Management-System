import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class UpdateEmployee {

    JFrame frame;
    JLabel label;
    private JTextField textField, nameField, salaryField, phoneField, emailField, addressField;
    private JCheckBox chkName, chkSalary, chkPhone, chkEmail, chkAddress;
    private JButton btnSet, btnUpdate;
    private String name, email, address;
    private double salary;
    private long phone;
    private int employeeId;
    private Map<JTextField, JLabel> labelMap = new HashMap<>();

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
        headerPanel.setBackground(new Color(0, 128, 128)); // Light blue
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        
     // Set the preferred height for the header panel
        headerPanel.setPreferredSize(new Dimension(headerPanel.getPreferredSize().width, 100));
        
     // Add vertical space to center the components
        headerPanel.add(Box.createVerticalStrut(30)); 
        
        JLabel lblTitle = new JLabel("UPDATE EMPLOYEE DETAILS");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

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
                String idPattern = "^[0-9]+$"; // Example: Only numeric IDs are allowed
                String enteredID = textField.getText().trim();

                if (enteredID.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter the ID to search!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!enteredID.matches(idPattern)) {
                    JOptionPane.showMessageDialog(frame, "Invalid ID! Only numeric IDs are allowed.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    fetchEmployee(); // Call the method to fetch employee details
                    textField.setText(""); // Clear the input field after search
                }
            }
        });


        // Employee Details Editable Fields (Initially Disabled)
        nameField = createTextField("Name", gbc, 0, 2, mainPanel);
        salaryField = createTextField("Salary", gbc, 0, 3, mainPanel);
        phoneField = createTextField("Phone", gbc, 0, 4, mainPanel);
        emailField = createTextField("Email", gbc, 0, 5, mainPanel);
        addressField = createTextField("Address", gbc, 0, 6, mainPanel);

        nameField.setVisible(false);
        salaryField.setVisible(false);
        phoneField.setVisible(false);
        emailField.setVisible(false);
        addressField.setVisible(false);

        // Checkbox Panel for selecting editable fields (Styled Checkboxes)
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(1, 5));
        checkboxPanel.setBackground(Color.WHITE);

        chkName = createStyledCheckBox("Name");
        chkSalary = createStyledCheckBox("Salary");
        chkPhone = createStyledCheckBox("Phone");
        chkEmail = createStyledCheckBox("Email");
        chkAddress = createStyledCheckBox("Address");

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

        // Initially hide checkboxes and buttons
        chkName.setVisible(false);
        chkSalary.setVisible(false);
        chkPhone.setVisible(false);
        chkEmail.setVisible(false);
        chkAddress.setVisible(false);

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

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) {
                    updateEmployee(); // Call the update method if inputs are valid
                    resetFieldsAndCheckboxes();
                }
            }
        });

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

    private JTextField createTextField(String labelText, GridBagConstraints gbc, int gridx, int gridy, JPanel panel) {
        JLabel label = new JLabel(labelText + ":");
        label.setFont(new Font("Tahoma", Font.BOLD, 18));
        label.setVisible(false);
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 18));
        textField.setEnabled(false); // Initially disabled
        textField.setBackground(Color.WHITE); 
        gbc.gridx = gridx + 1;
        panel.add(textField, gbc);

        // Map the label to the corresponding text field
        labelMap.put(textField, label);

        return textField;
    }



    private JCheckBox createStyledCheckBox(String text) {
        JCheckBox checkBox = new JCheckBox(text);
        checkBox.setFont(new Font("Tahoma", Font.BOLD, 16)); // Set font style and size
        checkBox.setForeground(new Color(0, 128, 128)); // Set text color
        checkBox.setBackground(Color.WHITE); // Set background color
        checkBox.setFocusPainted(false); // Remove focus border
        checkBox.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 128), 1)); // Add a border
        return checkBox;
    }

    private void showSetButtonAndCheckboxes() {
        // Show the checkboxes and set button after fetching the employee data
        chkName.setVisible(true);
        chkSalary.setVisible(true);
        chkPhone.setVisible(true);
        chkEmail.setVisible(true);
        chkAddress.setVisible(true);
        btnSet.setVisible(true);

        nameField.setVisible(true);
        salaryField.setVisible(true);
        phoneField.setVisible(true);
        emailField.setVisible(true);
        addressField.setVisible(true);
        
     // Show all labels associated with the fields
        for (JTextField textField : labelMap.keySet()) {
            JLabel label = labelMap.get(textField);
            if (label != null) {
                label.setVisible(true);
            }
        }

        // Revalidate and repaint the panel to reflect changes
        frame.revalidate();
        frame.repaint();
    }


    private void setEditableFields() {
        // Enable the fields based on the checkbox selection
        if (chkName.isSelected()) {
        	nameField.setEnabled(true);
            nameField.setEditable(true);
            nameField.setBackground(Color.WHITE);
        }
        if (chkSalary.isSelected()) {
        	salaryField.setEnabled(true);
            salaryField.setEditable(true);
            salaryField.setBackground(Color.WHITE);
        }
        if (chkPhone.isSelected()) {
        	phoneField.setEnabled(true);
            phoneField.setEditable(true);
            phoneField.setBackground(Color.WHITE);
        }
        if (chkEmail.isSelected()) {
        	emailField.setEnabled(true);
            emailField.setEditable(true);
            emailField.setBackground(Color.WHITE);
        }
        if (chkAddress.isSelected()) {
        	addressField.setEnabled(true);
            addressField.setEditable(true);
            addressField.setBackground(Color.WHITE);
        }
    }
    
    private boolean validateInputs() {
        String namePattern = "^[a-zA-Z\\s]+$";
        String salaryPattern = "^\\d+(\\.\\d{1,2})?$";
        String phonePattern = "^\\d{10}$";
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        String addressPattern = "^[a-zA-Z0-9\\s,.-]+$";

        if (chkName.isSelected() && !nameField.getText().trim().matches(namePattern)) {
            JOptionPane.showMessageDialog(frame, "Invalid Name! Only alphabets and spaces are allowed.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (chkSalary.isSelected() && !salaryField.getText().trim().matches(salaryPattern)) {
            JOptionPane.showMessageDialog(frame, "Invalid Salary! Only numeric values are allowed.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (chkPhone.isSelected() && !phoneField.getText().trim().matches(phonePattern)) {
            JOptionPane.showMessageDialog(frame, "Invalid Phone! Enter a valid 10-digit number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (chkEmail.isSelected() && !emailField.getText().trim().matches(emailPattern)) {
            JOptionPane.showMessageDialog(frame, "Invalid Email! Enter a valid email address.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (chkAddress.isSelected() && !addressField.getText().trim().matches(addressPattern)) {
            JOptionPane.showMessageDialog(frame, "Invalid Address! Only alphanumeric characters and basic punctuation are allowed.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
    
    private void resetFieldsAndCheckboxes() {
    	nameField.setEditable(false);
    	salaryField.setEditable(false);
    	phoneField.setEditable(false);
    	emailField.setEditable(false);
    	addressField.setEditable(false);
    	
    	// Unmark the checkboxes
        chkName.setSelected(false);
        chkSalary.setSelected(false);
        chkPhone.setSelected(false);
        chkEmail.setSelected(false);
        chkAddress.setSelected(false);
    }
    
    private void fetchEmployee() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        employeeId = Integer.parseInt(textField.getText().trim());
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
                
                showSetButtonAndCheckboxes(); // Show components if employee exists
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

    private void updateEmployee() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseUtil.getConnection();
            String query = "UPDATE employee SET name = ?, salary = ?, phone = ?, email = ?, address = ? WHERE id = ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, nameField.getText());
            pstmt.setDouble(2, Double.parseDouble(salaryField.getText()));
            pstmt.setLong(3, Long.parseLong(phoneField.getText()));
            pstmt.setString(4, emailField.getText());
            pstmt.setString(5, addressField.getText());
            pstmt.setInt(6, employeeId);

            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(frame, "Employee details updated successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Error updating employee details!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error updating employee details!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
