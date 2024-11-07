package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertDoesNotThrow;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.EVENT_A;
import static seedu.address.testutil.TypicalEvents.EVENT_F;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;

public class JsonAdaptedEventTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_LOCATION = "h@ll";
    private static final String INVALID_DATE = "2020-13-32";
    private static final String INVALID_TIME = "25:00";
    private static final String INVALID_DESCRIPTION = "!";

    private static final String VALID_NAME = EVENT_A.getName().toString();
    private static final String VALID_LOCATION = EVENT_A.getLocation().toString();
    private static final String VALID_DATE = EVENT_A.getDate().toParsableString();
    private static final String VALID_START_TIME = EVENT_A.getStartTime().toString();
    private static final String VALID_END_TIME = EVENT_A.getEndTime().toString();
    private static final String VALID_DESCRIPTION = EVENT_A.getDescription().toString();
    private static final List<String> VALID_VOLUNTEERS = EVENT_A.getVolunteers();


    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(EVENT_A);
        assertEquals(EVENT_A, event.toModelType());
    }

    @Test
    public void toModelType_nullDescription_returnsEvent() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME,
                VALID_LOCATION, null, VALID_VOLUNTEERS);
        assertDoesNotThrow(event::toModelType);
        try {
            Event eventModel = event.toModelType();
            assertEquals(EVENT_F, eventModel);
        } catch (IllegalValueException e) {
            throw new AssertionError("This should not happen", e);
        }
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(INVALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_LOCATION,
                        VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = EventName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(null, VALID_DATE, VALID_START_TIME, VALID_END_TIME,
                VALID_LOCATION, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, EventName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, INVALID_DATE, VALID_START_TIME, VALID_END_TIME,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, null, VALID_START_TIME, VALID_END_TIME,
                VALID_LOCATION, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, VALID_DATE, INVALID_TIME, VALID_END_TIME,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_DATE, null, VALID_END_TIME,
                VALID_LOCATION, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, VALID_DATE, VALID_START_TIME, INVALID_TIME,
                        VALID_LOCATION, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_DATE, VALID_START_TIME, null,
                VALID_LOCATION, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME,
                        INVALID_LOCATION, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME,
                null, VALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedEvent event =
                new JsonAdaptedEvent(VALID_NAME, VALID_DATE, VALID_START_TIME, VALID_END_TIME,
                        VALID_LOCATION, INVALID_DESCRIPTION, VALID_VOLUNTEERS);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    /*
    Note that we do not test for null volunteers as if volunteers is null, it will be replaced with an empty list.

    Also, we do not test for invalid volunteers as the checks are handled in JsonSerializableAddressBook::toModelType,
    where AddressBook::validateAllVolunteers will be called to handle the validation.
     */


}

