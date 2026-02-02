package PrelimLabwork3.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrelimGradeCalculatorGUI {

    public static void main(String[] args) {

        JFrame frame = new JFrame("Prelim Grade Calculator");
        frame.setSize(600, 750);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(220, 235, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Prelim Grade Calculator", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(30, 60, 120));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        gbc.gridwidth = 1;

        JLabel lblAttendance = new JLabel("Attendance Grade:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblAttendance, gbc);

        JTextField txtAttendance = new JTextField(10);
        gbc.gridx = 1;
        panel.add(txtAttendance, gbc);

        JLabel lblLab1 = new JLabel("Lab Work 1:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblLab1, gbc);

        JTextField txtLab1 = new JTextField(10);
        gbc.gridx = 1;
        panel.add(txtLab1, gbc);

        JLabel lblLab2 = new JLabel("Lab Work 2:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblLab2, gbc);

        JTextField txtLab2 = new JTextField(10);
        gbc.gridx = 1;
        panel.add(txtLab2, gbc);

        JLabel lblLab3 = new JLabel("Lab Work 3:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblLab3, gbc);

        JTextField txtLab3 = new JTextField(10);
        gbc.gridx = 1;
        panel.add(txtLab3, gbc);

        JButton btnCompute = new JButton("Compute Grades");
        btnCompute.setBackground(new Color(30, 144, 255));
        btnCompute.setForeground(Color.BLACK);
        btnCompute.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(btnCompute, gbc);

        JLabel outputLabel = new JLabel();
        outputLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        outputLabel.setOpaque(true);
        outputLabel.setBackground(new Color(245, 245, 245));
        outputLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        outputLabel.setVerticalAlignment(SwingConstants.TOP);
        outputLabel.setForeground(Color.BLACK);
        outputLabel.setPreferredSize(new Dimension(500, 380));
        gbc.gridy = 6;
        panel.add(outputLabel, gbc);

        frame.add(panel);
        frame.setVisible(true);

        btnCompute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double attendance = Double.parseDouble(txtAttendance.getText());
                    double lab1 = Double.parseDouble(txtLab1.getText());
                    double lab2 = Double.parseDouble(txtLab2.getText());
                    double lab3 = Double.parseDouble(txtLab3.getText());

                    if (attendance < 0 || attendance > 100 || lab1 < 0 || lab1 > 100
                            || lab2 < 0 || lab2 > 100 || lab3 < 0 || lab3 > 100) {
                        JOptionPane.showMessageDialog(frame,
                                "⚠ Please enter valid grades between 0 and 100.",
                                "Invalid Input", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double labAverage = (lab1 + lab2 + lab3) / 3;
                    double classStanding = (attendance * 0.40) + (labAverage * 0.60);

                    double requiredPass = (75 - (classStanding * 0.30)) / 0.70;
                    double requiredExcellent = (100 - (classStanding * 0.30)) / 0.70;

                    if (requiredPass > 100) requiredPass = 100;
                    if (requiredExcellent > 100) requiredExcellent = 100;

                    StringBuilder output = new StringBuilder("<html>");
                    output.append("<b>Attendance Grade:</b> ").append(attendance)
                            .append("<br>Explanation: This represents your attendance performance.<br><br>");

                    output.append("<b>Lab Work 1:</b> ").append(lab1).append("<br>");
                    output.append("<b>Lab Work 2:</b> ").append(lab2).append("<br>");
                    output.append("<b>Lab Work 3:</b> ").append(lab3).append("<br><br>");

                    output.append("<b>Lab Work Average:</b> ").append(String.format("%.2f", labAverage))
                            .append("<br>Explanation: This is the average of your three lab work grades.<br><br>");

                    output.append("<b>Class Standing:</b> ").append(String.format("%.2f", classStanding))
                            .append("<br>Explanation: Class Standing is 40% Attendance + 60% Lab Work Average.<br><br>");

                    output.append("<b>Required Prelim Exam Grade:</b><br>");
                    output.append("To Pass (75): ").append(String.format("%.2f", requiredPass)).append("<br>");
                    output.append("To Excel (100): ").append(String.format("%.2f", requiredExcellent)).append("<br><br>");

                    output.append("<b>Final Remark:</b> ");
                    if (requiredPass <= 75) {
                        output.append("You are in good standing and can pass the Prelim period with a reasonable exam score.");
                    } else if (requiredPass <= 100) {
                        output.append("You can still pass the Prelim period, but you need to score high on the exam.");
                    } else {
                        output.append("Passing the Prelim period is not achievable with your current Class Standing.");
                    }

                    output.append("</html>");
                    outputLabel.setText(output.toString());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame,
                            "⚠ Invalid input! Please enter numeric values only.",
                            "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
