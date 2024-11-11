package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.model.order.Order;

class JsonAdaptedOrderTest {

    @Test
    void toModelType_validOrder_success() {
        JsonAdaptedOrder jsonOrder = new JsonAdaptedOrder("cake");
        try {
            assertEquals(new Order("cake"), jsonOrder.toModelType());
        } catch (Exception e) {
            fail();
        }
    }
}
