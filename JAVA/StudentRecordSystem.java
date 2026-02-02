// Programmer Identifier: Wiley Javier - 23-0455-222

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRecordSystem extends JFrame {
    DefaultTableModel model;
    JTable table;
    JTextField tfID, tfFirstName, tfLastName;
    JTextField tfLab1, tfLab2, tfLab3, tfPrelim, tfAttendance;
    String csvFile = "MOCK_DATA.csv"; // CSV path

    public StudentRecordSystem() {
        this.setTitle("Records - Wiley Javier - 23-0455-222");
        this.setSize(1200, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.getContentPane().setBackground(new Color(245, 245, 245));

        // Table model & JTable
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // All cells editable
            }
        };
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(28);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(173, 216, 230));
        table.setGridColor(Color.LIGHT_GRAY);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(60, 179, 113));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Columns
        String[] columns = {"StudentID", "First Name", "Last Name",
                "LAB 1", "LAB 2", "LAB 3", "PRELIM EXAM", "ATTENDANCE"};
        for (String col : columns) model.addColumn(col);

        // Load CSV
        loadCSV(csvFile);

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.setBackground(new Color(245, 245, 245));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(60, 179, 113), 2),
                "Add / Delete Student",
                0, 0,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(60, 179, 113)
        ));

        tfID = new JTextField(6);
        tfFirstName = new JTextField(10);
        tfLastName = new JTextField(10);
        tfLab1 = new JTextField(5);
        tfLab2 = new JTextField(5);
        tfLab3 = new JTextField(5);
        tfPrelim = new JTextField(6);
        tfAttendance = new JTextField(5);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBackground(new Color(60, 179, 113));
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setFocusPainted(false);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBackground(new Color(220, 20, 60));
        btnDelete.setForeground(Color.BLACK);
        btnDelete.setFocusPainted(false);

        inputPanel.add(new JLabel("ID:")); inputPanel.add(tfID);
        inputPanel.add(new JLabel("First Name:")); inputPanel.add(tfFirstName);
        inputPanel.add(new JLabel("Last Name:")); inputPanel.add(tfLastName);
        inputPanel.add(new JLabel("LAB 1:")); inputPanel.add(tfLab1);
        inputPanel.add(new JLabel("LAB 2:")); inputPanel.add(tfLab2);
        inputPanel.add(new JLabel("LAB 3:")); inputPanel.add(tfLab3);
        inputPanel.add(new JLabel("PRELIM:")); inputPanel.add(tfPrelim);
        inputPanel.add(new JLabel("ATT:")); inputPanel.add(tfAttendance);
        inputPanel.add(btnAdd); inputPanel.add(btnDelete);

        this.add(scrollPane, BorderLayout.CENTER);
        this.add(inputPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addRow());
        btnDelete.addActionListener(e -> deleteRow());

        // Save CSV when cell is edited
        model.addTableModelListener(e -> saveCSV());

        this.setVisible(true);
    }

    private void loadCSV(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                List<String> row = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    if (i < values.length) row.add(values[i]);
                    else row.add("");
                }
                model.addRow(row.toArray());
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "CSV not found. A new file will be created.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading CSV: " + e.getMessage());
        }
    }

    private void addRow() {
        if (!tfID.getText().trim().isEmpty() && !tfFirstName.getText().trim().isEmpty()
                && !tfLastName.getText().trim().isEmpty()) {
            model.addRow(new Object[]{
                    tfID.getText().trim(),
                    tfFirstName.getText().trim(),
                    tfLastName.getText().trim(),
                    tfLab1.getText().trim(),
                    tfLab2.getText().trim(),
                    tfLab3.getText().trim(),
                    tfPrelim.getText().trim(),
                    tfAttendance.getText().trim()
            });
            clearFields();
            saveCSV(); // save CSV immediately
        } else {
            JOptionPane.showMessageDialog(this, "ID, First Name, and Last Name are required!",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteRow() {
        int selected = table.getSelectedRow();
        if (selected != -1) {
            model.removeRow(selected);
            saveCSV(); // save CSV immediately
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete.",
                    "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearFields() {
        tfID.setText(""); tfFirstName.setText(""); tfLastName.setText("");
        tfLab1.setText(""); tfLab2.setText(""); tfLab3.setText("");
        tfPrelim.setText(""); tfAttendance.setText("");
    }

    private void saveCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFile))) {
            // Write header
            pw.println("StudentID,First Name,Last Name,LAB 1,LAB 2,LAB 3,PRELIM EXAM,ATTENDANCE");

            // Write rows
            for (int i = 0; i < model.getRowCount(); i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < model.getColumnCount(); j++) {
                    sb.append(model.getValueAt(i, j));
                    if (j < model.getColumnCount() - 1) sb.append(",");
                }
                pw.println(sb.toString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving CSV: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch(Exception e) {}
        new StudentRecordSystem();
    }
}
