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

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 80)); 
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        JLabel lblNewEmployee = new JLabel("New Employee");
        lblNewEmployee.setFont(new Font("Tahoma", Font.BOLD, 30)); 
        lblNewEmployee.setForeground(Color.WHITE);
        headerPanel.add(lblNewEmployee, BorderLayout.CENTER); // Center the text in header

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
        if (txtName.getText().trim().isEmpty() || txtSalary.getText().trim().isEmpty() ||
            txtPhone.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() ||
            txtAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Double.parseDouble(txtSalary.getText().trim());
            Long.parseLong(txtPhone.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Salary must be a number and Phone must be numeric!", "Error", JOptionPane.ERROR_MESSAGE);
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
