package midlab3;

import java.io.*;

public class MP01 {
    public static void main(String[] args) {

        String filePath = "Sample_Data-Prog-2-csv.csv";
        int recordCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("\"")) {   // valid candidate row
                    recordCount++;
                }
            }

            System.out.println("Total number of records: " + recordCount);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}