package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.order.Order;
import seedu.address.model.order.OrderHistory;

class JsonAdaptedOrderHistoryTest {

    @Test
    void toModelType_invalidTimeStamp_throwsIllegalValueException() {
        JsonAdaptedOrderHistory orderHistory = new JsonAdaptedOrderHistory("cake",
                                        "Invalid timeStamp");
        assertThrows(IllegalValueException.class, orderHistory::toModelType);
    }

    @Test
    void toModelType_validInput_success() {
        LocalDateTime time = LocalDateTime.of(24, 12, 15, 01, 30);
        OrderHistory history = new OrderHistory(new Order("pizza"), time);
        JsonAdaptedOrderHistory jsonOrderHistory = new JsonAdaptedOrderHistory(history);

        try {
            assertEquals(history, jsonOrderHistory.toModelType());
        } catch (IllegalValueException e) {
            System.out.println(e);
        }
    }
}