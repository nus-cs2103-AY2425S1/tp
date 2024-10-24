package seedu.address.storage;

import static seedu.address.storage.JsonAdaptedDelivery.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.JsonAdaptedPersonTest.VALID_TAGS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalDeliveries.APPLES;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.delivery.Archive;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.Date;
import seedu.address.model.delivery.DeliveryId;
import seedu.address.model.delivery.Eta;
import seedu.address.model.delivery.ItemName;
import seedu.address.model.delivery.Status;
import seedu.address.model.delivery.Time;
import seedu.address.model.person.Address;

public class JsonAdaptedDeliveryTest {
    private static final List<JsonAdaptedItem> INVALID_ITEM_NAME = Arrays.asList(new JsonAdaptedItem("$%"));
    private static final String INVALID_ADDRESS = "123";
    private static final String INVALID_COST = "thirty";
    private static final String INVALID_DATE = "tomorrow";
    private static final String INVALID_TIME = "5 pm";
    private static final String INVALID_ETA = "tomorrow";
    private static final String INVALID_STATUS = "complete";
    private static final String INVALID_ARCHIVE = "yes";

    private static final String VALID_DELIVERY_ID = APPLES.getDeliveryId().value;
    private static final List<JsonAdaptedItem> VALID_ITEM_NAME = APPLES.getItems().stream()
            .map(JsonAdaptedItem::new)
            .collect(Collectors.toList());
    private static final String VALID_ADDRESS = APPLES.getAddress().value;
    private static final String VALID_COST = APPLES.getCost().value;
    private static final String VALID_DATE = String.valueOf(APPLES.getDate().value);
    private static final String VALID_TIME = String.valueOf(APPLES.getTime().value);
    private static final String VALID_ETA = String.valueOf(APPLES.getEta().value);
    private static final String VALID_STATUS = APPLES.getStatus().getValue();
    private static final String VALID_ARCHIVE = APPLES.getArchive().value;

    @Test
    public void toModelType_nullDeliveryId_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(null, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DeliveryId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidItemName_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, INVALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = ItemName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_emptyItems_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, null, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "items");
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, INVALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, null, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidCost_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, INVALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = Cost.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullCost_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, null, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Cost.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, INVALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, null,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        INVALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        null, VALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidEta_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, INVALID_ETA, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = Eta.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullEta_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, null, VALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Eta.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, INVALID_STATUS, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, null, VALID_TAGS, VALID_ARCHIVE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_invalidArchive_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, INVALID_ARCHIVE);
        String expectedMessage = Archive.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }

    @Test
    public void toModelType_nullArchive_throwsIllegalValueException() {
        JsonAdaptedDelivery delivery =
                new JsonAdaptedDelivery(VALID_DELIVERY_ID, VALID_ITEM_NAME, VALID_ADDRESS, VALID_COST, VALID_DATE,
                        VALID_TIME, VALID_ETA, VALID_STATUS, VALID_TAGS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Archive.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, delivery::toModelType);
    }
}
