package seedu.sellsavvy.model.order;

import static seedu.sellsavvy.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.sellsavvy.commons.util.ToStringBuilder;

/**
 * Represents an Order made by a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Order {

    // Item fields
    private final Item item;
    private final Quantity quantity;

    // Data fields
    private final Date date;
    private final Status status;

    /**
     * Every field must be present and not null.
     */
    public Order(Item item, Quantity quantity, Date date, Status status) {
        requireAllNonNull(item, quantity, date);
        this.item = item;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
    }

    public Item getItem() {
        return item;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Date getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Returns true if both orders have the same item.
     * This defines a weaker notion of equality between two orders.
     */
    public boolean isSameOrder(Order otherOrder) {
        if (otherOrder == this) {
            return true;
        }

        return otherOrder != null
                && otherOrder.getItem().equals(getItem());
    }

    /**
     * Returns if order date has passed.
     */
    public boolean hasDateElapsed() {
        return date.hasDateElapsed();
    }

    /**
     * Returns true if both orders have the same identity and data fields.
     * This defines a stronger notion of equality between two orders.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Order)) {
            return false;
        }

        Order otherOrder = (Order) other;
        return item.equals(otherOrder.item)
                && quantity.equals(otherOrder.quantity)
                && date.equals(otherOrder.date)
                && status.equals(otherOrder.status);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(item, quantity, date, status);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("item", item)
                .add("quantity", quantity)
                .add("date", date)
                .add("status", status)
                .toString();
    }

}
