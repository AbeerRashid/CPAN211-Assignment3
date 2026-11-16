public class SalesRecord {
    private String customerID;
    private String customerName;
    private String productID;
    private String productName;
    private int quantity;

    public SalesRecord(String customerID, String customerName, String productID, String productName, int quantity) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.productID = productID;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getCustomerID() { return customerID; }
    public String getCustomerName() { return customerName; }
    public String getProductID() { return productID; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }

    @Override
    public String toString() {
        return "Customer: " + customerName + " (" + customerID + ") | Product: " + productName + " | Qty: " + quantity;
    }
}
