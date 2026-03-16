package midlab3;

import java.io.*;
import java.util.*;

public class DatasetProcessor {
    public static void main(String[] args) {
        String filePath = "Sample_Data-Prog-2-csv.csv"; // your CSV file
        String keyword = "Python"; // change to any word you want to search
        List<String[]> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",", -1); // handle empty columns
                records.add(columns);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // MP01 – Total number of records
        System.out.println("Total number of records: " + records.size());

        // MP02 – First 10 rows
        System.out.println("\nFirst 10 rows:");
        for (int i = 0; i < Math.min(10, records.size()); i++) {
            System.out.println(Arrays.toString(records.get(i)));
        }

        // MP03 – Search for a keyword
        System.out.println("\nRecords containing '" + keyword + "':");
        for (String[] row : records) {
            for (String col : row) {
                if (col.toLowerCase().contains(keyword.toLowerCase())) {
                    System.out.println(Arrays.toString(row));
                    break; // stop checking other columns for this row
                }
            }
        }
    }
}