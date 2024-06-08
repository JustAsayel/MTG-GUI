import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

public class itfile extends JFrame {
    private JButton sendButton;
    private JTextField problemTypeField, detailsField, severityField;
    private JTextArea outputArea;
    private static Formatter output;
    private static final String FILE_PATH = System.getProperty("user.home") + "/IT.txt";

    if(args.length>0&&args[0].equals("console"))
    {
        consoleLogging();
    }else
    {
            SwingUtilities.invokeLater(() -> new itfile().setVisible(true));
        }

    public itfile() {
        setTitle("IT Issue Logger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLayout(new BorderLayout());

        sendButton = new JButton("Log Issue");
        problemTypeField = new JTextField();
        detailsField = new JTextField();
        severityField = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("Problem Type:"));
        inputPanel.add(problemTypeField);
        inputPanel.add(new JLabel("Details:"));
        inputPanel.add(detailsField);
        inputPanel.add(new JLabel("Severity:"));
        inputPanel.add(severityField);
        inputPanel.add(new JLabel()); // Placeholder
        inputPanel.add(sendButton);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        sendButton.addActionListener(e -> {
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
        });
    }

    private void processInput(String problemType, String details, String severity) {
        try (FileWriter fileWriter = new FileWriter(FILE_PATH, true)) {
            fileWriter.write(String.format("%s|%s|%s%n", problemType, details, severity));
            outputArea.append(
                    String.format("Data written to file successfully: %s|%s|%s%n", problemType, details, severity));
        } catch (IOException ex) {
            outputArea.append("An error occurred while writing to the file.\n");
            ex.printStackTrace();
        }
    }

    public static void consoleLogging() {
        try {
            openFile();
            addRecords();
        } finally {
            closeFile();
        }
        System.out.println("IT Issue Log has been saved successfully!");
    }

    public static void openFile() {
        try {
            output = new Formatter(FILE_PATH);
        } catch (SecurityException | IOException e) {
            System.err.println("Unable to open file. Terminating.");
            System.exit(1);
        }
    }

    public static void addRecords() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter problem type, details, and severity on separate lines. Type 'EOF' to end:");
        while (true) {
            String line = input.nextLine();
            if ("EOF".equals(line))
                break;
            output.format("%s%n", line);
        }
        input.close();
    }

    public static void closeFile() {
        if (output != null) {
            output.close();
        }
    }
}
