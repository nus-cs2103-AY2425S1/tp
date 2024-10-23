package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a delivery's status.
 * Guarantees: is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS =
            "Status should only be \"not delivered\", \"delivering\" or \"delivered\"";

    /**
     * 3 possible delivery status.
     */
    enum DeliveryStatus {
        NOT_DELIVERED,
        DELIVERING,
        DELIVERED
    }

    private DeliveryStatus value;

    /**
     * Constructs a {@code Status}.
     */
    public Status(String status) {
        requireNonNull(status);
        status = status.toLowerCase().replace("_", " ");
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        switch(status) {
        case "not delivered":
            this.value = DeliveryStatus.NOT_DELIVERED;
            break;
        case "delivering":
            this.value = DeliveryStatus.DELIVERING;
            break;
        case "delivered":
            this.value = DeliveryStatus.DELIVERED;
            break;
        default:
            break;
        }

    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test.equals("not delivered") || test.equals("delivering") || test.equals("delivered");
    }

    /**
     * Method to get the enumerated value
     * @return Value as a string
     */
    public String getValue() {
        return String.valueOf(value).toLowerCase().replace("_", " ");
    }

    @Override
    public String toString() {
        return "Status: " + getValue();
    }
}
