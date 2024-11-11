package seedu.sellsavvy.testutil;

import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.model.order.Status;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {
    public static final String DEFAULT_ITEM = "Bottle";
    public static final String DEFAULT_QUANTITY = "1";
    public static final String DEFAULT_DATE = "01-03-2025";
    public static final Status DEFAULT_STATUS = Status.PENDING;

    private Item item;
    private Quantity quantity;
    private Date date;
    private Status status;

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder() {
        item = new Item(DEFAULT_ITEM);
        quantity = new Quantity(DEFAULT_QUANTITY);
        date = new Date(DEFAULT_DATE);
        status = DEFAULT_STATUS;
    }

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder(Order orderToCopy) {
        item = orderToCopy.getItem();
        quantity = orderToCopy.getQuantity();
        date = orderToCopy.getDate();
        status = orderToCopy.getStatus();
    }

    /**
     * Sets the {@code Item} of the {@code Order} that we are building.
     */
    public OrderBuilder withItem(String item) {
        this.item = new Item(item);
        return this;
    }

    /**
     * Sets the {@code Quantity} of the {@code Order} that we are building.
     */
    public OrderBuilder withQuantity(String quantity) {
        this.quantity = new Quantity(quantity);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Order} that we are building.
     */
    public OrderBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Order} that we are building.
     */
    public OrderBuilder withStatus(Status status) {
        this.status = status;
        return this;
    }

    public Order build() {
        return new Order(item, quantity, date, status);
    }
}
