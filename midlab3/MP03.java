package midlab3;

import java.io.*;

public class MP03 {
    public static void main(String[] args) throws IOException {
        String filePath = "results.csv";
        String keyword = "JavaScript";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean found = false;
            while ((line = br.readLine()) != null) {
                if (line.contains(keyword)) {
                    System.out.println(line);
                    found = true;
                }
            }
            if (!found) System.out.println("No records found containing: " + keyword);
        }
    }
}