package seedu.address.model.order;

import java.util.Date;
import java.util.List;

public abstract class Order {
    private String orderId;
    private Date orderDate;
    private List<String> items;
    private String status;  // e.g., "Pending", "Completed", "Cancelled"
    private double totalAmount;

    // Constructor
    public Order(String orderId, Date orderDate, List<String> items, String status, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.items = items;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getOrderType();

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderDate=" + orderDate +
                ", items=" + items +
                ", status='" + status + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
