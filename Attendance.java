package AttendanceProject;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/*
 * Attendance Tracker Application
 * Generates system time and a programmatic E-Signature (UUID)
 */
public class Attendance {

    public static void main(String[] args) {

        // Create the main window (JFrame)
        JFrame frame = new JFrame("Attendance Tracker");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the window

        // Main panel using GridBagLayout for clean alignment
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        JLabel nameLabel = new JLabel("Attendance Name:");
        JLabel courseLabel = new JLabel("Course / Year:");
        JLabel timeLabel = new JLabel("Time In:");
        JLabel signatureLabel = new JLabel("E-Signature:");

        // Text fields
        JTextField nameField = new JTextField();
        JTextField courseField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField signatureField = new JTextField();

        nameField.setPreferredSize(new Dimension(220, 28));
        courseField.setPreferredSize(new Dimension(220, 28));
        timeField.setPreferredSize(new Dimension(220, 28));
        signatureField.setPreferredSize(new Dimension(220, 28));

        // Time and E-Signature fields should not be editable
        timeField.setEditable(false);
        signatureField.setEditable(false);

        // Obtain system date and time
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("MMMM dd, yyyy | hh:mm a");
        String timeIn = LocalDateTime.now().format(formatter);
        timeField.setText(timeIn);

        // Generate programmatic E-Signature using UUID
        String eSignature = UUID.randomUUID().toString();
        signatureField.setText(eSignature);

        // Submit button
        JButton submitButton = new JButton("Submit Attendance");

        submitButton.addActionListener(e -> {
            if (nameField.getText().isEmpty() || courseField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please complete all required fields.",
                        "Incomplete Form",
                        JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Attendance Submitted Successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Layout positioning
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(courseLabel, gbc);
        gbc.gridx = 1;
        panel.add(courseField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(timeLabel, gbc);
        gbc.gridx = 1;
        panel.add(timeField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(signatureLabel, gbc);
        gbc.gridx = 1;
        panel.add(signatureField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(submitButton, gbc);

        // Add panel to frame and display
        frame.add(panel);
        frame.setVisible(true);
    }
}
