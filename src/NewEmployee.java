import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

public class NewEmployee {

	JFrame frame;
	private JTextField textFieldName;
	private JTextField textFieldSalary;
	private JTextField textFieldPhone;
	private JTextField textFieldEmail;
	private JTextField textFieldAddress;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewEmployee window = new NewEmployee();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public NewEmployee() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1068, 777);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewEmployee = new JLabel("New Employee");
		lblNewEmployee.setForeground(new Color(0, 128, 128));
		lblNewEmployee.setFont(new Font("Tahoma", Font.BOLD, 28));
		lblNewEmployee.setBounds(404, 20, 227, 34);
		frame.getContentPane().add(lblNewEmployee);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBounds(0, 680, 1054, 60);
		frame.getContentPane().add(bottomPanel);
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
		JButton btnBack = new JButton("Back");
		btnBack.setPreferredSize(new Dimension(150, 40));
		btnBack.setForeground(Color.WHITE);
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnBack.setFocusPainted(false);
		btnBack.setBackground(new Color(0, 128, 128));
		bottomPanel.add(btnBack);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(new Color(192, 192, 192));
		mainPanel.setBounds(143, 85, 769, 573);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JLabel nameLabel = new JLabel("Name");
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		nameLabel.setBounds(77, 39, 134, 33);
		mainPanel.add(nameLabel);
		
		JLabel salaryLabel = new JLabel("Salary");
		salaryLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		salaryLabel.setBounds(77, 120, 134, 33);
		mainPanel.add(salaryLabel);
		
		JLabel phoneLabel = new JLabel("Phone Number");
		phoneLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		phoneLabel.setBounds(77, 199, 134, 33);
		mainPanel.add(phoneLabel);
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(77, 282, 134, 33);
		mainPanel.add(lblNewLabel_3);
		
		JLabel addressLabel = new JLabel("Address");
		addressLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addressLabel.setBounds(77, 366, 134, 33);
		mainPanel.add(addressLabel);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(192, 35, 446, 50);
		mainPanel.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldSalary = new JTextField();
		textFieldSalary.setColumns(10);
		textFieldSalary.setBounds(192, 114, 446, 50);
		mainPanel.add(textFieldSalary);
		
		textFieldPhone = new JTextField();
		textFieldPhone.setColumns(10);
		textFieldPhone.setBounds(192, 193, 446, 50);
		mainPanel.add(textFieldPhone);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		textFieldEmail.setBounds(192, 276, 446, 50);
		mainPanel.add(textFieldEmail);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setColumns(10);
		textFieldAddress.setBounds(192, 360, 446, 106);
		mainPanel.add(textFieldAddress);
		
		JButton btnOnborad = new JButton("Onborad");
		btnOnborad.setBounds(308, 512, 150, 40);
		mainPanel.add(btnOnborad);
		btnOnborad.setPreferredSize(new Dimension(150, 40));
		btnOnborad.setForeground(new Color(255, 255, 255));
		btnOnborad.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnOnborad.setFocusPainted(false);
		btnOnborad.setBackground(new Color(107, 107, 107));
		
		btnBack.addActionListener(e -> {
            // Close the current window and go back to the previous screen (if desired)
            frame.dispose(); // Close the current window
        });
		
	}
}
