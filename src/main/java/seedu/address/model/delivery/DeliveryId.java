package seedu.address.model.delivery;

import java.util.Random;

/**
 * Represents a delivery's unique ID.
 */
public class DeliveryId {
    public static final String VALIDATION_REGEX = "^\\d{13}-\\d{4}$";

    public final String value;

    public DeliveryId(String value) {
        this.value = value;
    }
    public DeliveryId() {
        value = System.currentTimeMillis() + "-" + String.format("%04d", new Random().nextInt(1000));
    }

    public static boolean isValidDeliveryId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return "ID: " + value;
    }
}
