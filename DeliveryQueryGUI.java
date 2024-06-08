import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.util.regex.Pattern;

public class DeliveryQueryGUI {

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Delivery Query");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        String[] columnNames = { "ID", "Company Name", "Type", "Cost Per Day" };

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JTextField filterText = new JTextField();
        filterText.setToolTipText("Type company name to filter...");

        JButton filterButton = new JButton("Search By Company");
        filterButton.addActionListener(e -> {
            String text = filterText.getText();
            if (text.trim().length() > 0) {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text), 1));
            } else {
                sorter.setRowFilter(null);
            }
        });

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(filterText, BorderLayout.CENTER);
        southPanel.add(filterButton, BorderLayout.EAST);

        frame.add(new JScrollPane(table), BorderLayout.CENTER);
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
            ResultSet resultSet = executeQuery(connection,
                    "SELECT Car_number, Car_name, color, Cost_of_car FROM Delivery_service");
            try {
                while (resultSet != null && resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(resultSet.getInt("Car_number"));
                    row.add(resultSet.getString("Car_name"));
                    row.add(resultSet.getString("color"));
                    row.add(resultSet.getString("Cost_of_car"));
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
