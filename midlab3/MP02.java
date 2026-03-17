package midlab3;

import java.io.*;

public class MP02 {
    public static void main(String[] args) {
        String filePath = "Sample_Data-Prog-2-csv.csv";
        int count = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null && count < 10) {
                if (line.trim().isEmpty() || line.startsWith("Candidate")) continue;
                System.out.println(line);
                count++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}