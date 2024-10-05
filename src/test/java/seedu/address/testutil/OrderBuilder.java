package seedu.address.testutil;

import seedu.address.model.order.Item;
import seedu.address.model.order.Count;
import seedu.address.model.order.Order;
import seedu.address.model.order.Date;

/**
 * A utility class to help with building Order objects.
 */
public class OrderBuilder {
    public static final String DEFAULT_ITEM = "Bottle";
    public static final String DEFAULT_COUNT = "1";
    public static final String DEFAULT_DATE = "01-03-2025";

    private Item item;
    private Count count;
    private Date date;

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder() {
        item = new Item(DEFAULT_ITEM);
        count = new Count(DEFAULT_COUNT);
        date = new Date(DEFAULT_DATE);
    }

    /**
     * Creates a {@code OrderBuilder} with the default details.
     */
    public OrderBuilder(Order orderToCopy) {
        item = orderToCopy.getItem();
        count = orderToCopy.getCount();
        date = orderToCopy.getDate();
    }

    /**
     * Sets the {@code Item} of the {@code Order} that we are building.
     */
    public OrderBuilder withItem(String item) {
        this.item = new Item(item);
        return this;
    }

    /**
     * Sets the {@code Count} of the {@code Order} that we are building.
     */
    public OrderBuilder withCount(String count) {
        this.count = new Count(count);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Order} that we are building.
     */
    public OrderBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    public Order build() {
        return new Order(item, count, date);
    }
}
