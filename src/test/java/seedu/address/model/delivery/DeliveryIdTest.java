package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeliveryIdTest {
    @Test
    public void testDeliveryIdFormat() {
        DeliveryId deliveryId = new DeliveryId();
        String value = deliveryId.value;

        assertTrue(value.matches("\\d{13}-\\d{4}"), "ID should match the format 'timestamp-4digits'");
    }

    @Test
    public void testDeliveryIdLength() {
        DeliveryId deliveryId = new DeliveryId();
        String value = deliveryId.value;

        assertEquals(18, value.length(), "ID should be 18 characters long");
    }

    @Test
    public void testDeliveryIdUniqueness() {
        DeliveryId deliveryId1 = new DeliveryId();
        DeliveryId deliveryId2 = new DeliveryId();

        assertNotEquals(deliveryId1.value, deliveryId2.value, "Two generated IDs should be unique");
    }

    @Test
    public void testDeliveryIdNotNull() {
        DeliveryId deliveryId = new DeliveryId();

        assertNotNull(deliveryId.value, "Generated ID should not be null");
    }
}
