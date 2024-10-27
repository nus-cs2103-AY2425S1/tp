package seedu.sellsavvy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.sellsavvy.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.sellsavvy.testutil.Assert.assertThrows;
import static seedu.sellsavvy.testutil.TypicalOrders.BLOCKS;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.commons.exceptions.IllegalValueException;
import seedu.sellsavvy.model.order.Quantity;
import seedu.sellsavvy.model.order.Date;
import seedu.sellsavvy.model.order.Item;
import seedu.sellsavvy.model.order.Status;

public class JsonAdaptedOrderTest {
    private static final String INVALID_ITEM = " ";
    private static final String INVALID_QUANTITY = "0";
    private static final String INVALID_DATE = "2-2-2";
    private static final String INVALID_STATUS = "not pending";

    private static final String VALID_ITEM = BLOCKS.getItem().toString();
    private static final String VALID_QUANTITY = BLOCKS.getQuantity().toString();
    private static final String VALID_DATE = BLOCKS.getDate().toString();
    private static final String VALID_STATUS = BLOCKS.getStatus().toString();

    @Test
    public void toModelType_validOrderDetails_returnsOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(BLOCKS);
        assertEquals(BLOCKS, order.toModelType());
    }

    @Test
    public void toModelType_invalidItem_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(INVALID_ITEM, VALID_QUANTITY, VALID_DATE, VALID_STATUS);
        String expectedMessage = Item.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullItem_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(null, VALID_QUANTITY, VALID_DATE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Item.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, INVALID_QUANTITY, VALID_DATE, VALID_STATUS);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, null, VALID_DATE, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY, INVALID_DATE, VALID_STATUS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY, null, VALID_STATUS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY, VALID_DATE, INVALID_STATUS);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ITEM, VALID_QUANTITY, VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }
}
