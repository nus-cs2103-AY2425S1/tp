package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Supplier;

/**
 * Represents a Delivery in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Delivery {

    private final Product product;
    private Supplier sender;
    private Status status;
    private final DateTime deliveryDateTime;
    private final Cost cost;
    private final Quantity quantity;

    /**
     * Every field must be present and not null.
     */
    public Delivery(Product product, Supplier sender, Status status,
                    DateTime deliveryDateTime, Cost cost, Quantity quantity) {
        requireAllNonNull(product, status, deliveryDateTime, cost, quantity);
        this.product = product;
        this.sender = sender;
        this.status = status;
        this.deliveryDateTime = deliveryDateTime;
        this.cost = cost;
        this.quantity = quantity;
    }
    public Product getDeliveryProduct() {
        return this.product;
    }

    public Supplier getDeliverySender() {
        return this.sender;
    }

    public Status getDeliveryStatus() {
        return this.status;
    }

    public DateTime getDeliveryDate() {
        return this.deliveryDateTime;
    }

    public Cost getDeliveryCost() {
        return this.cost;
    }

    public Quantity getDeliveryQuantity() {
        return this.quantity;
    }
    public void setDeliverySender(Supplier sender) {
        this.sender = sender;
    }

    /**
     * Checks if current instance of delivery has an earlier due date than input date.
     *
     * @param deliveryDateTime DateTime object to compare against.
     * @return True if current delivery is to be completed before input delivery time.
     */
    public boolean hasEarlierDateThan(DateTime deliveryDateTime) {
        return this.deliveryDateTime.isEarlierThan(deliveryDateTime);
    }

    /**
     * Checks if current instance of delivery has a later due date than input date.
     *
     * @param deliveryDateTime DateTime object to compare against.
     * @return True if current delivery is to be completed after input delivery time.
     */
    public boolean hasLaterDateThan(DateTime deliveryDateTime) {
        return this.deliveryDateTime.isLaterThan(deliveryDateTime);
    }

    /**
     * Checks if current instance of delivery has same status as input status.
     *
     * @param deliveryStatus Status object to compare against.
     * @return True if current delivery have same status as input status.
     */
    public boolean hasSameStatus(Status deliveryStatus) {
        return this.status.equals(deliveryStatus);
    }

    /**
     * Checks if current instance of delivery has same DateTime as input DateTime.
     *
     * @param targetDate DateTime object to compare against.
     * @return True if current delivery have same DateTime as input DateTime.
     */
    public boolean hasSameDate(DateTime targetDate) {
        return this.deliveryDateTime.equals(targetDate);
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
                && otherDelivery.getDeliveryStatus().equals(getDeliveryStatus())
                && otherDelivery.getDeliveryCost().equals(getDeliveryCost())
                && otherDelivery.getDeliveryQuantity().equals(getDeliveryQuantity());
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
        boolean isSameSender = this.sender == null || otherDelivery.sender == null
                             ? otherDelivery.sender == this.sender
                             : this.sender.equals(otherDelivery.sender);
        return product.equals(otherDelivery.product)
                && status.equals(otherDelivery.status)
                && deliveryDateTime.equals(otherDelivery.deliveryDateTime)
                && cost.equals(otherDelivery.cost)
                && quantity.equals(otherDelivery.quantity)
                && isSameSender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, sender, status, deliveryDateTime, cost, quantity);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("product", product)
                .add("sender", sender)
                .add("status", status)
                .add("deliveryDate", deliveryDateTime)
                .add("cost", cost)
                .add("quantity", quantity)
                .toString();
    }
}
