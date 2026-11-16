import java.util.HashMap;

public class Product {

    private HashMap<Integer, Integer> products = new HashMap<>();
    private HashMap<Integer, String> productNames = new HashMap<>();

    // Add or update product quantity //
    public void updateProduct(int productID, String productName, int quantity) {

        productNames.put(productID, productName);

        int total = products.getOrDefault(productID, 0);
        products.put(productID, total + quantity);
    }

    // Display product summary //
    public void printProducts() {

        System.out.println("\nProduct Summary");

        for (int id : products.keySet()) {
            System.out.println("Product ID: " + id
                    + " | Name: " + productNames.get(id)
                    + " | Total Purchased: " + products.get(id));
        }
    }
}
