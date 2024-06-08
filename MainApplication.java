import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.lang.SecurityException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Random;
import java.util.Vector;

public class MainApplication {

    public static void main(String[] args) {

        // LOGO
        PlannerApp plannerApp = new PlannerApp();
        plannerApp.setVisible(true);

        // LOGIN
        UserInterface userInterface = new UserInterface();
        userInterface.setVisible(true);

        // HOMEPAGE
        SwingUtilities.invokeLater(() -> {
            Homepage hp = new Homepage();
            hp.setVisible(true);
        });

    }

    class userinterface extends JFrame {

        private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/MTG";
        private static final String DATABASE_USER = "root";
        private static final String DATABASE_PASSWORD = "System";

public userinterface() {
    setTitle("My Tour Guide" )
                    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(350, 300);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel loginPanel = new JPanel();
    placeComponentsLogin(loginPanel);
    tabbedPane.addTab("Login", loginPanel);

    JPanel signupPanel = new JPanel();
    placeComponentsSignup(signupPanel);
    tabbedPane.addTab("Sign Up", signupPanel);

    add(tabbedPane, BorderLayout.CENTER);
    
}

        private void placeComponentsLogin(JPanel panel) {
            panel.setLayout(new GridLayout(3, 2));

            JLabel userLabel = new JLabel("Username:");
            JTextField userText = new JTextField();
            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordText = new JPasswordField();
            JButton loginButton = new JButton("Login");

            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String username = userText.getText();
                    String password = new String(passwordText.getPassword());
                }
            });

            panel.add(userLabel);
            panel.add(userText);
            panel.add(passwordLabel);
            panel.add(passwordText);
            panel.add(loginButton);
        }

        private void placeComponentsSignup(JPanel panel) {
            panel.setLayout(new GridLayout(5, 2));

            JLabel nameLabel = new JLabel("Full Name:");
            JTextField nameText = new JTextField();
            JLabel emailLabel = new JLabel("Email:");
            JTextField emailText = new JTextField();
            JLabel passwordLabel = new JLabel("Password:");
            JPasswordField passwordText = new JPasswordField();
            JLabel phoneLabel = new JLabel("Phone:");
            JTextField phoneText = new JTextField();
            JButton createAccountButton = new JButton("Create Account");

            createAccountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = nameText.getText();
                    String email = emailText.getText();
                    String password = new String(passwordText.getPassword());
                    String phone = phoneText.getText();
                    try {
                        insertUserToDatabase(name, email, password, phone);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(userinterface.this, "Error creating user account.");
                    }
                }
            });

            panel.add(nameLabel);
            panel.add(nameText);
            panel.add(emailLabel);
            panel.add(emailText);
            panel.add(passwordLabel);
            panel.add(passwordText);
            panel.add(phoneLabel);
            panel.add(phoneText);
            panel.add(createAccountButton);
        }

        private void insertUserToDatabase(String name, String email, String password, String phone)
                throws SQLException {
            Connection conn = null;
            PreparedStatement preparedStatement = null;

            String sql = "INSERT INTO Users (Name, Email, Password, Phone) VALUES (?, ?, ?, ?)";
            try {
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
                preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, phone);

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    JOptionPane.showMessageDialog(this, "User added successfully!");
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }
        }
    }

    class Update extends JFrame {
        private JLabel jLabel1, jLabel2, jLabel3;
        private JButton jButton1, jButton2;
        private JPasswordField jPasswordField1, jPasswordField2;

        public Update() {
            setTitle("Update Password");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 300);
            setLocationRelativeTo(null);
            initComponents();
        }

        private void initComponents() {
            jLabel1 = new JLabel("Update Password");
            jLabel2 = new JLabel("Enter new password");
            jLabel3 = new JLabel("Confirm new password");

            jButton1 = new JButton("Confirm Update");
            jButton2 = new JButton("Back");

            jPasswordField1 = new JPasswordField(20);
            jPasswordField2 = new JPasswordField(20);

            jButton1.addActionListener(this::jButton1ActionPerformed);
            jButton2.addActionListener(e -> this.dispose());

            JPanel panel = new JPanel(new GridLayout(4, 2));
            panel.add(jLabel1);
            panel.add(new JLabel(""));
            panel.add(jLabel2);
            panel.add(jPasswordField1);
            panel.add(jLabel3);
            panel.add(jPasswordField2);
            panel.add(jButton1);
            panel.add(jButton2);

            add(panel);
        }

        private void jButton1ActionPerformed(ActionEvent evt) {
            String pass = new String(jPasswordField1.getPassword());
            String cpass = new String(jPasswordField2.getPassword());

            if (pass.isEmpty() || cpass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields can't be empty");
                jPasswordField1.setText("");
                jPasswordField2.setText("");
            } else if (!pass.equals(cpass)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match");
            } else {
                updatePassword(pass);
            }
        }

        private void updatePassword(String newPassword) {
            String url = "jdbc:mysql://localhost:3306/MTG";
            String username = "root";
            String password = "System";

            try (Connection conn = DriverManager.getConnection(url, username, password);
                    PreparedStatement stmt = conn
                            .prepareStatement("UPDATE Users SET Password = ? WHERE Username = ?")) {
                stmt.setString(1, newPassword);
                stmt.setString(2, "CurrentUsername");

                int affected = stmt.executeUpdate();
                if (affected > 0) {
                    JOptionPane.showMessageDialog(this, "Password updated successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update password.");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
            }
        }
    }
    // end of login

    class PlannerApp extends JFrame {
        MainApplication mainApp;

        public PlannerApp(MainApplication mainApp) {
            this.mainApp = mainApp;
            setTitle("My Tour Guide ");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new FlowLayout());
            JLabel welcomeLabel = new JLabel("Welcome to our planner");
            JButton startButton = new JButton("Start Homepage");

            ImageIcon logoIcon = new ImageIcon("logo.jpeg");
            Image image = logoIcon.getImage();
            Image newimg = image.getScaledInstance(100, 100, java.awt.Image.SCALE_DEFAULT);
            logoIcon = new ImageIcon(newimg);

            JLabel logoLabel = new JLabel(logoIcon);

            startButton.addActionListener(e -> {
                mainApp.showHomepage();
                dispose();
            });
            add(logoLabel);

            add(welcomeLabel);
            add(startButton);
        }
    }

    class Homepage extends JFrame {
        MainApplication mainApp;

        public Homepage(MainApplication mainApp) {
            this.mainApp = mainApp;
            setTitle("Homepage");
            setSize(400, 250);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridLayout(0, 2));
            add(mainPanel, BorderLayout.CENTER);

            JPanel leftPanel = new JPanel(new GridLayout(5, 1));
            mainPanel.add(leftPanel, BorderLayout.WEST);

            JPanel rightPanel = new JPanel(new GridLayout(5, 1));
            mainPanel.add(rightPanel, BorderLayout.EAST);

            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            mainPanel.add(centerPanel, BorderLayout.PAGE_END);

            JButton newTripButton = new JButton("New Trip");
            newTripButton.addActionListener(e -> {
                TripGUI tripGUI = new TripGUI();
                tripGUI.setVisible(true);
            });

            JButton infoButton = new JButton("My Info");
            infoButton.addActionListener(e -> {
                DatabaseManagerGUI infoGUI = new DatabaseManagerGUI();
                infoGUI.setVisible(true);
            });

            JButton ordersButton = new JButton("Orders");
            ordersButton.addActionListener(e -> {
                OrderQueryGUI orderGUI = new OrderQueryGUI();
                orderGUI.setVisible(true);
            });

            JButton imageButton = new JButton("Images");
            imageButton.addActionListener(e -> {
                ImageBrowser imageBrowser = new ImageBrowser();
                imageBrowser.setVisible(true);
            });

            JButton tripButton = new JButton("Trip");
            tripButton.addActionListener(e -> {
                TripDisplayGUI tripQueryManager = new TripDisplayGUI();
                tripQueryManager.setVisible(true);
            });

            leftPanel.add(newTripButton);
            leftPanel.add(infoButton);
            leftPanel.add(ordersButton);
            leftPanel.add(imageButton);
            leftPanel.add(tripButton);

            // Right Panel Buttons
            JButton priceButton = new JButton("Price Menu");
            priceButton.addActionListener(e -> {
                TourGuiApp priceTable = new TourGuiApp();
                priceTable.setVisible(true);
            });

            JButton offersButton = new JButton("Offers");
            offersButton.addActionListener(e -> {
                DiscountGUI discountGUI = new DiscountGUI();
                discountGUI.setVisible(true);
            });

            JButton carsButton = new JButton("Transaction");
            carsButton.addActionListener(e -> {
                DeliveryQueryGUI transactionGUI = new DeliveryQueryGUI();
                transactionGUI.setVisible(true);
            });

            JButton passwButton = new JButton("Update PW");
            passwButton.addActionListener(e -> {
                Update updatePasswordGUI = new Update();
                updatePasswordGUI.setVisible(true);
            });

            JButton helpButton = new JButton("Help");
            helpButton.addActionListener(e -> {
                ITIssueLoggerGUI itIssueLoggerGUI = new ITIssueLoggerGUI();
                itIssueLoggerGUI.setVisible(true);
            });

            rightPanel.add(priceButton);
            rightPanel.add(offersButton);
            rightPanel.add(carsButton);
            rightPanel.add(passwButton);
            rightPanel.add(helpButton);

            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> {
                dispose();
            });
            JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            logoutPanel.add(logoutButton);
            add(logoutPanel, BorderLayout.PAGE_END);
        }
    }

    // all user verification classes

    // GUI PART

    // Booking step(asayel+norah+batool+lama)
    // Asayel Gui

    class TripGUI extends JFrame {

        private JButton saveButton, backButton;
        private JComboBox<String> cityComboBox;
        private JLabel selectCityLabel, departOnLabel, adultsLabel, childrenLabel, infantsLabel;
        private JSpinner adultsSpinner, childrenSpinner, infantsSpinner;
        private JTextField dayField, monthField, yearField;

        public TripGUI() {
            initComponents();
            setupLayout();
            setupListeners();
        }

        private void initComponents() {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Booking tourism");

            selectCityLabel = new JLabel("Select City:");
            departOnLabel = new JLabel("Depart on:");
            adultsLabel = new JLabel("Adults (>12 yrs):");
            childrenLabel = new JLabel("Children (2-11 yrs):");
            infantsLabel = new JLabel("Infants (<2 yrs):");

            String[] cities = { "Abha", "Alula", "Eastern Province", "Jeddah", "Riyadh" };
            cityComboBox = new JComboBox<>(cities);

            dayField = new JTextField("DD", 2);
            monthField = new JTextField("MM", 2);
            yearField = new JTextField("YYYY", 4);

            adultsSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
            childrenSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
            infantsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));

            saveButton = new JButton("Save");
            backButton = new JButton("Back");
        }

        private void setupLayout() {
            GroupLayout layout = new GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            layout.setHorizontalGroup(
                    layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(selectCityLabel)
                                    .addComponent(cityComboBox, GroupLayout.PREFERRED_SIZE, 150,
                                            GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(departOnLabel)
                                    .addGroup(layout.createSequentialGroup()
                                            .addComponent(dayField, GroupLayout.PREFERRED_SIZE, 50,
                                                    GroupLayout.PREFERRED_SIZE)
                                            .addComponent(monthField, GroupLayout.PREFERRED_SIZE, 50,
                                                    GroupLayout.PREFERRED_SIZE)
                                            .addComponent(yearField, GroupLayout.PREFERRED_SIZE, 50,
                                                    GroupLayout.PREFERRED_SIZE))
                                    .addComponent(adultsLabel)
                                    .addComponent(adultsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addComponent(childrenLabel)
                                    .addComponent(childrenSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addComponent(infantsLabel)
                                    .addComponent(infantsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addComponent(saveButton))
                            .addComponent(backButton));

            layout.setVerticalGroup(
                    layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(selectCityLabel)
                                    .addComponent(departOnLabel))
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(cityComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addComponent(dayField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addComponent(monthField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE)
                                    .addComponent(yearField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                            GroupLayout.PREFERRED_SIZE))
                            .addComponent(adultsLabel)
                            .addComponent(adultsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addComponent(childrenLabel)
                            .addComponent(childrenSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addComponent(infantsLabel)
                            .addComponent(infantsSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                    GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(saveButton)
                                    .addComponent(backButton)));

            pack();
            setLocationRelativeTo(null); // Center on screen
        }

        private void setupListeners() {
            cityComboBox.addActionListener(e -> {
                String selectedCity = (String) cityComboBox.getSelectedItem();
                showCityGuide(selectedCity);
            });

            saveButton.addActionListener(this::onSave);
            backButton.addActionListener(e -> dispose());
        }

        private void onSave(ActionEvent evt) {
            try {
                String city = (String) cityComboBox.getSelectedItem();
                int day = Integer.parseInt(dayField.getText().trim());
                int month = Integer.parseInt(monthField.getText().trim());
                int year = Integer.parseInt(yearField.getText().trim());

                LocalDate startDate;
                try {
                    startDate = LocalDate.of(year, month, day);
                    if (startDate.isBefore(LocalDate.now())) {
                        JOptionPane.showMessageDialog(this, "The date must not be in the past.", "Date Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (DateTimeException e) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid future date.", "Date Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int adults = (int) adultsSpinner.getValue();
                int children = (int) childrenSpinner.getValue();
                int infants = (int) infantsSpinner.getValue();

                if (adults < 0 || children < 0 || infants < 0) {
                    JOptionPane.showMessageDialog(this, "Number of adults, children, and infants must be non-negative.",
                            "Number Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                openPersonalInformationGui(adults, children, infants);
                JOptionPane.showMessageDialog(this,
                        "Booking details saved successfully! Please fill in personal information.", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please check your input values. Numbers are expected for day, month, year, adults, children, and infants.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void openPersonalInformationGui(int adults, int children, int infants) {
            for (int i = 0; i < adults; i++) {
                new PersonalInformationGUI("Adult " + (i + 1)).setVisible(true);
            }
            for (int i = 0; i < children; i++) {
                new PersonalInformationGUI("Child " + (i + 1)).setVisible(true);
            }
            for (int i = 0; i < infants; i++) {
                new PersonalInformationGUI("Infant " + (i + 1)).setVisible(true);
            }
        }

        private void showCityGuide(String city) {
            JFrame cityGuide = null;

            switch (city) {
                case "Abha":
                    cityGuide = new AbhaGUI();
                    cityGuide.setVisible(true);
                    break;
                case "Alula":
                    cityGuide = new AlulaGUI();
                    cityGuide.setVisible(true);
                    break;
                case "Eastern Province":
                    cityGuide = new EasternProvinceGUI();
                    cityGuide.setVisible(true);
                    break;
                case "Jeddah":
                    cityGuide = new JeddahGUI();
                    cityGuide.setVisible(true);
                    break;
                case "Riyadh":
                    cityGuide = new RiyadhGUI();
                    cityGuide.setVisible(true);
                    break;
            }
        }
    }

    // Norah gui's
    class RiyadhGUI extends JFrame {

        private JLabel titleLabel;
        private JTable activityTable;
        private JTable shoppingTable;
        private JTextArea safetyTipsTextArea;

        public RiyadhGUI() {
            setTitle("Riyadh City Guide");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            titleLabel = new JLabel("Riyadh City Guide");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            String[] activityColumnNames = { "Day", "Activities" };
            Object[][] activityData = {
                    { "Day 1",
                            "1-Breakfast(hotel)\n 2-Kingdom Suspension Bridge\n 3-Riyadh Park\n 4-Beef Bar Restaurant" },
                    { "Day 2", "1-Lovefer Branch\n 2-Cinema (Boulevard)\n 3-Meraki Restuarant" },
                    { "Day 3", "Free Day" },
                    { "Day 4", "1-Rishshaw London Branch\n 2-Winter Wonderland\n 3-PizzaBar Restaurant" },
                    { "Day 5", "1-Breakfast(hotel)\n 2-Al-Nakheel Mall\n3-Oia Restuarant" },
                    { "Day 6", "1-Easy Bakery Branch\n 2-Dalila Camp Event" },
                    { "Day 7", "1-Breakfast(hotel)\n 2-KingAbdullah Financial District\n3-Al-Nakheel Mall" }
            };
            activityTable = new JTable(activityData, activityColumnNames);
            JScrollPane activityScrollPane = new JScrollPane(activityTable);
            String[] shoppingColumnNames = { "Day", "Shopping" };
            Object[][] shoppingData = {
                    { "Day 1", "1-F60r Branch\n 2-KingAbdullah Park\n 3-Riyadh Front" },
                    { "Day 2", "1-Breakfast(hotel)\n 2-Winter Wonderland\n 3-Sign Restuarant" },
                    { "Day 3", "1-Breakfast(hotel)\n 2-Historic Murabba Palace\n 3-Roor Restuarant" },
                    { "Day 4", "1-Arkmi Restuarant\n 2-Al-Nakheel Mall\n 3-Riyadh Zoo\n 4-Roasted Way Restuarant" },
                    { "Day 5", "Free Day" },
                    { "Day 6", "1-Salam Park\n 2-Suspension Bridge\n 3-Urban Restuarant" },
                    { "Day 7", "\n1-KingAbdullah Financial Districtn\n2- Kingdom Suspension Bridge" }
            };
            shoppingTable = new JTable(shoppingData, shoppingColumnNames);
            JScrollPane shoppingScrollPane = new JScrollPane(shoppingTable);
            safetyTipsTextArea = new JTextArea();
            safetyTipsTextArea.setEditable(false);
            safetyTipsTextArea.setText("Safety Tips:\n" +
                    "- Be cautious of your belongings in crowded areas.\n" +
                    "- Dress modestly and respect local customs.\n" +
                    "- Drink plenty of water to stay hydrated, especially during hot weather.");
            JButton modifyButton = new JButton("Rotate Schedule");
            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to rotate the schedule?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        rotateSchedule(activityData, shoppingData);
                        activityTable.repaint();
                        shoppingTable.repaint();
                        JOptionPane.showMessageDialog(null, "Schedule rotated successfully!");
                    }
                }
            });

            JPanel mainPanel = new JPanel(new GridLayout(4, 1));
            mainPanel.add(titleLabel);
            mainPanel.add(activityScrollPane);
            mainPanel.add(shoppingScrollPane);
            mainPanel.add(modifyButton);

            getContentPane().add(mainPanel, BorderLayout.CENTER);
            getContentPane().add(safetyTipsTextArea, BorderLayout.SOUTH);
        }

        private void rotateSchedule(Object[][] activityData, Object[][] shoppingData) {
            Object[] tempActivity = activityData[activityData.length - 1];
            System.arraycopy(activityData, 0, activityData, 1, activityData.length - 1);
            activityData[0] = tempActivity;
            Object[] tempShopping = shoppingData[shoppingData.length - 1];
            System.arraycopy(shoppingData, 0, shoppingData, 1, shoppingData.length - 1);
            shoppingData[0] = tempShopping;
        }

    }

    class JeddahGUI extends JFrame {
        private JLabel titleLabel;
        private JTable activityTable;
        private JTable shoppingTable;
        private JTextArea safetyTipsTextArea;

        public JeddahGUI() {
            setTitle("Jeddah City Guide");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            titleLabel = new JLabel("Jeddah City Guide");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            String[] activityColumnNames = { "Day", "Activities" };
            Object[][] activityData = {
                    { "Day 1",
                            "1-Caffeine Lab Branch\n 2-BROOTS Coffee & Cocoaln\n 3-ALshallal\n 4-Atallah Happy Land ParkIn" },
                    { "Day 2", "1-Al-Tayebat international city\n 2-Maqadeer Restuarant\n3-Fakieh Aquarium" },
                    { "Day 3", "Free Day" },
                    { "Day 4", "1-THE YEMENI VILLAE\n 2-Thoul BeachIn\n 3-Tayebat internatio" },
                    { "Day 5",
                            "1-American Corner Restuarant\n 2-Zillion Restuarant\n 3-San Carlo Cicchetti Restuarant" },
                    { "Day 6", "1-San Carlo Cicchetti Restuarant\n 2-Hemi Cafe & Roastrey " },
                    { "Day 7", "1-Breakfast(hotel)\n2-KingAbdullah Financial District\n 3-Zillion Restuarant" }
            };
            activityTable = new JTable(activityData, activityColumnNames);
            JScrollPane activityScrollPane = new JScrollPane(activityTable);
            String[] shoppingColumnNames = { "Day", "Shopping" };
            Object[][] shoppingData = {
                    { "Day 1", "1-Mall of Arabia\n 2-Serafi Mega Mall\n 3-Aziz Mall" },
                    { "Day 2", "1-Aziz Mall\n 2-Le Chateau Mall\n 3-Boulevard" },
                    { "Day 3", "1-El Khayyat Center\n 2-Ana Special Mall\n 3-Haifaa Mall" },
                    { "Day 4", "1-Stars Avenue Mall\n 2-Andalus Mall\n 3-Jeddahmall\n 4-Al Salaam Mall" },
                    { "Day 5", "Free Day" },
                    { "Day 6", "1-Roshan Mall\n 2-Flamengo Park\n 3-Jeddah Park" },
                    { "Day 7", "\n1-Yasmin Mall" }
            };
            shoppingTable = new JTable(shoppingData, shoppingColumnNames);
            JScrollPane shoppingScrollPane = new JScrollPane(shoppingTable);
            safetyTipsTextArea = new JTextArea();
            safetyTipsTextArea.setEditable(false);
            safetyTipsTextArea.setText("Safety Tips:\n" +
                    "- Be cautious of your belongings in crowded areas.\n" +
                    "- Dress modestly and respect local customs.\n" +
                    "- Drink plenty of water to stay hydrated, especially during hot weather.");

            JButton modifyButton = new JButton("Rotate Schedule");
            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to rotate the schedule?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        rotateSchedule(activityData, shoppingData);
                        activityTable.repaint();
                        shoppingTable.repaint();
                        JOptionPane.showMessageDialog(null, "Schedule rotated successfully!");
                    }
                }
            });

            JPanel mainPanel = new JPanel(new GridLayout(4, 1));
            mainPanel.add(titleLabel);
            mainPanel.add(activityScrollPane);
            mainPanel.add(shoppingScrollPane);
            mainPanel.add(modifyButton);

            getContentPane().add(mainPanel, BorderLayout.CENTER);
            getContentPane().add(safetyTipsTextArea, BorderLayout.SOUTH);
        }

        private void rotateSchedule(Object[][] activityData, Object[][] shoppingData) {
            Object[] tempActivity = activityData[activityData.length - 1];
            System.arraycopy(activityData, 0, activityData, 1, activityData.length - 1);
            activityData[0] = tempActivity;
            Object[] tempShopping = shoppingData[shoppingData.length - 1];
            System.arraycopy(shoppingData, 0, shoppingData, 1, shoppingData.length - 1);
            shoppingData[0] = tempShopping;
        }

    }

    class AlulaGUI extends JFrame {
        private JLabel titleLabel;
        private JTable activityTable;
        private JTable shoppingTable;
        private JTextArea safetyTipsTextArea;

        public AlulaGUI() {
            setTitle("Alula City Guide");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            titleLabel = new JLabel("Alula City Guide");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            String[] activityColumnNames = { "Day", "Activities" };
            Object[][] activityData = {
                    { "Day 1", "1-The old city of AlUla\n 2-AlUla Museum\n 3-Go to the hotel (AlUla Mirrors)" },
                    { "Day 2", "1-Have dinner at Al Diwan Restaurant\n 2-Madain Saleh\n 3-AlUla Oasis" },
                    { "Day 3", "Free Day" },
                    { "Day 4",
                            "1-Al Manara Restaurant\n 2-Elephant Rock: Take a tour to see the unique rock formation that resembles an elephant" },
                    { "Day 5", "1-Have dinner at Al Khaira Restaurant\n 2-Old railway station" },
                    { "Day 6", "1-Enjoy activities like horse riding\n 2-Fayrouz Restaurant " },
                    { "Day 7",
                            "1-Go to AlUlA Desert Resort\n 2-KingAbdullah Financial District\n 3-Zillion Restuarant" }
            };
            activityTable = new JTable(activityData, activityColumnNames);
            JScrollPane activityScrollPane = new JScrollPane(activityTable);
            safetyTipsTextArea = new JTextArea();
            safetyTipsTextArea.setEditable(false);
            safetyTipsTextArea.setText("Safety Tips:\n" +
                    "- Be cautious of your belongings in crowded areas.\n" +
                    "- Dress modestly and respect local customs.\n" +
                    "- Drink plenty of water to stay hydrated, especially during hot weather.");
            JButton modifyButton = new JButton("Rotate Schedule");
            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to rotate the schedule?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        rotateSchedule(activityData, null);
                        activityTable.repaint();

                        JOptionPane.showMessageDialog(null, "Schedule rotated successfully!");
                    }
                }
            });

            JPanel mainPanel = new JPanel(new GridLayout(4, 1));
            mainPanel.add(titleLabel);
            mainPanel.add(activityScrollPane);
            mainPanel.add(modifyButton);
            getContentPane().add(mainPanel, BorderLayout.CENTER);
            getContentPane().add(safetyTipsTextArea, BorderLayout.SOUTH);
        }

        private void rotateSchedule(Object[][] activityData, Object[][] shoppingData) {
            Object[] tempActivity = activityData[activityData.length - 1];
            System.arraycopy(activityData, 0, activityData, 1, activityData.length - 1);
            activityData[0] = tempActivity;
        }

    }

    class AbhaGUI extends JFrame {
        private JLabel titleLabel;
        private JTable activityTable;
        private JTable shoppingTable;
        private JTextArea safetyTipsTextArea;

        public AbhaGUI() {
            setTitle("Abha City Guide");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            titleLabel = new JLabel("Abha City Guide");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            String[] activityColumnNames = { "Day", "Activities" };
            Object[][] activityData = {
                    { "Day 1", "1-Verde Restuarant \n 2-Abu Kheyal\n 3-Giorno Coffe" },
                    { "Day 2", "1-Le Voyage Restuarant\n 2-Aya Sofy\n 3-VotelSt" },
                    { "Day 3", "Free Day" },
                    { "Day 4", "1-View Cafe\n 2-Al-Sahab Park\n 3-Black Box Cafe" },
                    { "Day 5", "1-Ala Bali Restuarant\n 2-Damside Park\n 3-The Dabbab Walkway" },
                    { "Day 6", "1-Tonir Restuarant\n 2-The Dabbab Walkway " },
                    { "Day 7", "1-Will Cafe\n 2-VotelSt\n 3-Giorno Coffe" }
            };
            activityTable = new JTable(activityData, activityColumnNames);
            JScrollPane activityScrollPane = new JScrollPane(activityTable);
            String[] shoppingColumnNames = { "Day", "Shopping" };
            Object[][] shoppingData = {
                    { "Day 1", "Aseer Mall\n" },
                    { "Day 2", "Rihanna Mall\n" },
                    { "Day 3", "Al Nakheel " },
                    { "Day 4", "Abha Mall\n " },
                    { "Day 5", "Free Day" },
                    { "Day 6", "Ravala  Plaza\n" },
                    { "Day 7", "Al Rashid Mall" }
            };
            shoppingTable = new JTable(shoppingData, shoppingColumnNames);
            JScrollPane shoppingScrollPane = new JScrollPane(shoppingTable);
            safetyTipsTextArea = new JTextArea();
            safetyTipsTextArea.setEditable(false);
            safetyTipsTextArea.setText("Safety Tips:\n" +
                    "- Be cautious of your belongings in crowded areas.\n" +
                    "- Dress modestly and respect local customs.\n" +
                    "- Drink plenty of water to stay hydrated, especially during hot weather.");
            JButton modifyButton = new JButton("Rotate Schedule");
            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to rotate the schedule?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        rotateSchedule(activityData, shoppingData);
                        activityTable.repaint();
                        shoppingTable.repaint();
                        JOptionPane.showMessageDialog(null, "Schedule rotated successfully!");
                    }
                }
            });

            JPanel mainPanel = new JPanel(new GridLayout(4, 1));
            mainPanel.add(titleLabel);
            mainPanel.add(activityScrollPane);
            mainPanel.add(shoppingScrollPane);
            mainPanel.add(modifyButton);

            getContentPane().add(mainPanel, BorderLayout.CENTER);
            getContentPane().add(safetyTipsTextArea, BorderLayout.SOUTH);
        }

        private void rotateSchedule(Object[][] activityData, Object[][] shoppingData) {
            Object[] tempActivity = activityData[activityData.length - 1];
            System.arraycopy(activityData, 0, activityData, 1, activityData.length - 1);
            activityData[0] = tempActivity;
            Object[] tempShopping = shoppingData[shoppingData.length - 1];
            System.arraycopy(shoppingData, 0, shoppingData, 1, shoppingData.length - 1);
            shoppingData[0] = tempShopping;
        }
    }

    class EasternProvinceGUI extends JFrame {
        private JLabel titleLabel;
        private JTable activityTable;
        private JTable shoppingTable;
        private JTextArea safetyTipsTextArea;

        public EasternProvinceGUI() {
            setTitle("EasternProvince Guide");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 600);
            titleLabel = new JLabel("EasternProvince Guide");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);

            String[] activityColumnNames = { "Day", "Activities" };
            Object[][] activityData = {
                    { "Day 1",
                            "1-Arrival to Dammam Airport and entry to the (Sheraton Hotel)\n 2-Kingdom Suspension Bridge\n 3-Have breakfast at the hotel\n 4-Have lunch at( Lumiere) Restaurant" },
                    { "Day 2",
                            "1-Have breakfast at the hotel\n 2-Go to( Paul Gardenia) Resort in Al Khobar and spend the day there\n 3-Meraki Restuarant" },
                    { "Day 3", "Free Day" },
                    { "Day 4",
                            "1-Have breakfast at (Solas )Restaurant\n 2-Ithra visit\n 3-Watch a movie at (Ajdan Walk Cinema)" },
                    { "Day 5", "1-Have breakfast at the hotel\n 2-Have dinner and cofe at (City Walk)" },
                    { "Day 6",
                            "1-Have lunch at (Parkers) Restaurant\n 2-Go to Deghaithir Island and enjoy the food and scenery" },
                    { "Day 7",
                            "1-Breakfast(hotel)\n 2-Have dinner at (Miso) Restaurant\n 3-Visit (Loupage) Water Games" }
            };
            activityTable = new JTable(activityData, activityColumnNames);
            JScrollPane activityScrollPane = new JScrollPane(activityTable);
            String[] shoppingColumnNames = { "Day", "Shopping" };
            Object[][] shoppingData = {
                    { "Day 1", "Al Rashid Mall" },
                    { "Day 2", "Dareen Mall Dammam " },
                    { "Day 3", "Beach Mall Dammam or Marina Mall Dammam" },
                    { "Day 4", "Al Khobar Mall Center" },
                    { "Day 5", "Free Day" },
                    { "Day 6", "Dhahran Mall" },
                    { "Day 7", "Nakheel Mall Dammam" }
            };
            shoppingTable = new JTable(shoppingData, shoppingColumnNames);
            JScrollPane shoppingScrollPane = new JScrollPane(shoppingTable);
            safetyTipsTextArea = new JTextArea();
            safetyTipsTextArea.setEditable(false);
            safetyTipsTextArea.setText("Safety Tips:\n" +
                    "- Be cautious of your belongings in crowded areas.\n" +
                    "- Dress modestly and respect local customs.\n" +
                    "- Drink plenty of water to stay hydrated, especially during hot weather.");
            JButton modifyButton = new JButton("Rotate Schedule");
            modifyButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to rotate the schedule?", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (confirmed == JOptionPane.YES_OPTION) {
                        rotateSchedule(activityData, shoppingData);
                        activityTable.repaint();
                        shoppingTable.repaint();
                        JOptionPane.showMessageDialog(null, "Schedule rotated successfully!");
                    }
                }
            });
            JPanel mainPanel = new JPanel(new GridLayout(4, 1));
            mainPanel.add(titleLabel);
            mainPanel.add(activityScrollPane);
            mainPanel.add(shoppingScrollPane);
            mainPanel.add(modifyButton);
            getContentPane().add(mainPanel, BorderLayout.CENTER);
            getContentPane().add(safetyTipsTextArea, BorderLayout.SOUTH);
        }

        private void rotateSchedule(Object[][] activityData, Object[][] shoppingData) {
            Object[] tempActivity = activityData[activityData.length - 1];
            System.arraycopy(activityData, 0, activityData, 1, activityData.length - 1);
            activityData[0] = tempActivity;
            Object[] tempShopping = shoppingData[shoppingData.length - 1];
            System.arraycopy(shoppingData, 0, shoppingData, 1, shoppingData.length - 1);
            shoppingData[0] = tempShopping;
        }

    }

    // Batool gui
    class PersonalInformationGUI extends JFrame {
        private JLabel nameLabel, ageLabel, genderLabel, idLabel, birthDateLabel;
        private JTextField nameField, ageField, idField, birthDateField;
        private JRadioButton maleButton, femaleButton;
        private ButtonGroup genderGroup;
        private JButton submitButton;

        public PersonalInformationGUI(String titleSuffix) {
            super("Personal Information - " + titleSuffix);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

            setupUIComponents();
            pack();
            setVisible(true);
        }

        private void setupUIComponents() {
            JPanel formPanel = new JPanel();
            formPanel.setLayout(new GridLayout(0, 2, 10, 10));

            nameLabel = new JLabel("Name:");
            nameField = new JTextField(20);
            formPanel.add(nameLabel);
            formPanel.add(nameField);

            ageLabel = new JLabel("Age:");
            ageField = new JTextField(20);
            formPanel.add(ageLabel);
            formPanel.add(ageField);

            idLabel = new JLabel("ID:");
            idField = new JTextField(20);
            formPanel.add(idLabel);
            formPanel.add(idField);

            birthDateLabel = new JLabel("Birth Date (yyyy-mm-dd):");
            birthDateField = new JTextField(10);
            formPanel.add(birthDateLabel);
            formPanel.add(birthDateField);

            genderLabel = new JLabel("Gender:");
            setupGenderRadioButtons();
            JPanel genderPanel = new JPanel();
            genderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            genderPanel.add(maleButton);
            genderPanel.add(femaleButton);
            formPanel.add(genderLabel);
            formPanel.add(genderPanel);

            submitButton = new JButton("Submit");
            submitButton.addActionListener(new SubmitButtonListener());
            formPanel.add(new JLabel()); // Placeholder
            formPanel.add(submitButton);

            getContentPane().add(formPanel);
        }

        private void setupGenderRadioButtons() {
            maleButton = new JRadioButton("Male", true);
            femaleButton = new JRadioButton("Female");
            genderGroup = new ButtonGroup();
            genderGroup.add(maleButton);
            genderGroup.add(femaleButton);
        }

        private class SubmitButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String id = idField.getText();
                    LocalDate birthDate = LocalDate.parse(birthDateField.getText());
                    String gender = maleButton.isSelected() ? "Male" : "Female";

                    // Assuming the Person class is used to store personal data
                    Person person = new Person(name, age, id, gender, birthDate);
                    System.out.println("Person Information Collected: " + person);

                    // Open the billing window
                    openBillingGUI();

                    // Dispose the current GUI
                    dispose();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(PersonalInformationGUI.this, "Invalid birth date format.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PersonalInformationGUI.this, "Invalid number format.");
                }
            }
        }

        // Method to open the Billing GUI
        private void openBillingGUI() {
            new billing().setVisible(true); // Ensure the 'billing' class is correctly capitalized if needed
        }

        // Assuming the Person class is here or imported if it's located in another file
        class Person {
            private String name;
            private int age;
            private String id;
            private String gender;
            private LocalDate birthDate;

            public Person(String name, int age, String id, String gender, LocalDate birthDate) {
                this.name = name;
                this.age = age;
                this.id = id;
                this.gender = gender;
                this.birthDate = birthDate;
            }

            @Override
            public String toString() {
                return "Person{" +
                        "name='" + name + '\'' +
                        ", age=" + age +
                        ", id='" + id + '\'' +
                        ", gender='" + gender + '\'' +
                        ", birthDate=" + birthDate +
                        '}';
            }
        }
    }

    // Payment step (haifa + atheer)

    // haifa
    class billing extends JFrame {
        private JTextField totalField;

        public billing() {
            setTitle("Billing");
            setSize(700, 500);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLayout(new BorderLayout());

            Font labelFont = new Font("Arial", Font.BOLD, 12);
            Font textFont = new Font("Arial", Font.PLAIN, 12);

            JPanel northPanel = new JPanel(new GridLayout(1, 2));
            JPanel centerPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            JPanel southPanel = new JPanel();

            JLabel orIdLabel = new JLabel("OR ID:");
            orIdLabel.setFont(labelFont);
            JTextField orIdField = new JTextField(generateOrderId(), 10);
            orIdField.setFont(textFont);
            northPanel.add(orIdLabel);
            northPanel.add(orIdField);

            JLabel clientDetailsLabel = new JLabel("Order Details");
            clientDetailsLabel.setFont(labelFont);
            centerPanel.add(clientDetailsLabel);
            centerPanel.add(new JPanel()); // Blank panel for alignment

            addClientDetail("Firstname", centerPanel, labelFont, textFont);
            addClientDetail("Lastname", centerPanel, labelFont, textFont);
            addClientDetail("Address", centerPanel, labelFont, textFont);
            addClientDetail("Contact", centerPanel, labelFont, textFont);

            addClientDetail("Bill Date", centerPanel, labelFont, textFont, LocalDate.now().toString());
            addClientDetail("Bill ID", centerPanel, labelFont, textFont, generateRandomBillId());

            int randomSubtotal = generateRandomSubtotal();
            JTextField subtotalField = addClientDetail("Subtotal", centerPanel, labelFont, textFont,
                    String.valueOf(randomSubtotal));
            totalField = addClientDetail("TOTAL", centerPanel, labelFont, textFont, calculateTotal(randomSubtotal));

            JButton addButton = new JButton("Add");
            addButton.addActionListener(this::openPayment);
            JButton cancelButton = new JButton("Cancel");

            JLabel cashierLabel = new JLabel("Make Sure that Your billing info is correct", JLabel.RIGHT);
            cashierLabel.setFont(labelFont);

            southPanel.add(addButton);
            southPanel.add(cancelButton);
            southPanel.add(cashierLabel);

            add(northPanel, BorderLayout.NORTH);
            add(centerPanel, BorderLayout.CENTER);
            add(southPanel, BorderLayout.SOUTH);

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private JTextField addClientDetail(String labelText, JPanel panel, Font labelFont, Font textFont) {
            JLabel label = new JLabel(labelText);
            label.setFont(labelFont);
            JTextField textField = new JTextField(15);
            textField.setFont(textFont);
            panel.add(label);
            panel.add(textField);
            return textField;
        }

        private JTextField addClientDetail(String labelText, JPanel panel, Font labelFont, Font textFont,
                String defaultValue) {
            JLabel label = new JLabel(labelText);
            label.setFont(labelFont);
            JTextField textField = new JTextField(defaultValue, 15);
            textField.setFont(textFont);
            panel.add(label);
            panel.add(textField);
            return textField;
        }

        private String generateOrderId() {
            Random random = new Random();
            return String.format("MTG %03d-%d", random.nextInt(1000), random.nextInt(10));
        }

        private String generateRandomBillId() {
            Random random = new Random();
            return String.format("%05d", random.nextInt(100000));
        }

        private int generateRandomSubtotal() {
            Random random = new Random();
            return 2500 + random.nextInt(3501);
        }

        private String calculateTotal(int subtotal) {
            double total = subtotal * 1.15;
            return String.format("%.2f", total);
        }

        private void openPayment(ActionEvent e) {
            double total = Double.parseDouble(totalField.getText());
            new Payment(total);
        }
    }

    // atheer
    class Payment extends JFrame {
        private JLabel paymentIdLabel, amountLabel, paymentMethodLabel;
        private JTextField paymentIdTextField, amountTextField;
        private JComboBox<String> paymentMethodComboBox;
        private JButton processPaymentButton, nextButton;
        private double amount;

        public Payment(double amount) {
            setTitle("Payment");
            setSize(400, 250);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 10));
            this.amount = amount;

            paymentIdLabel = new JLabel("Payment ID:");
            paymentIdTextField = new JTextField(10);
            amountLabel = new JLabel("Amount:");
            amountTextField = new JTextField(String.format("%.2f", this.amount), 10);
            amountTextField.setEditable(false);
            paymentMethodLabel = new JLabel("Payment Method:");
            paymentMethodComboBox = new JComboBox<>(new String[] { "Paypal", "MasterCard", "VisaCard", "Cash" });

            formPanel.add(paymentIdLabel);
            formPanel.add(paymentIdTextField);
            formPanel.add(amountLabel);
            formPanel.add(amountTextField);
            formPanel.add(paymentMethodLabel);
            formPanel.add(paymentMethodComboBox);

            processPaymentButton = new JButton("Process Payment");
            nextButton = new JButton("Next");
            nextButton.setEnabled(false);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(processPaymentButton);
            buttonPanel.add(nextButton);

            add(formPanel, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);

            processPaymentButton.addActionListener(this::processPayment);
            nextButton.addActionListener(this::openThankYouAndRating);
            setVisible(true);
        }

        private void processPayment(ActionEvent e) {
            if (paymentIdTextField.getText().isEmpty() || paymentMethodComboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Payment processed successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                nextButton.setEnabled(true);
                processPaymentButton.setEnabled(false);
            }
        }

        private void openThankYouAndRating(ActionEvent e) {
            new RatingGUI();
            dispose();
        }
    }

    // lama
    class RatingGUI extends JFrame {

        public RatingGUI() {
            setTitle("Rate Your Experience");
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

            JPanel ratingPanel = new JPanel();
            JLabel ratingLabel = new JLabel("Please rate your experience (1-5):");
            ratingPanel.add(ratingLabel);

            JRadioButton rating1 = new JRadioButton("1");
            JRadioButton rating2 = new JRadioButton("2");
            JRadioButton rating3 = new JRadioButton("3");
            JRadioButton rating4 = new JRadioButton("4");
            JRadioButton rating5 = new JRadioButton("5");
            ButtonGroup group = new ButtonGroup();
            group.add(rating1);
            group.add(rating2);
            group.add(rating3);
            group.add(rating4);
            group.add(rating5);

            ratingPanel.add(rating1);
            ratingPanel.add(rating2);
            ratingPanel.add(rating3);
            ratingPanel.add(rating4);
            ratingPanel.add(rating5);

            JPanel buttonPanel = new JPanel();
            JButton submitButton = new JButton("Submit Rating");
            submitButton.addActionListener(e -> submitRating(group));
            buttonPanel.add(submitButton);

            add(ratingPanel);
            add(buttonPanel);
            pack();
            setVisible(true);
        }

        private void submitRating(ButtonGroup group) {
            Enumeration<AbstractButton> buttons = group.getElements();
            while (buttons.hasMoreElements()) {
                AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Thank you for using my tour guide. Have a nice day!",
                            "Thank You", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Please select a rating before submitting.", "No Selection",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // lama
    static class ImageBrowser extends JFrame {
        private JLabel cityLabel;
        private JLabel imageLabel;
        private int currentCityIndex = 0;
        private ArrayList<String>[] cityImagePaths = new ArrayList[5];
        private String[] cityNames = { "Eastern Province", "Jeddah", "Riyadh", "Abha", "Alula" };

        public ImageBrowser() {
            setTitle("Image Browser");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            for (int i = 0; i < cityImagePaths.length; i++) {
                cityImagePaths[i] = new ArrayList<>();
            }

            loadCityImagePaths();

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.setLayout(new GridLayout(1, 2));

            JButton prevButton = new JButton("Previous City");
            prevButton.addActionListener(e -> showPreviousCity());

            JButton nextButton = new JButton("Next City");
            nextButton.addActionListener(e -> showNextCity());

            buttonPanel.add(prevButton);
            buttonPanel.add(nextButton);

            cityLabel = new JLabel();
            cityLabel.setHorizontalAlignment(JLabel.CENTER);
            showCityName();

            imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            showCityImages();

            JPanel labelPanel = new JPanel(new BorderLayout());
            labelPanel.add(cityLabel, BorderLayout.NORTH);
            labelPanel.add(imageLabel, BorderLayout.CENTER);

            add(buttonPanel, BorderLayout.NORTH);
            add(labelPanel, BorderLayout.CENTER);
        }

        private void loadCityImagePaths() {
            cityImagePaths[0].add("e.jpeg");
            cityImagePaths[0].add("ha.png");
            cityImagePaths[0].add("je.jpeg");
            cityImagePaths[1].add("j.jpeg");
            cityImagePaths[1].add("je.png");
            cityImagePaths[1].add("jedd.avif");
            cityImagePaths[2].add("Riyadh.jpeg");
            cityImagePaths[3].add("abha.jpeg");
            cityImagePaths[4].add("alula.jpeg");
        }

        private void showCityName() {
            String currentCity = cityNames[currentCityIndex];
            cityLabel.setText(currentCity);
        }

        private void showCityImages() {
            ArrayList<String> images = cityImagePaths[currentCityIndex];
            if (images.isEmpty()) {
                imageLabel.setText("No images available for this city.");
            } else {
                ImageIcon icon = new ImageIcon(images.get(0));
                Image scaledImage = icon.getImage().getScaledInstance(400, 300, Image.SCALE_DEFAULT);
                icon = new ImageIcon(scaledImage);
                imageLabel.setIcon(icon);
                imageLabel.setText(null);
            }
        }

        private void showNextCity() {
            currentCityIndex = (currentCityIndex + 1) % cityNames.length;
            showCityName();
            showCityImages();
        }

        private void showPreviousCity() {
            currentCityIndex = (currentCityIndex - 1 + cityNames.length) % cityNames.length;
            showCityName();
            showCityImages();
        }
    }

    // DATA PART

    // A-DATABASE

    // asayel
    static class DatabaseManagerGUI extends JFrame {
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
                    String additionalServices = rs.getString("TOUR_GUIDE_NAME") != null
                            ? rs.getString("TOUR_GUIDE_NAME")
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
                    PreparedStatement pstAdditionalServices = connection
                            .prepareStatement(insertAdditionalServicesQuery)) {
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

                tableModel
                        .addRow(new Object[] { firstName, lastName, gender.equals("M") ? "Male" : "Female", age, phone,
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

    // atheer
    static class DeliveryQueryGUI extends JFrame {

        public DeliveryQueryGUI() {
            createAndShowGUI();
        }

        private void createAndShowGUI() {
            setTitle("Delivery Query");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 400);

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

            add(new JScrollPane(table), BorderLayout.CENTER);
            add(southPanel, BorderLayout.SOUTH);
            loadDataFromDatabase(model);
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private Connection openDatabase() {
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

        private void closeDatabase(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private ResultSet executeQuery(Connection connection, String query) {
            try {
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void loadDataFromDatabase(DefaultTableModel model) {
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

    // norah
    static class OrderQueryGUI extends JFrame {

        public OrderQueryGUI() {
            createAndShowGUI();
        }

        private void createAndShowGUI() {
            setTitle("Order Query");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(700, 400);

            String[] columnNames = { "Trip ID", "City", "Start Date", "Days" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

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

            add(southPanel, BorderLayout.SOUTH);
            loadDataFromDatabase(model);

            setLocationRelativeTo(null);
            setVisible(true);
        }

        private Connection openDatabase() {
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

        private void closeDatabase(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private ResultSet executeQuery(Connection connection, String query) {
            try {
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void loadDataFromDatabase(DefaultTableModel model) {
            Connection connection = openDatabase();
            if (connection != null) {
                String query = "SELECT o.Trip_id, c.City_name, t.start_date, DATEDIFF(t.end_date, t.start_date) AS days "
                        +
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

    // haifa
    static class TicketGuiApp extends JFrame {

        public TicketGuiApp() {
            createAndShowGUI();
        }

        private void createAndShowGUI() {
            setTitle("Ticket");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 400);
            setLayout(new BorderLayout());

            String[] columnNames = { "ID", "Technician Name", "Specialization", "Issue Description", "Priority",
                    "Status" };

            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton backButton = new JButton("Back");
            buttonPanel.add(backButton);
            add(buttonPanel, BorderLayout.NORTH);

            String[] statuses = { "All", "Pending", "Confirmed", "Cancelled" };
            JComboBox<String> statusComboBox = new JComboBox<>(statuses);
            add(statusComboBox, BorderLayout.SOUTH);

            statusComboBox.addActionListener(e -> {
                String selectedStatus = (String) statusComboBox.getSelectedItem();
                filterTableByStatus(selectedStatus, model);
            });

            loadDataFromDatabase(model);
            setVisible(true);
        }

        private Connection openDatabase() {
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

        private void closeDatabase(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private ResultSet executeQuery(Connection connection, String query) {
            try {
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void loadDataFromDatabase(DefaultTableModel model) {
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

        private void filterTableByStatus(String status, DefaultTableModel model) {
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

    // lama
    static class TourGuiApp extends JFrame {

        public TourGuiApp() {
            createAndShowGUI();
        }

        private void createAndShowGUI() {
            setTitle("Tour Price Query");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(800, 400);
            setLayout(new BorderLayout());

            String[] columnNames = { "ID", "City", "Tour Name", "Language", "Cost" };

            DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (column == 4) {
                        return Integer.class;
                    }
                    return String.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton backButton = new JButton("Back");
            JButton refreshButton = new JButton("Filter by Cost");
            buttonPanel.add(backButton);
            buttonPanel.add(refreshButton);
            add(buttonPanel, BorderLayout.SOUTH);

            refreshButton.addActionListener(e -> {
                loadDataFromDatabase(model, true);
                table.repaint();
            });

            loadDataFromDatabase(model, false);
            setVisible(true);
        }

        private Connection openDatabase() {
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

        private void closeDatabase(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private ResultSet executeQuery(Connection connection, String query) {
            try {
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void loadDataFromDatabase(DefaultTableModel model, boolean sortByCost) {
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

    // batool
    static class TripDisplayGUI extends JFrame {

        public TripDisplayGUI() {
            createAndShowGUI();
        }

        private void createAndShowGUI() {
            setTitle("Available Trips");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 300);

            String[] columnNames = { "Trip ID", "City", "Start Date", "End Date", "Tour Name" };
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane);

            fetchData(model);

            setLocationRelativeTo(null);
            setVisible(true);
        }

        private Connection openDatabase() {
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

        private void closeDatabase(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private ResultSet executeQuery(Connection connection, String query) {
            try {
                Statement statement = connection.createStatement();
                return statement.executeQuery(query);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private void fetchData(DefaultTableModel model) {
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

    // b-files
    // batool,haifa,norah
    static class OfferViewer {

        public void run() {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the file or directory name:");
            String fileName = input.nextLine();

            Path path = Paths.get(fileName);

            if (Files.exists(path)) {
                if (Files.isDirectory(path)) {
                    // Output directory listing
                    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                        for (Path p : directoryStream) {
                            System.out.println(p);
                        }
                    } catch (IOException e) {
                        System.out.println("Error accessing the directory.");
                    }
                } else {
                    // Read and display the offer file
                    try {
                        String offerContent = Files.readString(path);
                        System.out.println("Offer Details:");
                        System.out.println(offerContent);

                        // Get start and end dates from the user
                        System.out.println("Enter the start date of the offer (YYYY-MM-DD):");
                        String startDate = input.nextLine();

                        System.out.println("Enter the end date of the offer (YYYY-MM-DD):");
                        String endDate = input.nextLine();

                        // Check if the current date is within the offer's duration
                        String currentDate = "2024-05-03"; // Replace with the actual current date
                        if (currentDate.compareTo(startDate) >= 0 && currentDate.compareTo(endDate) <= 0) {
                            System.out.println("This offer is currently valid.");
                        } else {
                            System.out.println("This offer is not valid at the moment.");
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading the offer file.");
                    }
                }
            } else {
                System.out.println("File or directory does not exist.");
            }
        }
    }

    static class DiscountGUI extends JFrame implements ActionListener {

        private final String[] destinations = { "Riyadh", "Jeddah", "Abha", "Eastern Region", "AlUla" };
        private final String[][] discounts = {
                { "500 and less: RIYADH5", "Between 500 and 1000: RIYADH10", "For 1000 and more: RIYADH15" },
                { "500 and less: JEDDAH5", "Between 500 and 1000: JEDDAH10", "For 1000 and more: JEDDAH15" },
                { "500 and less: ABHA5", "Between 500 and 1000: ABHA10", "For 1000 and more: ABHA15" },
                { "500 and less: ER5", "Between 500 and 1000: ER10", "For 1000 and more: ER15" },
                { "500 and less: ALULLA5", "Between 500 and 1000: ALULA10", "For 1000 and more: ALULA15" }
        };

        private JLabel titleLabel, destinationLabel, amountLabel;
        private JComboBox<String> destinationComboBox;
        private JTextField amountTextField;
        private JButton generateButton;
        private JTextArea offerTextArea;

        public static void main(String[] args) {
            SwingUtilities.invokeLater(() -> new DiscountGUI().setVisible(true));
        }

        public DiscountGUI() {
            super("Discount Calculator");
            titleLabel = new JLabel("Discount Calculator", JLabel.CENTER);
            destinationLabel = new JLabel("Select Destination:");
            destinationComboBox = new JComboBox<>(destinations);
            amountLabel = new JLabel("Enter Amount (SAR):");
            amountTextField = new JTextField(10);
            generateButton = new JButton("Generate Discount");
            offerTextArea = new JTextArea(10, 30);
            offerTextArea.setEditable(false);

            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            panel.add(titleLabel, c);

            c.gridy = 1;
            panel.add(destinationLabel, c);

            c.gridy = 2;
            panel.add(destinationComboBox, c);

            c.gridy = 3;
            panel.add(amountLabel, c);

            c.gridy = 4;
            panel.add(amountTextField, c);

            c.gridy = 5;
            panel.add(generateButton, c);

            c.gridy = 6;
            c.gridwidth = 1;
            panel.add(offerTextArea, c);

            generateButton.addActionListener(this);

            add(panel);
            pack();
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String destination = (String) destinationComboBox.getSelectedItem();
            double amount = Double.parseDouble(amountTextField.getText());
            String[] offerDetails = discounts[getDestinationIndex(destination)];

            offerTextArea.setText("Offers for " + destination + ":\n\n");
            for (String offer : offerDetails) {
                offerTextArea.append("- " + offer + "\n");
            }
        }

        private int getDestinationIndex(String destination) {
            for (int i = 0; i < destinations.length; i++) {
                if (destinations[i].equals(destination)) {
                    return i;
                }
            }
            return -1;
        }
    }

    // asayel,lama,atheer

    class CreateTextFile {
        private static Formatter output; // Declare the Formatter outside of methods to use it in multiple methods

        public static void openFile() {
            try {
                output = new Formatter("IT.txt");
            } catch (SecurityException securityException) {
                System.err.println("Write denied. Terminating");
                System.exit(1);
            } catch (IOException fileNotFoundException) {
                System.err.println("Error opening file. Terminating");
                System.exit(1);
            }
        }

        public static void addRecords() {
            Scanner input = new Scanner(System.in);
            System.out.printf("%s%n%s%n?", "Enter the problem you encountered, Details",
                    "Enter end-of-file indicator to end input");

            while (input.hasNext()) {
                try {
                    output.format("%s|%s|%s%n", input.next(), input.next(), input.next());
                } catch (NoSuchElementException elementException) {
                    System.err.println("Invalid input. Please try again.");
                    input.nextLine(); // Discard input so user can try again
                } catch (FormatterClosedException formatterClosedException) {
                    System.err.println("Error writing to file. Terminating");
                    break;
                }
                System.out.print("?");
            }
        }

        public static void closeFile() {
            if (output != null) {
                output.close();
            }
        }

        static class ITIssueLoggerGUI extends JFrame {
            private JButton sendButton;
            private JTextField problemTypeField;
            private JTextField detailsField;
            private JTextField severityField;
            private JTextArea outputArea;

            public ITIssueLoggerGUI() {
                setTitle("IT Issue Logger");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(500, 300);
                setLayout(new BorderLayout());

                sendButton = new JButton("Send");
                problemTypeField = new JTextField();
                detailsField = new JTextField();
                severityField = new JTextField();
                outputArea = new JTextArea();
                outputArea.setEditable(false);

                JPanel inputPanel = new JPanel(new GridLayout(4, 2));
                inputPanel.add(new JLabel("Problem Type: "));
                inputPanel.add(problemTypeField);
                inputPanel.add(new JLabel("Details: "));
                inputPanel.add(detailsField);
                inputPanel.add(new JLabel("Severity: "));
                inputPanel.add(severityField);
                inputPanel.add(sendButton);

                JScrollPane scrollPane = new JScrollPane(outputArea);

                add(inputPanel, BorderLayout.NORTH);
                add(scrollPane, BorderLayout.CENTER);

                sendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String problemType = problemTypeField.getText();
                        String details = detailsField.getText();
                        String severity = severityField.getText();

                        if (!problemType.isEmpty() && !details.isEmpty() && !severity.isEmpty()) {
                            processInput(problemType, details, severity);
                            problemTypeField.setText("");
                            detailsField.setText("");
                            severityField.setText("");
                        } else {
                            outputArea.append("Please fill in all fields.\n");
                        }
                    }
                });
            }

            private void processInput(String problemType, String details, String severity) {
                try {
                    FileWriter fileWriter = new FileWriter("IT.txt", true); // append mode

                    fileWriter.write(problemType + "|" + details + "|" + severity + "\n");
                    fileWriter.close();

                    outputArea.append("Data written to file successfully: " + problemType + "|" + details + "|"
                            + severity + "\n");

                } catch (IOException ex) {
                    outputArea.append("An error occurred while writing to the file.\n");
                    ex.printStackTrace();
                }
            }
        }
    }

}