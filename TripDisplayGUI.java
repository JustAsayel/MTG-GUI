import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class TripDisplayGUI {

    JFrame frame = new JFrame(
            "Available Trips");frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);frame.setSize(600,300);

    String[] columnNames = { "Trip ID", "City", "Start Date", "End Date", "Tour Name" };
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);frame.add(scrollPane);

    fetchData(model);

        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

    private static Connection openDatabase() {
        String url = "jdbc:mysql://localhost:3306/MTG";
        String user = "root";
        String password = "Syetem";

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

    private static void fetchData(DefaultTableModel model) {
        Connection connection = openDatabase();
        if (connection != null) {
            String query = "SELECT TRIP.TRIP_ID, CITIES.CITY_NAME, TRIP.START_DATE, TRIP.END_DATE, ADDITIONAL_SERVICES.TOUR_GUIDE_NAME "
                    +
                    "FROM TRIP " +
                    "JOIN CITIES ON TRIP.CITY_ID = CITIES.CITY_ID " +
                    "JOIN ADDITIONAL_SERVICES ON TRIP.CUSTOMER_ID = ADDITIONAL_SERVICES.CUSTOMER_ID";
            ResultSet resultSet = executeQuery(connection, query);
            try {
                while (resultSet != null && resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(resultSet.getInt("TRIP_ID"));
                    row.add(resultSet.getString("CITY_NAME"));
                    row.add(resultSet.getDate("START_DATE"));
                    row.add(resultSet.getDate("END_DATE"));
                    row.add(resultSet.getString("TOUR_GUIDE_NAME"));
                    model.addRow(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeDatabase(connection);
            }
        }
    }
}
