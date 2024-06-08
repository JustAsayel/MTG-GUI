import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManagerGUI extends JFrame {
    private JTextField txtFirstName, txtLastName, txtAge, txtPhone, txtAdditionalServices;
    private JRadioButton rbtnMale, rbtnFemale;
    private ButtonGroup sexGroup;
    private JButton btnAdd, btnSearch, btnEdit, btnDelete;
    private JTable table;
    private DefaultTableModel tableModel;
    private Connection connection;

    public DatabaseManagerGUI() {
        setTitle("My Information");
        setSize(800, 600);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtAge = new JTextField();
        txtPhone = new JTextField();
        txtAdditionalServices = new JTextField();

        rbtnMale = new JRadioButton("Male");
        rbtnFemale = new JRadioButton("Female");
        sexGroup = new ButtonGroup();
        sexGroup.add(rbtnMale);
        sexGroup.add(rbtnFemale);
        JPanel sexPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sexPanel.add(rbtnMale);
        sexPanel.add(rbtnFemale);

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(txtFirstName);
        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(txtLastName);
        formPanel.add(new JLabel("Sex:"));
        formPanel.add(sexPanel);
        formPanel.add(new JLabel("Age:"));
        formPanel.add(txtAge);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(txtPhone);
        formPanel.add(new JLabel("Additional Services:"));
        formPanel.add(txtAdditionalServices);
        JPanel buttonPanel = new JPanel();
        btnAdd = new JButton("Add");
        btnSearch = new JButton("Search");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(
                new Object[] { "First Name", "Last Name", "Sex", "Age", "Phone", "Additional Services" }, 0);
        table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(formPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        connectToDatabase();
        loadCustomerData();

        btnAdd.addActionListener(e -> addCustomer());
        btnSearch.addActionListener(e -> searchCustomer());
        btnEdit.addActionListener(e -> editCustomer());
        btnDelete.addActionListener(e -> deleteCustomer());

        setVisible(true);
    }

    private void connectToDatabase() {
        String url = "jdbc:mysql://localhost:3306/MTG";
        String user = "root";
        String password = "System";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerData() {
        String query = "SELECT CUSTOMER.FIRST_NAME, CUSTOMER.LAST_NAME, CUSTOMER.GENDER, CUSTOMER.AGE, " +
                "CUSTOMER.PHONE_NUMBER, ADDITIONAL_SERVICES.TOUR_GUIDE_NAME " +
                "FROM CUSTOMER " +
                "LEFT JOIN ADDITIONAL_SERVICES ON CUSTOMER.CUSTOMER_ID = ADDITIONAL_SERVICES.CUSTOMER_ID";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String firstName = rs.getString("FIRST_NAME");
                String lastName = rs.getString("LAST_NAME");
                String gender = rs.getString("GENDER").equals("M") ? "Male" : "Female";
                int age = rs.getInt("AGE");
                String phone = rs.getString("PHONE_NUMBER");
                String additionalServices = rs.getString("TOUR_GUIDE_NAME") != null ? rs.getString("TOUR_GUIDE_NAME")
                        : "None";

                tableModel.addRow(new Object[] { firstName, lastName, gender, age, phone, additionalServices });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCustomer() {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String gender = rbtnMale.isSelected() ? "M" : "F";
        int age = Integer.parseInt(txtAge.getText());
        String phone = txtPhone.getText();
        String additionalServices = txtAdditionalServices.getText();

        String insertCustomerQuery = "INSERT INTO CUSTOMER (FIRST_NAME, LAST_NAME, GENDER, AGE, PHONE_NUMBER) VALUES (?, ?, ?, ?, ?)";
        String insertAdditionalServicesQuery = "INSERT INTO ADDITIONAL_SERVICES (TOUR_GUIDE_NAME, CUSTOMER_ID) VALUES (?, LAST_INSERT_ID())";

        try (PreparedStatement pstCustomer = connection.prepareStatement(insertCustomerQuery);
                PreparedStatement pstAdditionalServices = connection.prepareStatement(insertAdditionalServicesQuery)) {
            pstCustomer.setString(1, firstName);
            pstCustomer.setString(2, lastName);
            pstCustomer.setString(3, gender);
            pstCustomer.setInt(4, age);
            pstCustomer.setString(5, phone);
            pstCustomer.executeUpdate();

            if (!additionalServices.isEmpty()) {
                pstAdditionalServices.setString(1, additionalServices);
                pstAdditionalServices.executeUpdate();
            }

            tableModel.addRow(new Object[] { firstName, lastName, gender.equals("M") ? "Male" : "Female", age, phone,
                    additionalServices });

            // Clear input fields
            txtFirstName.setText("");
            txtLastName.setText("");
            sexGroup.clearSelection();
            txtAge.setText("");
            txtPhone.setText("");
            txtAdditionalServices.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void searchCustomer() {
        String searchQuery = txtFirstName.getText().toLowerCase();
        ArrayList<Object[]> results = new ArrayList<>();

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String firstName = tableModel.getValueAt(i, 0).toString().toLowerCase();
            if (firstName.contains(searchQuery)) {
                results.add(new Object[] {
                        tableModel.getValueAt(i, 0),
                        tableModel.getValueAt(i, 1),
                        tableModel.getValueAt(i, 2),
                        tableModel.getValueAt(i, 3),
                        tableModel.getValueAt(i, 4),
                        tableModel.getValueAt(i, 5)
                });
            }
        }

        tableModel.setRowCount(0);

        for (Object[] row : results) {
            tableModel.addRow(row);
        }
    }

    private void editCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String gender = rbtnMale.isSelected() ? "M" : "F";
            int age = Integer.parseInt(txtAge.getText());
            String phone = txtPhone.getText();
            String additionalServices = txtAdditionalServices.getText();

            String updateCustomerQuery = "UPDATE CUSTOMER SET FIRST_NAME = ?, LAST_NAME = ?, GENDER = ?, AGE = ?, PHONE_NUMBER = ? WHERE CUSTOMER_ID = ?";
            String updateAdditionalServicesQuery = "UPDATE ADDITIONAL_SERVICES SET TOUR_GUIDE_NAME = ? WHERE CUSTOMER_ID = ?";

            try (PreparedStatement pstCustomer = connection.prepareStatement(updateCustomerQuery);
                    PreparedStatement pstAdditionalServices = connection
                            .prepareStatement(updateAdditionalServicesQuery)) {
                pstCustomer.setString(1, firstName);
                pstCustomer.setString(2, lastName);
                pstCustomer.setString(3, gender);
                pstCustomer.setInt(4, age);
                pstCustomer.setString(5, phone);
                int customerId = (int) tableModel.getValueAt(selectedRow, 6);
                pstCustomer.setInt(6, customerId);
                pstCustomer.executeUpdate();

                if (!additionalServices.isEmpty()) {
                    pstAdditionalServices.setString(1, additionalServices);
                    pstAdditionalServices.setInt(2, customerId);
                    pstAdditionalServices.executeUpdate();
                }

                tableModel.setValueAt(firstName, selectedRow, 0);
                tableModel.setValueAt(lastName, selectedRow, 1);
                tableModel.setValueAt(gender.equals("M") ? "Male" : "Female", selectedRow, 2);
                tableModel.setValueAt(age, selectedRow, 3);
                tableModel.setValueAt(phone, selectedRow, 4);
                tableModel.setValueAt(additionalServices, selectedRow, 5);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteCustomer() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int customerId = (int) tableModel.getValueAt(selectedRow, 6);
            String deleteCustomerQuery = "DELETE FROM CUSTOMER WHERE CUSTOMER_ID = ?";
            String deleteAdditionalServicesQuery = "DELETE FROM ADDITIONAL_SERVICES WHERE CUSTOMER_ID = ?";

            try (PreparedStatement pstCustomer = connection.prepareStatement(deleteCustomerQuery);
                    PreparedStatement pstAdditionalServices = connection
                            .prepareStatement(deleteAdditionalServicesQuery)) {
                pstCustomer.setInt(1, customerId);
                pstCustomer.executeUpdate();

                pstAdditionalServices.setInt(1, customerId);
                pstAdditionalServices.executeUpdate();

                tableModel.removeRow(selectedRow);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
