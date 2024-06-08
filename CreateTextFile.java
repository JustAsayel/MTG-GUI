import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class CreateTextFile {
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
        System.out.printf("%s%n%s%n?", "Enter the problem you encountered, Details", "Enter end-of-file indicator to end input");

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

                outputArea.append("Data written to file successfully: " + problemType + "|" + details + "|" + severity + "\n");

            } catch (IOException ex) {
                outputArea.append("An error occurred while writing to the file.\n");
                ex.printStackTrace();
            }
        }
    }
}

        }