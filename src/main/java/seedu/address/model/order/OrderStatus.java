package seedu.address.model.order;

public enum OrderStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String description;

    // Constructor
    OrderStatus(String description) {
        this.description = description;
    }

    // Override toString() method
    @Override
    public String toString() {
        return description;
    }
}
