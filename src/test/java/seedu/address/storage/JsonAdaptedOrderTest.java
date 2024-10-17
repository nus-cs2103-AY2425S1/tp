package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.order.Order;

class JsonAdaptedOrderTest {

    @Test
    void toModelType_validOrder_success() {
        JsonAdaptedOrder jsonOrder = new JsonAdaptedOrder("cake");
        assertEquals(new Order("cake"), jsonOrder.toModelType());
    }
}
