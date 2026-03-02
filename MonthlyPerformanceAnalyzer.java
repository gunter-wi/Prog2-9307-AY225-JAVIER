
import java.io.*;
import java.util.*;

public class MonthlyPerformanceAnalyzer {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        File file;

        // LOOP until valid file path
        while (true) {
            System.out.print("Enter dataset file path: ");
            String path = input.nextLine();
            file = new File(path);

            if (!file.exists()) {
                System.out.println("Error: File does not exist.\n");
            } else if (!file.isFile()) {
                System.out.println("Error: Path is not a file.\n");
            } else if (!file.canRead()) {
                System.out.println("Error: File is not readable.\n");
            } else if (!path.toLowerCase().endsWith(".csv")) {
                System.out.println("Error: File is not CSV format.\n");
            } else {
                break;
            }
        }

        try {
            List<DataRecord> records = readCSV(file);
            Map<String, Double> monthlyTotals = computeMonthlyTotals(records);
            displaySummary(monthlyTotals);
            identifyBestMonth(monthlyTotals);

        } catch (Exception e) {
            System.out.println("Error processing file: " + e.getMessage());
        }

        input.close();
    }

    // Read CSV using BufferedReader
    public static List<DataRecord> readCSV(File file) throws IOException {

        List<DataRecord> records = new ArrayList<>();

        // use try-with-resources so the reader is always closed
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {

                if (firstLine) { // skip header
                    firstLine = false;
                    continue;
                }

                String[] values = parseCSVLine(line);

                // Make sure we have enough columns
                if (values.length < 13) continue;

                String date = values[12].trim();       // release_date column
                String salesStr = values[7].trim();    // total_sales column

                // Skip rows with missing data
                if (date.isEmpty() || salesStr.isEmpty()) continue;

                // Basic validation: need at least "YYYY-MM" (7 chars) to extract month
                if (date.length() < 7) continue;
                // Optional: ensure there's a dash after year
                if (date.charAt(4) != '-') continue;

                // sanitize sales (remove thousands separators and possible currency symbols)
                salesStr = salesStr.replaceAll("[,$]", "");

                try {
                    double sales = Double.parseDouble(salesStr);
                    // safe to create DataRecord now
                    records.add(new DataRecord(date, sales));
                } catch (NumberFormatException e) {
                    // skip invalid numeric values
                    continue;
                }
            }
        }

        return records;
    }

    // Simple CSV parser that handles quoted fields and double-quote escapes
    private static String[] parseCSVLine(String line) {
        List<String> cols = new ArrayList<>();
        if (line == null || line.isEmpty()) return new String[0];

        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                // handle double-quote escape ""
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++; // skip next quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                cols.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        cols.add(sb.toString());
        return cols.toArray(new String[0]);
    }

    // Group by month using TreeMap (sorted ascending)
    public static Map<String, Double> computeMonthlyTotals(List<DataRecord> records) {

        Map<String, Double> monthlyTotals = new TreeMap<>();

        for (DataRecord record : records) {
            monthlyTotals.put(
                record.getMonth(),
                monthlyTotals.getOrDefault(record.getMonth(), 0.0)
                        + record.getSales()
            );
        }

        return monthlyTotals;
    }

    public static void displaySummary(Map<String, Double> monthlyTotals) {

        System.out.println("\n===== Monthly Sales Summary =====");

        for (Map.Entry<String, Double> entry : monthlyTotals.entrySet()) {
            System.out.printf("Month: %s | Total Sales: %.2f\n",
                    entry.getKey(),
                    entry.getValue());
        }
    }

    public static void identifyBestMonth(Map<String, Double> monthlyTotals) {

        if (monthlyTotals == null || monthlyTotals.isEmpty()) {
            System.out.println("\nNo valid records to analyze.");
            return;
        }

        String bestMonth = "";
        double highest = Double.NEGATIVE_INFINITY;

        for (Map.Entry<String, Double> entry : monthlyTotals.entrySet()) {
            if (entry.getValue() > highest) {
                highest = entry.getValue();
                bestMonth = entry.getKey();
            }
        }

        System.out.println("\n===== Best Performing Month =====");
        System.out.printf("Month: %s | Total Sales: %.2f\n",
                bestMonth, highest);
    }

    // Inner DataRecord class to ensure the type is resolvable inside this file
    public static class DataRecord {
        private String month;
        private double sales;

        public DataRecord(String date, double sales) {
            // Extract YYYY-MM safely
            if (date != null && date.length() >= 7 && date.charAt(4) == '-') {
                this.month = date.substring(0, 7);
            } else {
                this.month = date == null ? "" : date;
            }
            this.sales = sales;
        }

        public String getMonth() {
            return month;
        }

        public double getSales() {
            return sales;
        }
    }
}