package seedu.ddd.storage;

import static seedu.ddd.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_CLIENT_ID;
import static seedu.ddd.testutil.contact.TypicalContactFields.DEFAULT_VENDOR_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.INVALID_EVENT_DESCRIPTION;
import static seedu.ddd.testutil.event.TypicalEventFields.INVALID_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_1;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_NAME;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.exceptions.IllegalValueException;
import seedu.ddd.model.event.common.Description;
import seedu.ddd.model.event.common.EventId;

public class JsonAdaptedEventTest {

    private static final List<JsonAdaptedContactId> VALID_JSON_CLIENT_CONTACT_IDS =
            List.of(new JsonAdaptedContactId(DEFAULT_CLIENT_ID));
    private static final List<JsonAdaptedContactId> VALID_JSON_VENDOR_CONTACT_IDS =
            List.of(new JsonAdaptedContactId(DEFAULT_VENDOR_ID));

    // @Test
    // public void toModelType_validEventDetails_returnsEvent() throws Exception {
    //     JsonAdaptedEvent event = new JsonAdaptedEvent(
    //         VALID_EVENT_NAME,
    //         VALID_EVENT_DESCRIPTION_1,
    //         VALID_EVENT_DATE,
    //         DEFAULT_JSON_CLIENT_CONTACT_ID_LIST,
    //         DEFAULT_JSON_VENDOR_CONTACT_ID_LIST,
    //         Integer.parseInt(VALID_EVENT_ID)
    //     );
    //     assertEquals(VALID_EVENT, event.toModelType());
    // }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(
            VALID_EVENT_NAME,
            INVALID_EVENT_DESCRIPTION,
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
            VALID_EVENT_NAME,
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
            VALID_EVENT_NAME,
            VALID_EVENT_DESCRIPTION_1,
            VALID_EVENT_DATE,
            VALID_JSON_CLIENT_CONTACT_IDS,
            VALID_JSON_VENDOR_CONTACT_IDS,
            Integer.parseInt(INVALID_EVENT_ID)
        );
        String expectedMessage = EventId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

}
