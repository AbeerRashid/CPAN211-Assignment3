public class Customer {
   private int customerID;
   private String firstName;
   private String lastName;
   private double totalBill;
   public Customer(int customerID, String firstName, String lastName) {
       this.customerID = customerID;
       this.firstName = firstName;
       this.lastName = lastName;
       this.totalBill = 0.0;
   }
   // Getters
   public int getCustomerID() {
       return customerID;
   }
   public String getFirstName() {
       return firstName;
   }
   public String getLastName() {
       return lastName;
   }
   public double getTotalBill() {
       return totalBill;
   }
   /**
    * Adds the cost of a purchase to this customer's total bill.
    *
    * @param singleItemPrice price of one unit
    * @param quantity number of items purchased
    */
   public void addToBill(double singleItemPrice, int quantity) {
       if (singleItemPrice < 0 || quantity < 0) {
           throw new IllegalArgumentException(
                   "Price and quantity must be non-negative.");
       }
       this.totalBill += singleItemPrice * quantity;
   }
   /**
    * This Creates a new Customer from a sales record. Assumes the record has
    * already been validated (I think that makes sense?)
    */
   public static Customer fromRecord(int id, String first, String last) {
       return new Customer(id, first, last);
   }
   @Override
   public String toString() {
       return String.format("%-8d %-12s %-12s $%,.2f",
               customerID, firstName, lastName, totalBill);
   }
}