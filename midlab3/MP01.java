package midlab3;

import java.io.*;

public class MP01 {
    public static void main(String[] args) {
        String filePath = "results.csv";
        int recordCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                // Skip empty lines and headers
                if (line.trim().isEmpty()) continue;
                if (!headerSkipped && line.startsWith("Candidate")) {
                    headerSkipped = true;
                    continue;
                }
                if (headerSkipped) recordCount++;
            }

            System.out.println("Total number of records: " + recordCount);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}