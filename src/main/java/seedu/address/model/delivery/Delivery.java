package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;

/**
 * Represents a Delivery.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Delivery {

    private final ItemId itemId;
    private final Address address;

    private final Cost cost;

    private final Date date;

    private final Time time;

    private final Eta eta;

    /**
     * Every field must be present and not null.
     */
    public Delivery(ItemId itemId, Address address, Cost cost, Date date, Time time, Eta eta) {
        requireAllNonNull(itemId, address, cost, date, time, eta);
        this.itemId = itemId;
        this.address = address;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.eta = eta;
    }

    /**
     * Every field must be present and not null.
     */
    public Delivery(ItemId itemId, Address address, Cost cost, Eta eta) {
        this(itemId, address, cost, new Date(LocalDate.now().toString()), new Time(LocalTime.now().toString()), eta);
    }

    public ItemId getItemId() {
        return itemId;
    }

    public Address getAddress() {
        return address;
    }

    public Cost getCost() {
        return cost;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Eta getEta() {
        return eta;
    }

    /**
     * Returns true if both deliveries are equal.
     */
    public boolean isSameDelivery(Delivery otherDelivery) {
        if (otherDelivery == this) {
            return true;
        }

        return equals(otherDelivery);
    }

    /**
     * Returns true if both deliveries have the same identity and data fields.
     * This defines a stronger notion of equality between two deliveries.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Delivery)) {
            return false;
        }

        Delivery otherDelivery = (Delivery) other;
        return itemId.equals(otherDelivery.itemId)
                && address.equals(otherDelivery.address)
                && cost.equals(otherDelivery.cost)
                && date.equals(otherDelivery.date)
                && time.equals(otherDelivery.time)
                && eta.equals(otherDelivery.eta);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("itemId", itemId)
                .add("date", date)
                .add("time", time)
                .add("eta", eta)
                .add("address", address)
                .add("cost", cost)
                .toString();
    }

}
