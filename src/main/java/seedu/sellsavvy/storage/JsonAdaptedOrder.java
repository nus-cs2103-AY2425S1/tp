package seedu.sellsavvy.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.sellsavvy.commons.exceptions.IllegalValueException;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Order;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.model.order.Status;

/**
 * Jackson-friendly version of {@link Order}.
 */
class JsonAdaptedOrder {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Order's %s field is missing!";

    private final String quantity;
    private final String date;
    private final String item;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     * @param item represents the item to be delivered in the order.
     * @param quantity represents the item quantity requested in the order.
     * @param date represents the delivery date of the order.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("item") String item, @JsonProperty("quantity") String quantity,
                             @JsonProperty("date") String date, @JsonProperty("status") String status) {
        this.date = date;
        this.item = item;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order source) {
        item = source.getItem().fullDescription;
        date = source.getDate().value;
        quantity = source.getQuantity().value;
        status = source.getStatus().toString();
    }

    /**
     * Converts this Jackson-friendly adapted order object into the model's {@code Order} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted order.
     */
    public Order toModelType() throws IllegalValueException {
        if (item == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Item.class.getSimpleName()));
        }
        if (!Item.isValidItem(item)) {
            throw new IllegalValueException(Item.MESSAGE_CONSTRAINTS);
        }
        final Item modelItem = new Item(item);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDateRegex(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        if (!Date.isValidCalendarDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_INVALID_DATE);
        }
        final Date modelDate = new Date(date);

        if (quantity == null) {
            throw new
                    IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName()));
        }
        if (!Quantity.isValidQuantity(quantity)) {
            throw new IllegalValueException(Quantity.MESSAGE_CONSTRAINTS);
        }
        final Quantity modelQuantity = new Quantity(quantity);

        if (status == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        final Status modelStatus = Status.fromString(status);

        return new Order(modelItem, modelQuantity, modelDate, modelStatus);
    }

}
