import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AllEmployee {

    JFrame frame;
    private JTable table;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AllEmployee window = new AllEmployee();
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
    public AllEmployee() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1000, 737);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));

        // Define the column names
        String[] columnNames = {"ID", "Name", "Salary", "Phone", "Email", "Address"};

        // Fetch data from the database
        Object[][] data = fetchDataFromDatabase();

     // Set the table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        table = new JTable(model);

        // Add the table to a scroll pane (to make it scrollable)
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER); // Add scroll pane to center

//        // Add a header panel (optional)
        JPanel headerPanel = new JPanel();
//        headerPanel.setBackground(new Color(0, 128, 128)); // Light blue
        JLabel lblTitle = new JLabel("Employees Data");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 128, 128)); // Correct way to set the color
        headerPanel.add(lblTitle);
        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);
        
     // Create Back button at the bottom center
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center align the button
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnBack.setBackground(new Color(0, 128, 128)); // Button color
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setPreferredSize(new Dimension(150, 40));
        
        // Action listener for the Back button
        btnBack.addActionListener(e -> {
            // Close the current window and go back to the previous screen (if desired)
            frame.dispose(); // Close the current window
        });
        
        bottomPanel.add(btnBack);
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH); // Add bottom panel to the south

    }

    /**
     * Fetch data from the database and return it as a 2D Object array.
     */
    private Object[][] fetchDataFromDatabase() {
        Object[][] data = new Object[0][0];
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database
            conn = DatabaseUtil.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM employee";
            rs = stmt.executeQuery(query);

            // Get the row count from ResultSet
            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst(); // Move cursor back to the start

            // Initialize data array
            data = new Object[rowCount][6];

            // Populate the data array with ResultSet values
            int rowIndex = 0;
            while (rs.next()) {
                data[rowIndex][0] = rs.getInt(1);
                data[rowIndex][1] = rs.getString(2);
                data[rowIndex][2] = rs.getDouble(3);
                data[rowIndex][3] = rs.getLong(4);
                data[rowIndex][4] = rs.getString(5);
                data[rowIndex][5] = rs.getString(6);
                rowIndex++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return data;
    }
}
