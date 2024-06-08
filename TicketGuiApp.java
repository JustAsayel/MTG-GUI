import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class TicketGuiApp {

    JFrame frame = new JFrame(
            "Ticket");frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);frame.setSize(600,400);frame.setLayout(new BorderLayout());

    String[] columnNames = { "ID", "Technician Name", "Specialization", "Issue Description", "Priority", "Status" };

    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);frame.add(scrollPane,BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    JButton backButton = new JButton("Back");buttonPanel.add(backButton);frame.add(buttonPanel,BorderLayout.NORTH);

    String[] statuses = { "All", "Pending", "Confirmed", "Cancelled" };
    JComboBox<String> statusComboBox = new JComboBox<>(statuses);frame.add(statusComboBox,BorderLayout.SOUTH);

    statusComboBox.addActionListener(e->
    {
            String selectedStatus = (String) statusComboBox.getSelectedItem();
            filterTableByStatus(selectedStatus, model);
        });

    loadDataFromDatabase(model);
        frame.setVisible(true);

    private static Connection openDatabase() {
        String url = "jdbc:mysql://localhost:3306/MTG";
        String user = "root";
        String password = "System";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void closeDatabase(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static ResultSet executeQuery(Connection connection, String query) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void loadDataFromDatabase(DefaultTableModel model) {
        Connection connection = openDatabase();
        if (connection != null) {
            ResultSet resultSet = executeQuery(connection, "SELECT * FROM IT_Tickets");
            try {
                while (resultSet != null && resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(resultSet.getInt("Ticket_id"));
                    row.add(resultSet.getString("Technician_name"));
                    row.add(resultSet.getString("Technician_specialization"));
                    row.add(resultSet.getString("Issue_description"));
                    row.add(resultSet.getString("Priority"));
                    row.add(resultSet.getString("Status"));
                    model.addRow(row);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeDatabase(connection);
            }
        }
    }

    private static void filterTableByStatus(String status, DefaultTableModel model) {
        Connection connection = openDatabase();
        if (connection != null) {
            String query = "SELECT * FROM IT_Tickets";
            if (!status.equals("All")) {
                query += " WHERE Status = '" + status + "'";
            }

            ResultSet resultSet = executeQuery(connection, query);
            try {
                model.setRowCount(0);
                while (resultSet != null && resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(resultSet.getInt("Ticket_id"));
                    row.add(resultSet.getString("Technician_name"));
                    row.add(resultSet.getString("Technician_specialization"));
                    row.add(resultSet.getString("Issue_description"));
                    row.add(resultSet.getString("Priority"));
                    row.add(resultSet.getString("Status"));
                    model.addRow(row);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeDatabase(connection);
            }
        }
    }
}
