import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n Product Price Tracker");
        System.out.println("1. Track a new price");
        System.out.println("2. View past prices");
        System.out.print("\nChoose an option (1/2): ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (choice == 1) {
            // Track new price
            System.out.print("\nEnter the product name to track: ");
            String productName = scanner.nextLine();

            String price = PriceFetcher.fetchProductPrice(productName);

            if (price != null) {
                System.out.println("Price of '" + productName + "': " + price);
            
                CSVWriter.writeToCSV(productName, price);

            } else {
                System.out.println("Failed to fetch product data.");
            }

        } else if (choice == 2) {
            // View past prices
            System.out.print("\nEnter the product name: ");
            String productName = scanner.nextLine();
            
            CSVWriter.displayPastPrices(productName);

        } else {
            System.out.println("Invalid choice. Exiting...");
        }

        scanner.close();
    }
}