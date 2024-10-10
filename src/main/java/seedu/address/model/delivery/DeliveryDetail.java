package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a details in regard to a delivery in the system.
 */
public class DeliveryDetail {
    private final String title;
    private final String description;

    private final double quantity;

    private final double cost;

    /**
     * Every field must be present and not null.
     */
    public DeliveryDetail(String name, String description, Double quantity, Double cost) {
        requireAllNonNull(name, description, quantity, cost);
        this.title = name;
        this.description = description;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getName() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("Title: %s | Descrption: %s", this.title, this.description);
    }
}
