package seedu.address.model.delivery;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Status {

    public static final String MESSAGE_CONSTRAINTS =
            "Status should only be \"not delivered\", \"delivering\" or \"delivered\"";
    enum deliveryStatus {
        NOT_DELIVERED,
        DELIVERING,
        DELIVERED
    }

    public deliveryStatus value;

    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        switch(status) {
        case "not delivered":
            this.value = deliveryStatus.NOT_DELIVERED;
            break;
        case "delivering":
            this.value = deliveryStatus.DELIVERING;
            break;
        case "delivered":
            this.value = deliveryStatus.DELIVERED;
            break;
        }

    }

    public static boolean isValidStatus(String test) {
        return test.equals("not delivered") || test.equals("delivering") || test.equals("delivered");
    }

    @Override
    public String toString() {
        return "Status: " + value;
    }
}
