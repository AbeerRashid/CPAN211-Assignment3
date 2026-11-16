import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainDriver {

    public static void main(String[] args) {
        System.out.println("=== Humber Sales Management System (Data Structures) ===");
        loadSalesFile();
    }

    private static void loadSalesFile() {
        List<SalesRecord> salesRecords = new ArrayList<>();
        int totalRecords = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("Sales.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");

                if (data.length != 5) {
                    continue;
                }

                try {
                    String customerID = data[0].trim();
                    String customerName = data[1].trim();
                    String productID = data[2].trim();
                    String productName = data[3].trim();
                    int quantity = Integer.parseInt(data[4].trim());

                    if (customerID.isEmpty() || customerName.isEmpty() || productID.isEmpty() || productName.isEmpty() || quantity <= 0) {
                        continue;
                    }

                    SalesRecord record = new SalesRecord(customerID, customerName, productID, productName, quantity);
                    salesRecords.add(record);
                    totalRecords++;

                    // Print each record to the console
                    System.out.println(record);

                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity in line: " + line);
                }
            }

            System.out.println("\nTotal records processed successfully: " + totalRecords);

        } catch (IOException ex) {
            System.out.println("Could not read Sales.txt: " + ex.getMessage());
        }
    }
}
