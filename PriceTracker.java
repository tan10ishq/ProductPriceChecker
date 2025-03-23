import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PriceTracker {

    private static final String CSV_FILE = "price_history.csv";

    public static void displayPriceHistory() {
        System.out.println("\n--- Price History ---");

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {

            String line;
            boolean isEmpty = true;

            // Read and display each line
            while ((line = reader.readLine()) != null) {
                isEmpty = false;
                System.out.println(line);
            }

            if (isEmpty) {
                System.out.println("No price history found.");
            }

        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        }
    }
}
