package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents a Delivery in the application.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Delivery {

    private final DeliveryDetail deliveryDetail;
    // CHANGE TO SUPPLIER LATER ON
    private final Person sender;

    // Data fields
    private final DeliveryStatus status;
    private final DeliveryTime deliveryTime;

    /**
     * Every field must be present and not null.
     */
    public Delivery(DeliveryDetail deliveryDetail, Person sender, DeliveryStatus status, DeliveryTime deliveryTime) {
        requireAllNonNull(deliveryDetail, sender, status, deliveryTime);
        this.deliveryDetail = deliveryDetail;
        this.sender = sender;
        this.status = status;
        this.deliveryTime = deliveryTime;
    }

    public DeliveryDetail getDeliveryDetails() {
        return deliveryDetail;
    }

    public Person getSender() {
        return this.sender;
    }

    public DeliveryStatus getStatus() {
        return this.status;
    }

    public DeliveryTime getDeliveryDate() {
        return this.deliveryTime;
    }

    /**
     * Returns true if both deliveries have the same package details, sender, and receiver.
     */
    public boolean isSameDelivery(Delivery otherDelivery) {
        if (otherDelivery == this) {
            return true;
        }

        return otherDelivery != null
                && otherDelivery.getDeliveryDate().equals(getDeliveryDate())
                && otherDelivery.getDeliveryDetails().equals(getDeliveryDetails())
                && otherDelivery.getSender().equals(getSender());
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
        return deliveryDetail.equals(otherDelivery.deliveryDetail)
                && sender.equals(otherDelivery.sender)
                && status.equals(otherDelivery.status)
                && deliveryTime.equals(otherDelivery.deliveryTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deliveryDetail, sender, status, deliveryTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("packageDetails", deliveryDetail)
                .add("sender", sender)
                .add("status", status)
                .add("deliveryDate", deliveryTime)
                .toString();
    }
}
