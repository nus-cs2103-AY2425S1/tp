package seedu.address.model.order;

import seedu.address.model.person.Phone;

import java.util.Date;
import java.util.List;

/**
 * Abstract class representing a general order.
 */
public abstract class Order {
    private String phoneNumber; //may be better to store "person" object or "Phone" in the future
    private Date orderDate;
    private List<? extends Product> items; //stores list of pastries/ingredients
    private String status;  // e.g., "Pending", "Completed", "Cancelled"

    // Constructor
    public Order(String phoneNumber, Date orderDate, List<? extends Product> items, String status) {
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
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

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<? extends Product> getItems() {
        return items;
    }

    public void setItems(List<? extends Product> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}
