package seedu.ddd.storage;

import static seedu.ddd.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_VENDOR_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.INVALID_EVENT_DESC;
import static seedu.ddd.testutil.event.TypicalEventFields.INVALID_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_1;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_NAME_1;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.event.common.Description;

public class JsonAdaptedEventTest {
    private static final List<JsonAdaptedId> VALID_JSON_CLIENT_CONTACT_IDS =
            List.of(new JsonAdaptedId(DEFAULT_CLIENT_ID));
    private static final List<JsonAdaptedId> VALID_JSON_VENDOR_CONTACT_IDS =
            List.of(new JsonAdaptedId(DEFAULT_VENDOR_ID));

    // There is no test for a valid event, since the logic to add clients and vendors to an event
    // is heavily dependent on how address book is implemented.
    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
            VALID_EVENT_NAME_1,
            INVALID_EVENT_DESC,
            VALID_EVENT_DATE,
            VALID_JSON_CLIENT_CONTACT_IDS,
            VALID_JSON_VENDOR_CONTACT_IDS,
            Integer.parseInt(VALID_EVENT_ID)
        );
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
            VALID_EVENT_NAME_1,
            null,
            VALID_EVENT_DATE,
            VALID_JSON_CLIENT_CONTACT_IDS,
            VALID_JSON_VENDOR_CONTACT_IDS,
            Integer.parseInt(INVALID_EVENT_ID)
        );
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Description.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventId_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
            VALID_EVENT_NAME_1,
            VALID_EVENT_DESCRIPTION_1,
            VALID_EVENT_DATE,
            VALID_JSON_CLIENT_CONTACT_IDS,
            VALID_JSON_VENDOR_CONTACT_IDS,
            Integer.parseInt(INVALID_EVENT_ID)
        );
        String expectedMessage = Id.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

}
