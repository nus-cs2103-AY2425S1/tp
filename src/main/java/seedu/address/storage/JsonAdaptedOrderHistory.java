package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderHistory;

/**
 * Jackson-friendly version of {@link OrderHistory}.
 */
class JsonAdaptedOrderHistory {

    private final String order;
    private final String timestamp;

    /**
     * Constructs a {@code JsonAdaptedOrderHistory} with the given order details.
     */
    @JsonCreator
    public JsonAdaptedOrderHistory(@JsonProperty("order") String order, @JsonProperty("timestamp") String timestamp) {
        this.order = order;
        this.timestamp = timestamp;
    }

    /**
     * Converts a given {@code OrderHistory} into this class for Jackson use.
     */
    public JsonAdaptedOrderHistory(OrderHistory orderHistory) {
        this.order = String.valueOf(orderHistory.getOrder());
        this.timestamp = orderHistory.getTime().toString();
    }

    /**
     * Converts this Jackson-friendly adapted OrderHistory object into the model's {@code OrderHistory} object.
     *
     */
    public OrderHistory toModelType() throws IllegalValueException {
        LocalDateTime parsedTime;
        try {
            parsedTime = LocalDateTime.parse(this.timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid timestamp format.");
        }
        return new OrderHistory(new Order(order), parsedTime);
    }
}
