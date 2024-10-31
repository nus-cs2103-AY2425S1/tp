package seedu.address.model.order;

import seedu.address.model.person.Person;
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
    private LocalDateTime orderDate;
    private List<? extends Product> items; //stores list of pastries/ingredients
    private OrderStatus status;
    private Person person;

    // Constructor
    public Order(Person person, List<? extends Product> items, OrderStatus status) {
        this.orderId = nextId++;  // Assign the current value of nextId and increment
        this.orderDate = LocalDateTime.now();
        this.items = items;
        this.status = status;
        this.person = person;
    }

    public Person getPerson() {
        return person;
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

    public int getOrderId() { return orderId; }

    // Abstract method to be implemented by subclasses
    public abstract String getOrderType();

    @Override
    public String toString() {

        return "Order Type: " + getOrderType() + "\n" +
                "Order Date: " + orderDate + "\n" +
                "Status: " + status + "\n" +
                "Items: " + "\n" +
                viewOrder();
    }

    public String viewOrder() {
        StringBuilder sb = new StringBuilder();

        for (Product entry: items) {
            sb.append(entry.viewProduct());
            sb.append("\n");
        }

        return sb.toString();
    }
}
