package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;

/**
 * Represents a Delivery in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Delivery {

    private final Product product;
    private final Person sender; // CHANGE TO SUPPLIER LATER ON
    private final Status status;
    private final Time deliveryTime;
    private final Cost cost;
    private final Quantity quantity;

    /**
     * Every field must be present and not null.
     */
    public Delivery(Product product, Person sender, Status status, Time deliveryTime, Cost cost, Quantity quantity) {
        requireAllNonNull(product, sender, status, deliveryTime, cost, quantity);
        this.product = product;
        this.sender = sender;
        this.status = status;
        this.deliveryTime = deliveryTime;
        this.cost = cost;
        this.quantity = quantity;
    }

    public Product getDeliveryProduct() {
        return this.product;
    }

    public Person getDeliverySender() {
        return this.sender;
    }

    public Status getDeliveryStatus() {
        return this.status;
    }

    public Time getDeliveryDate() {
        return this.deliveryTime;
    }

    public Cost getDeliveryCost() {
        return this.cost;
    }

    public Quantity getDeliveryQuantity() {
        return this.quantity;
    }


    /**
     * Returns true if both deliveries have the same package details, sender.
     */
    public boolean isSameDelivery(Delivery otherDelivery) {
        if (otherDelivery == this) {
            return true;
        }

        // what are the conditions on same deliveries (weaker equality)
        // added the status check cos marking status needs to create a new delivery to replace the existing
        return otherDelivery != null
                && otherDelivery.getDeliveryDate().equals(getDeliveryDate())
                && otherDelivery.getDeliveryProduct().equals(getDeliveryProduct())
                && otherDelivery.getDeliverySender().equals(getDeliverySender())
                && otherDelivery.getDeliveryStatus().equals(getDeliveryStatus());
    }

    /**
     * Returns true if both deliveries have the same identity and data fields.
     */

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Delivery)) {
            return false;
        }

        Delivery otherDelivery = (Delivery) other;
        return product.equals(otherDelivery.product)
                && sender.equals(otherDelivery.sender)
                && status.equals(otherDelivery.status)
                && deliveryTime.equals(otherDelivery.deliveryTime)
                && cost.equals(otherDelivery.cost)
                && quantity.equals(otherDelivery.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, sender, status, deliveryTime, cost, quantity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("product", product)
                .add("sender", sender)
                .add("status", status)
                .add("deliveryDate", deliveryTime)
                .add("cost", cost)
                .add("quantity", quantity)
                .toString();
    }
}
