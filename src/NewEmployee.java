import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class NewEmployee {

    JFrame frame;
    private JTextField txtName, txtSalary, txtPhone, txtEmail, txtAddress;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NewEmployee window = new NewEmployee();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NewEmployee() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 700); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("New Employee");
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
        
        JLabel lblTitle = new JLabel("NEW EMPLOYEE");
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

        // Labels and Text Fields
        addLabelAndTextField("Name:", txtName = new JTextField(20), mainPanel, gbc, 0);
        addLabelAndTextField("Salary:", txtSalary = new JTextField(20), mainPanel, gbc, 1);
        addLabelAndTextField("Phone:", txtPhone = new JTextField(20), mainPanel, gbc, 2);
        addLabelAndTextField("Email:", txtEmail = new JTextField(20), mainPanel, gbc, 3);
        addLabelAndTextField("Address:", txtAddress = new JTextField(20), mainPanel, gbc, 4);

        // Add Button
        JButton btnAdd = new JButton("Onboard");
        btnAdd.setBackground(new Color(0, 128, 128));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Tahoma", Font.BOLD, 18)); 
        gbc.gridx = 1;
        gbc.gridy = 5;
        mainPanel.add(btnAdd, gbc);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    addEmployee();
                    clearFields();
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

    private void addLabelAndTextField(String labelText, JTextField textField, JPanel panel, GridBagConstraints gbc, int gridy) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Tahoma", Font.BOLD, 18)); 
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        textField.setFont(new Font("Tahoma", Font.PLAIN, 18)); 
        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private boolean validateFields() {
        String name = txtName.getText().trim();
        String salary = txtSalary.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();

        // Check for empty fields
        if (name.isEmpty() || salary.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate name (allow only alphabets and spaces)
        if (!name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(frame, "Name must contain only alphabets and spaces!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate salary (must be a positive number)
        try {
            double salaryValue = Double.parseDouble(salary);
            if (salaryValue <= 0) {
                JOptionPane.showMessageDialog(frame, "Salary must be a positive number!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Salary must be a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate phone number (must be 10 digits)
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(frame, "Phone number must be exactly 10 digits!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validate email (basic email pattern)
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(frame, "Email must be in a valid format (e.g., user@example.com)!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }


    private void addEmployee() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseUtil.getConnection(); 
            String query = "INSERT INTO employee (name, salary, phone, email, address) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, txtName.getText().trim());
            pstmt.setDouble(2, Double.parseDouble(txtSalary.getText().trim()));
            pstmt.setLong(3, Long.parseLong(txtPhone.getText().trim()));
            pstmt.setString(4, txtEmail.getText().trim());
            pstmt.setString(5, txtAddress.getText().trim());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(frame, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error adding employee!", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        txtName.setText("");
        txtSalary.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
    }
}
