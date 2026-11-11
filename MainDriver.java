
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

public class MainDriver extends Application {

    private ObservableList<SalesRecord> salesRecords =
            FXCollections.observableArrayList();

    private TableView<SalesRecord> table;
    private Label statusLabel;

    @Override
    public void start(Stage stage) {

        Label title = new Label("Humber Sales Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button loadButton = new Button("Load Sales Records");

        statusLabel = new Label("Click the button to load sales records.");

        table = new TableView<>();

        TableColumn<SalesRecord, String> customerID =
                new TableColumn<>("Customer ID");
        customerID.setCellValueFactory(
                data -> data.getValue().customerIDProperty());

        TableColumn<SalesRecord, String> customerName =
                new TableColumn<>("Customer Name");
        customerName.setCellValueFactory(
                data -> data.getValue().customerNameProperty());

        TableColumn<SalesRecord, String> productID =
                new TableColumn<>("Product ID");
        productID.setCellValueFactory(
                data -> data.getValue().productIDProperty());

        TableColumn<SalesRecord, String> productName =
                new TableColumn<>("Product Name");
        productName.setCellValueFactory(
                data -> data.getValue().productNameProperty());

        TableColumn<SalesRecord, Integer> quantity =
                new TableColumn<>("Quantity");
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

    // Read and process Sales.txt
    private void loadSalesFile() {

        salesRecords.clear();

        int totalRecords = 0;

        try (BufferedReader reader =
                new BufferedReader(new FileReader("Sales.txt"))) {

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

                    if (customerID.isEmpty()
                            || customerName.isEmpty()
                            || productID.isEmpty()
                            || productName.isEmpty()
                            || quantity <= 0) {
                        continue;
                    }

                    updateCustomer(customerID, customerName);

                    updateProduct(productID, productName, quantity);

                    salesRecords.add(new SalesRecord(
                            customerID, customerName,
                            productID, productName, quantity));

                    totalRecords++;

                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity: " + line);
                }
            }

            statusLabel.setText(
                    "Total records processed: " + totalRecords);

        } catch (IOException ex) {

            statusLabel.setText("Could not read Sales.txt.");

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File Error");
            alert.setHeaderText("Unable to open Sales.txt");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    // Customer update method
    public static void updateCustomer(
            String customerID, String customerName) {

        System.out.println("Customer updated: "
                + customerID + " - " + customerName);
    }

    // Product update method
    public static void updateProduct(
            String productID, String productName, int quantity) {

        System.out.println("Product updated: "
                + productID + " - " + productName
                + " - Quantity: " + quantity);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
