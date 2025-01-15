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
        frame.setBounds(100, 100, 1000, 700); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Fetch Employee");
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
        
        JLabel lblTitle = new JLabel("EMPLOYEE DETAILS");
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
                if (textField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter the ID to search!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    fetchEmployee();
                    textField.setText("");
                }
            }
        });

        // Employee Details Labels
        lblGetName = createLabel("", gbc, 0, 1, mainPanel);
        lblGetSalary = createLabel("", gbc, 0, 2, mainPanel);
        lblGetPhone = createLabel("", gbc, 0, 3, mainPanel);
        lblGetEmail = createLabel("", gbc, 0, 4, mainPanel);
        lblGetAddress = createLabel("", gbc, 0, 5, mainPanel);

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
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(valueLabel, gbc);

        return valueLabel;
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

                lblGetName.setText("<html><b>Name:</b>&nbsp;&nbsp;" + name + "</html>");
                lblGetSalary.setText("<html><b>Salary:</b>&nbsp;&nbsp;" + salary + "</html>");
                lblGetPhone.setText("<html><b>Phone:</b>&nbsp;&nbsp;" + phone + "</html>");
                lblGetEmail.setText("<html><b>Email:</b>&nbsp;&nbsp;" + email + "</html>");
                lblGetAddress.setText("<html><b>Address:</b>&nbsp;&nbsp;" + address + "</html>");


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
