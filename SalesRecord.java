
import javafx.beans.property.*;

public class SalesRecord {

    private final StringProperty customerID;
    private final StringProperty customerName;
    private final StringProperty productID;
    private final StringProperty productName;
    private final IntegerProperty quantity;

    public SalesRecord(String customerID,
                       String customerName,
                       String productID,
                       String productName,
                       int quantity) {

        this.customerID = new SimpleStringProperty(customerID);
        this.customerName = new SimpleStringProperty(customerName);
        this.productID = new SimpleStringProperty(productID);
        this.productName = new SimpleStringProperty(productName);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public StringProperty customerIDProperty() {
        return customerID;
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public StringProperty productIDProperty() {
        return productID;
    }

    public StringProperty productNameProperty() {
        return productName;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }
}
