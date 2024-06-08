import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

public class OrderQueryGUI {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Order Query");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);

        String[] columnNames = { "Trip ID", "City", "Start Date", "Days" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout());

        JTextField searchField = new JTextField(10);
        JButton searchButton = new JButton("Search by ID");
        JButton refreshButton = new JButton("Refresh");

        searchButton.addActionListener(e -> {
            String text = searchField.getText();
            TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);
            sorter.setRowFilter(RowFilter.regexFilter(text, 0));
        });

        refreshButton.addActionListener(e -> {
            loadDataFromDatabase(model);
            table.setRowSorter(new TableRowSorter<>(model));
        });

        southPanel.add(searchField);
        southPanel.add(searchButton);
        southPanel.add(refreshButton);

        frame.add(southPanel, BorderLayout.SOUTH);
        loadDataFromDatabase(model);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

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
            String query = "SELECT o.Trip_id, c.City_name, t.start_date, DATEDIFF(t.end_date, t.start_date) AS days " +
                    "FROM Orders o " +
                    "JOIN Trip t ON o.Trip_id = t.Trip_id " +
                    "JOIN Cities c ON t.City_id = c.City_id";
            ResultSet resultSet = executeQuery(connection, query);
            try {
                model.setRowCount(0);
                while (resultSet != null && resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(resultSet.getInt("Trip_id"));
                    row.add(resultSet.getString("City_name"));
                    row.add(resultSet.getDate("start_date"));
                    row.add(resultSet.getInt("days"));
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
