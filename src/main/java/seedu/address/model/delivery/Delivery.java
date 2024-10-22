package seedu.address.model.delivery;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Address;

/**
 * Represents a Delivery.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Delivery {

    private final DeliveryId deliveryId;
    private final Set<ItemName> items = new HashSet<>();
    private final Address address;
    private final Cost cost;
    private final Date date;
    private final Time time;
    private final Eta eta;
    private Status status;
    private Archive archive;

    /**
     * Every field must be present and not null.
     */
    public Delivery(DeliveryId deliveryId, Set<ItemName> items, Address address, Cost cost, Date date, Time time,
                    Eta eta, Status status, Archive archive) {
        requireAllNonNull(deliveryId, address, cost, date, time, eta, status, archive, items);
        this.deliveryId = deliveryId;
        this.address = address;
        this.cost = cost;
        this.date = date;
        this.time = time;
        this.eta = eta;
        this.status = status;
        this.archive = archive;
        this.items.addAll(items);
    }

    /**
     * Every field must be present and not null.
     */
    public Delivery(Set<ItemName> items, Address address, Cost cost, Eta eta, Status status, Archive archive) {
        this(new DeliveryId(), items, address, cost, new Date(LocalDate.now().toString()),
                new Time(LocalTime.now().toString()), eta, status, archive);
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

    public DeliveryId getDeliveryId() {
        return deliveryId;
    }

    public Status getStatus() {
        return status;
    }

    public Archive getArchive() {
        return archive;
    }

    public boolean isArchived() {
        return archive.isArchived();
    }

    /**
     * Returns an immutable item set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<ItemName> getItems() {
        return Collections.unmodifiableSet(items);
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
        return address.equals(otherDelivery.address)
                && cost.equals(otherDelivery.cost)
                && date.equals(otherDelivery.date)
                && time.equals(otherDelivery.time)
                && eta.equals(otherDelivery.eta)
                && deliveryId.equals(otherDelivery.deliveryId)
                && archive.equals(otherDelivery.archive)
                && items.equals(otherDelivery.items);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("deliveryId", deliveryId)
                .add("items", items)
                .add("date", date)
                .add("time", time)
                .add("eta", eta)
                .add("address", address)
                .add("cost", cost)
                .add("status", status)
                .add("archive", archive)
                .toString();
    }

}
