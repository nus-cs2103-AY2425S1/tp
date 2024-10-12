package seedu.address.model.delivery;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalDeliveries.APPLE;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.DeliveryBuilder;
public class DeliveryTest {
    @Test
    public void isSameDelivery() {
        // same object -> returns true
        assertTrue(APPLE.isSameDelivery(APPLE));

        // null -> returns false
        assertFalse(APPLE.isSameDelivery(null));

        // different product name, all other attributes same -> returns false
        Delivery editedApple = new DeliveryBuilder(APPLE).withProduct("Iphone12").build();
        assertFalse(APPLE.isSameDelivery(editedApple));


    }
}
