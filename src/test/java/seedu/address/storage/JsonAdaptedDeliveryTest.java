package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedDelivery.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.APPLE;
import static seedu.address.testutil.TypicalDeliveries.BREAD;
import static seedu.address.testutil.TypicalPersons.CARL;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.person.Person;
import seedu.address.model.product.Product;


public class JsonAdaptedDeliveryTest {
    private static final String INVALID_PRODUCT = "Iphone 16@";
    private static final String INVALID_TIME = "1-1-1 10:00";
    private static final String INVALID_COST = "100.";
    private static final String INVALID_QUANTITY = "500 PCS";
    private static final String INVALID_SUPPLIER_INDEX = "abc";
    private static final String VALID_PRODUCT = BREAD.getDeliveryProduct().toString();
    private static final JsonAdaptedPerson VALID_SENDER = new JsonAdaptedPerson(CARL);
    private static final String VALID_TIME = APPLE.getDeliveryDate().toString();
    private static final String VALID_COST = APPLE.getDeliveryCost().toString();
    private static final String VALID_QUANTITY = APPLE.getDeliveryQuantity().toString();
    private static final String VALID_SUPPLIER_INDEX = APPLE.getSupplierIndex().toString();


    @Test
    public void toModelType_validDeliveryDetails_returnsDelivery() throws Exception {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(BREAD);
        assertEquals(BREAD, delivery.toModelType());
    }

    @Test
    public void toModelType_invalidProductName_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(INVALID_PRODUCT, Status.PENDING.toString(), VALID_TIME,
                VALID_COST, VALID_QUANTITY, VALID_SENDER);
        String expectedMessage = Product.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullProductName_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(null, Status.PENDING.toString(), VALID_TIME,
                VALID_COST, VALID_QUANTITY, VALID_SENDER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Product.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_PRODUCT, Status.PENDING.toString(), INVALID_TIME,
                VALID_COST, VALID_QUANTITY, VALID_SENDER);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_PRODUCT, Status.PENDING.toString(), null,
                VALID_COST, VALID_QUANTITY, VALID_SENDER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidCost_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_PRODUCT, Status.PENDING.toString(), VALID_TIME,
                INVALID_COST, VALID_QUANTITY, VALID_SENDER);
        String expectedMessage = Cost.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullCost_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_PRODUCT, Status.PENDING.toString(), VALID_TIME,
                null, VALID_QUANTITY, VALID_SENDER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_PRODUCT, Status.PENDING.toString(), VALID_TIME,
                VALID_COST, INVALID_QUANTITY, VALID_SENDER);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_PRODUCT, Status.PENDING.toString(), VALID_TIME,
                VALID_COST, null, VALID_SENDER);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullSender_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery = new JsonAdaptedDelivery(VALID_PRODUCT, Status.PENDING.toString(), VALID_TIME,
                VALID_COST, VALID_QUANTITY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Person.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

}
