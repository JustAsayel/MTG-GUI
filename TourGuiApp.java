iimport javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Vector;

public class TourGuiApp {

    JFrame frame = new JFrame(
            "Tour Price Query");frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);frame.setSize(800,400);frame.setLayout(new BorderLayout());

    String[] columnNames = { "ID", "City", "Tour Name", "Language", "Cost" };

    DefaultTableModel model=new DefaultTableModel(columnNames,0){@Override public Class<?>getColumnClass(int column){if(column==4){return Integer.class;}return String.class;}

    @Override public boolean isCellEditable(int row,int column){return false; // Make table cells non-editable
    }};
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);frame.add(scrollPane,BorderLayout.CENTER);
    JPanel buttonPanel = new JPanel();
    JButton backButton = new JButton("Back");
    JButton refreshButton = new JButton(
            "Filter by Cost");buttonPanel.add(backButton);buttonPanel.add(refreshButton);frame.add(buttonPanel,BorderLayout.SOUTH);

    refreshButton.addActionListener(e->
    {
            loadDataFromDatabase(model, true);
            table.repaint();
        });

    loadDataFromDatabase(model, false);
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

    private static void loadDataFromDatabase(DefaultTableModel model, boolean sortByCost) {
        Connection connection = openDatabase();
        if (connection != null) {
            String query = "SELECT id, city_name, tour_name, language, cost FROM TourPrices";
            if (sortByCost) {
                query += " ORDER BY cost ASC";
            } else {
                query += " ORDER BY id ASC";
            }
            ResultSet resultSet = executeQuery(connection, query);
            try {
                model.setRowCount(0);
                while (resultSet != null && resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    row.add(resultSet.getInt("id"));
                    row.add(resultSet.getString("city_name"));
                    row.add(resultSet.getString("tour_name"));
                    row.add(resultSet.getString("language"));
                    row.add(resultSet.getInt("cost"));
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
