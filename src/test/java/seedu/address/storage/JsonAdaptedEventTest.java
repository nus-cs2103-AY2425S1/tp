package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.MEETING;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;

public class JsonAdaptedEventTest {
    private static final String INVALID_EVENT_NAME = "+meeting";
    private static final String INVALID_EVENT_START_DATE = "2024-10-01";
    private static final String INVALID_EVENT_END_DATE = "2023-10-01";
    private static final String INVALID_EVENT_DATE_FORMAT = "20231001";

    private static final String VALID_EVENT_NAME = MEETING.getEventName().toString();
    private static final String VALID_EVENT_DESCRIPTION = MEETING.getEventDescription().toString();
    private static final String VALID_EVENT_START_DATE = MEETING.getEventStartDate().toString();
    private static final String VALID_EVENT_END_DATE = MEETING.getEventEndDate().toString();
    private static final int VALID_EVENT_ID = MEETING.getEventId();

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(MEETING);
        assertEquals(MEETING, event.toModelType());
    }

    @Test
    public void toModelType_invalidEventName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(INVALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, VALID_EVENT_START_DATE,
                        VALID_EVENT_END_DATE, VALID_EVENT_ID);
        String expectedMessage = EventName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(null, VALID_EVENT_DESCRIPTION, VALID_EVENT_START_DATE,
                VALID_EVENT_END_DATE, VALID_EVENT_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventDate_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, INVALID_EVENT_DATE_FORMAT,
                        VALID_EVENT_END_DATE, VALID_EVENT_ID);
        String expectedMessage = EventDuration.MESSAGE_CONSTRAINTS_DATE_STRING;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventDuration_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, INVALID_EVENT_START_DATE,
                        INVALID_EVENT_END_DATE, VALID_EVENT_ID);
        String expectedMessage = EventDuration.MESSAGE_CONSTRAINTS_DATE_ORDER;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventDuration_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_EVENT_NAME, VALID_EVENT_DESCRIPTION, null, null, VALID_EVENT_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventDuration.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_NAME, null, VALID_EVENT_START_DATE,
                VALID_EVENT_END_DATE, VALID_EVENT_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "EventDescription");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
