import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class AllEmployees {

    JFrame frame;
    private JTable table;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AllEmployees window = new AllEmployees();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AllEmployees() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("All Employee Data");
        frame.setBounds(100, 100, 1000, 737);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Open the frame in maximized state


        // Define the column names
        String[] columnNames = {"ID", "Name", "Salary", "Phone", "Email", "Address"};

        // Fetch data from the database
        Object[][] data = fetchDataFromDatabase();

        // Set the table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };
        table = new JTable(model);

     // Table customizations
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));

        // Increase row height (e.g., 35 pixels per row)
        table.setRowHeight(35);

        // Alternate row colors
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    comp.setBackground(row % 2 == 0 ? Color.LIGHT_GRAY : Color.WHITE);
                }
                return comp;
            }
        });

        // Customize table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Tahoma", Font.BOLD, 18));
        tableHeader.setBackground(new Color(0, 128, 128));
        tableHeader.setForeground(Color.WHITE);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Add a header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 128, 128));
        headerPanel.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Employees Data");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerPanel.add(lblTitle, BorderLayout.CENTER);

        frame.getContentPane().add(headerPanel, BorderLayout.NORTH);

        // Create Back button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnBack.setBackground(new Color(0, 128, 128));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setPreferredSize(new Dimension(150, 40));
        btnBack.addActionListener(e -> frame.dispose()); // Close the window
        bottomPanel.add(btnBack);
        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
    }

    private Object[][] fetchDataFromDatabase() {
        Object[][] data = new Object[0][0];
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseUtil.getConnection();
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT * FROM employee";
            rs = stmt.executeQuery(query);

            rs.last();
            int rowCount = rs.getRow();
            rs.beforeFirst();

            data = new Object[rowCount][6];
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
            JOptionPane.showMessageDialog(null, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
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
