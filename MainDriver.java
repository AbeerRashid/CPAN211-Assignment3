
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.classfile.Label;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.TableColumn;
import javax.swing.text.TableView;

public class MainDriver extends Application {

        private ObservableList<SalesRecord> salesRecords = FXCollections.observableArrayList();

        private TableView<SalesRecord> table;
        private Label statusLabel;

        @Override
        public void start(Stage stage) {

                Label title = new Label("Humber Sales Management System");
                title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

                Button loadButton = new Button("Load Sales Records");

                statusLabel = new Label("Click the button to load sales records.");

                table = new TableView<>();

                TableColumn<SalesRecord, String> customerID = new TableColumn<>("Customer ID");
                customerID.setCellValueFactory(
                                data -> data.getValue().customerIDProperty());

                TableColumn<SalesRecord, String> customerName = new TableColumn<>("Customer Name");
                customerName.setCellValueFactory(
                                data -> data.getValue().customerNameProperty());

                TableColumn<SalesRecord, String> productID = new TableColumn<>("Product ID");
                productID.setCellValueFactory(
                                data -> data.getValue().productIDProperty());

                TableColumn<SalesRecord, String> productName = new TableColumn<>("Product Name");
                productName.setCellValueFactory(
                                data -> data.getValue().productNameProperty());

                TableColumn<SalesRecord, Integer> quantity = new TableColumn<>("Quantity");
                quantity.setCellValueFactory(
                                data -> data.getValue().quantityProperty().asObject());

                table.getColumns().addAll(
                                customerID, customerName,
                                productID, productName, quantity);

                table.setItems(salesRecords);

                loadButton.setOnAction(event -> loadSalesFile());

                VBox layout = new VBox(15);
                layout.setPadding(new Insets(20));

                layout.getChildren().addAll(
                                title, loadButton, table, statusLabel);

                Scene scene = new Scene(layout, 850, 500);

                stage.setTitle("Humber Sales Management System");
                stage.setScene(scene);
                stage.show();
        }

        // Abeer Part starts here
        // Read and process Sales.txt
    private void loadSalesFile() {

        salesRecords.clear();
        int totalRecords = 0;

        Product productManager = new Product();

        // Initialize the two maps required by the assignment
        Map<Integer, Customer> customerMap = new HashMap<>();

        // Try-with-resources to safely read Sales.txt without throwing exceptions to main
        try (BufferedReader reader =
                new BufferedReader(new FileReader("Sales.txt"))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                
                // Input validation - skip blank lines
                if (line.trim().isEmpty()) {
                    continue;
                }
                //Split line by whitespace to match the 7 columns:
                String[] data = line.split("\\s+");

                if (data.length < 7){
                        System.out.println("Invalid data format at line " + lineNumber + ": " + line);
                        continue;
                }
                // Try-catch block for safe number parsing (prevents mid-execution crashes) resume here
                try {    
                    int customerID = Integer.parseInt(data[0]);
                    String firstName = data[1];
                    String lastName = data[2];
                    String customerFullName = firstName + " " + lastName;

                    int productID = Integer.parseInt(data[3]);
                    String productName = data[4];
                    double singleItemPrice = Double.parseDouble(data[5]);
                    int itemsPurchased = Integer.parseInt(data[6]);

                    // Create or update customer in the map
                        Customer customer = customerMap.get(customerID);
                        if (customer == null) {
                            customer = new Customer(customerID, customerFullName);
                            customerMap.put(customerID, customer);
                        }
                        customer.addPurchase(singleItemPrice * itemsPurchased);

                        // Update product using Person 3's Product class
                        productManager.updateProduct(productID, productName, itemsPurchased);

                        // Add record to JavaFX table view for UI display
                        salesRecords.add(new SalesRecord(
                                String.valueOf(customerID), firstName + " " + lastName,
                                String.valueOf(productID), productName, itemsPurchased));
                        
                        totalRecords++;

                    } catch (NumberFormatException ex) {
                        // Exception handling for bad numbers on a specific line
                        System.out.println("Error parsing number in line " + lineNumber + ": " + line);
                    }
                }
                statusLabel.setText("Total records processed: "+ totalRecords);

                // Print final summaries to console to satisfy assignment printout requirement
                System.out.println("\n--- FINAL CUSTOMER BILLS ---");
                for (Customer c : customerMap.values()) {
                System.out.println(c);
                }

                // Print product summary using Person 3's method
                productManager.printProducts();
                
        } catch (IOException ex) {
                statusLabel.setText("Could not read Sales.txt.");

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("File Error");
                alert.setHeaderText("Unable to open Sales.txt");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
        }
    }

        // Abeer Ends here


        public static void main(String[] args) {
                launch(args);
        }
}
