package midlab3;

import java.io.*;
import java.util.Scanner;

public class MP03 {
    public static void main(String[] args) {
        String filePath = "Sample_Data-Prog-2-csv.csv";
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine().toLowerCase();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int foundCount = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("Candidate")) continue;

                if (line.toLowerCase().contains(keyword)) {
                    System.out.println(line);
                    foundCount++;
                }
            }

            if (foundCount == 0) {
                System.out.println("No results found for \"" + keyword + "\".");
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}