import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSVWriter {

    private static final String FILE_PATH = "prices_history.csv";

    // Method to append new price to CSV
    public static void writeToCSV(String product, String price) {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {

            // Add current date and time
            LocalDateTime now = LocalDateTime.now();
            
            // Store full timestamp but display only date later
            DateTimeFormatter fullFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(fullFormatter);

            // Append new row to the CSV file
            writer.append(String.format("%s, %s, %s\n", timestamp, product, price));

            System.out.println("Price saved to CSV.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to save price to CSV.");
        }
    }

    // Method to display past prices from the CSV (showing only the date)
    public static void displayPastPrices(String product) {
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            System.out.println("\nPast Prices for '" + product + "':");

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(", ");
                if (data.length == 3 && data[1].equalsIgnoreCase(product)) {

                    // Extract only the date from the full timestamp
                    String fullTimestamp = data[0];
                    String date = fullTimestamp.split(" ")[0];  // Get only the date part

                    System.out.println( date+ " - " + data[2]);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No past prices found for '" + product + "'.");
            }

        } catch (IOException e) {
            System.out.println("Error reading CSV file.");
            e.printStackTrace();
        }
    }
}
