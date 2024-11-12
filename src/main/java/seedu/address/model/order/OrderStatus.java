package seedu.address.model.order;

/**
 * Represents the possible statuses of an order in the system.
 * Each status has a description that is used for display purposes.
 */
public enum OrderStatus {
    /**
     * Status representing an order that has been created but not yet completed.
     */
    PENDING("Pending"),

    /**
     * Status representing an order that has been successfully completed.
     */
    COMPLETED("Completed"),

    /**
     * Status representing an order that has been cancelled and will not be processed further.
     */
    CANCELLED("Cancelled");

    private final String description;

    /**
     * Constructs an {@code OrderStatus} with a specified description.
     *
     * @param description The display description of the order status.
     */
    OrderStatus(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the order status.
     *
     * @return The description of this status.
     */
    @Override
    public String toString() {
        return description;
    }
}
