import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Home {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Home window = new Home();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public Home() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 844, 625);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Open the frame in maximized state
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128)); // Light blue
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        
     // Set the preferred height for the header panel
        headerPanel.setPreferredSize(new Dimension(headerPanel.getPreferredSize().width, 120));
        
     // Add vertical space to center the components
        headerPanel.add(Box.createVerticalStrut(30)); 
        
        JLabel lblWelcome = new JLabel("WELCOME TO");
        lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblWelcome.setForeground(Color.WHITE);
        headerPanel.add(lblWelcome);
        
        JLabel lblTitle = new JLabel("EMPLOYEE MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton btnFetchAllEmployees = createButton("FETCH ALL EMPLOYEES");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(btnFetchAllEmployees, gbc);
        
     // Action Listener to open Array window
        btnFetchAllEmployees.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of AllEmployee and set it visible in a new window
                AllEmployees allEmployeeWindow = new AllEmployees();
                allEmployeeWindow.frame.setVisible(true); // Open AllEmployee window
            }
        });

        JButton btnOnboardNewEmployee = createButton("ONBOARD NEW EMPLOYEE");
        gbc.gridy = 1;
        mainPanel.add(btnOnboardNewEmployee, gbc);
        
        btnOnboardNewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of AllEmployee and set it visible in a new window
                NewEmployee newEmployee = new NewEmployee();
                newEmployee.frame.setVisible(true); // Open AllEmployee window
            }
        });

        JButton btnFetchEmployeeById = createButton("FETCH EMPLOYEE BY ID");
        gbc.gridy = 2;
        mainPanel.add(btnFetchEmployeeById, gbc);
        
        btnFetchEmployeeById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of AllEmployee and set it visible in a new window
                FetchEmployee fetchEmployee = new FetchEmployee();
                fetchEmployee.frame.setVisible(true); // Open AllEmployee window
            }
        });

        JButton btnUpdateEmployee = createButton("UPDATE EMPLOYEE");
        gbc.gridy = 3;
        mainPanel.add(btnUpdateEmployee, gbc);

        JButton btnDeleteEmployeeById = createButton("DELETE EMPLOYEE BY ID");
        gbc.gridy = 4;
        mainPanel.add(btnDeleteEmployeeById, gbc);
        
        btnDeleteEmployeeById.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create an instance of AllEmployee and set it visible in a new window
                DeleteEmployee deleteEmployee = new DeleteEmployee();
                deleteEmployee.frame.setVisible(true); // Open AllEmployee window
            }
        });

        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Create a styled button.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.PLAIN, 18));
        button.setBackground(new Color(0, 128, 128)); // Cornflower blue
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(300, 50));
        
     // Add hover effect to change background color
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(32, 178, 170)); // Lighter shade
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 128, 128)); // Original color
            }
        });
        
        return button;
    }
}
