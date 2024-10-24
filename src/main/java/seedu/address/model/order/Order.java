package seedu.address.model.order;

import seedu.address.model.product.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstract class representing a general order.
 */
public abstract class Order {
    private static int nextId = 1; // Static field to track the next available order ID
    private int orderId;           // Unique ID for each order
    private String phoneNumber; //may be better to store "person" object or "Phone" in the future
    private LocalDateTime orderDate;
    private List<? extends Product> items; //stores list of pastries/ingredients
    private OrderStatus status;

    // Constructor
    public Order(String phoneNumber, List<? extends Product> items, OrderStatus status) {
        this.orderId = nextId++;  // Assign the current value of nextId and increment
        this.phoneNumber = phoneNumber;
        this.orderDate = LocalDateTime.now();
        this.items = items;
        this.status = status;
    }

    // Getters and Setters
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderDate() {
        return orderDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<? extends Product> getItems() {
        return items;
    }

    public void setItems(List<? extends Product> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getOrderType();

    @Override
    public String toString() {
        return "Order{" +
                "PhoneNumber='" + phoneNumber + '\'' +
                ", orderDate=" + orderDate +
                ", items=" + items +
                ", status='" + status + '\'' +
                '}';
    }

    public String viewOrder() {
        StringBuilder sb = new StringBuilder();

        for (Product entry: items) {
            sb.append(entry.viewProduct());
            sb.append("\n");
        }

        return sb.toString();
//        return "Phone Number: " + phoneNumber + "\n" +
//                "Order Date: " + orderDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n" +
//                "Items: \n" + sb.toString();
    }
}
