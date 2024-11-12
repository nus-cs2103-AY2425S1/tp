package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedOrder {

    private final String name;

    /**
     * Constructs a {@code JsonAdaptedOrder} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrder(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Converts a given {@code Order} into this class for Jackson use.
     */
    public JsonAdaptedOrder(Order order) {
        this.name = order.toString();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Order} object.
     *
     */
    public Order toModelType() throws IllegalValueException {
        if (!Order.isValidName(name)) {
            throw new IllegalValueException(Order.MESSAGE_CONSTRAINTS);
        }
        return new Order(this.name);
    }
}
