package seedu.address.model.delivery;

import java.util.Random;

/**
 * Represents a delivery's unique ID.
 */
public class DeliveryId {
    public final String value;

    public DeliveryId() {
        value = System.currentTimeMillis() + "-" + String.format("%04d", new Random().nextInt(1000));
    }

    @Override
    public String toString() {
        return "ID: " + value;
    }
}
