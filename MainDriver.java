import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainDriver {

    public static void main(String[] args) {
        System.out.println("=== Humber Sales Management System (Console Version) ===");
        loadSalesFile();
    }

    private static void loadSalesFile() {
        int totalRecords = 0;

        // Initialize team manager classes
        Product productManager = new Product();
        Map<Integer, Customer> customerMap = new HashMap<>();

        // Try-with-resources for safe file reading using relative path
        try (BufferedReader reader = new BufferedReader(new FileReader("Sales.txt"))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                // Input validation - skip blank lines
                if (line.isEmpty()) {
                    continue;
                }

                // Split line by whitespace to match your 7 columns:
                String[] data = line.split("\\s+");

                // Validation: if row doesn't have at least 7 elements, log and skip
                if (data.length < 7) {
                    System.out.println("Invalid data format at line " + lineNumber + ": " + line);
                    continue;
                }

                // Local try-catch block for safe number parsing (prevents mid-execution crashes)
                try {
                    int customerID = Integer.parseInt(data[0]);
                    String firstName = data[1];
                    String lastName = data[2];
                    String customerFullName = firstName + " " + lastName;

                    int productID = Integer.parseInt(data[3]);
                    String productName = data[4];
                    double singleItemPrice = Double.parseDouble(data[5]);
                    int itemsPurchased = Integer.parseInt(data[6]);

                    // Integrate Customer class
                    Customer customer = customerMap.get(customerID);
                    if (customer == null) {
                        customer = new Customer(customerID, firstName, lastName);
                        customerMap.put(customerID, customer);
                    }
                    customer.addToBill(singleItemPrice, itemsPurchased);

                    // Integrate Product class
                    productManager.updateProduct(productID, productName, itemsPurchased);

                    // Create record and print confirmation line
                    SalesRecord record = new SalesRecord(
                            String.valueOf(customerID), customerFullName,
                            String.valueOf(productID), productName, itemsPurchased);
                    
                    System.out.println(record);
                    totalRecords++;

                } catch (NumberFormatException ex) {
                    // Exception handling for bad numbers on a specific line
                    System.out.println("Error parsing number in line " + lineNumber + ": " + line);
                }
            }

            System.out.println("\nTotal records successfully processed: " + totalRecords);

            // Print final customer bills summary
            System.out.println("\n--- FINAL CUSTOMER BILLS ---");
            for (Customer c : customerMap.values()) {
                System.out.println(c);
            }

            // Print product summary using Person 3's method
            productManager.printProducts();

        } catch (IOException ex) {
            System.out.println("Could not read Sales.txt: " + ex.getMessage());
        }
    }
}