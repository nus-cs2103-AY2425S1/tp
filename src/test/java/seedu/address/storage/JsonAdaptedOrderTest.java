package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
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

    @Test
    void toModelType_invalidOrder_throwIllegalValueException() {
        JsonAdaptedOrder jsonOrder = new JsonAdaptedOrder("cak.e");
        assertThrows(IllegalValueException.class, jsonOrder::toModelType);
    }
}
