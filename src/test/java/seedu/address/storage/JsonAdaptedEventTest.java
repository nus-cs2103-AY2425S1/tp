package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalEvents.TECH_CONFERENCE;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Event;

public class JsonAdaptedEventTest {
    private static final String INVALID_NAME = " ";

    // Using valid details from a typical event for testing
    private static final String VALID_NAME = TECH_CONFERENCE.getName();
    private static final List<JsonAdaptedPerson> VALID_ATTENDEES = TECH_CONFERENCE.getAttendees().stream()
            .map(JsonAdaptedPerson::new)
            .toList();
    private static final List<JsonAdaptedPerson> VALID_VENDORS = TECH_CONFERENCE.getVendors().stream()
            .map(JsonAdaptedPerson::new)
            .toList();
    private static final List<JsonAdaptedPerson> VALID_SPONSORS = TECH_CONFERENCE.getSponsors().stream()
            .map(JsonAdaptedPerson::new)
            .toList();
    private static final List<JsonAdaptedPerson> VALID_VOLUNTEERS = TECH_CONFERENCE.getVolunteers().stream()
            .map(JsonAdaptedPerson::new)
            .toList();

    @Test
    public void toModelType_validEventDetails_returnsEvent() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(TECH_CONFERENCE);
        assertEquals(TECH_CONFERENCE, event.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(null, VALID_ATTENDEES,
                                                    VALID_VENDORS, VALID_SPONSORS, VALID_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedEvent event = new JsonAdaptedEvent(null, VALID_ATTENDEES,
                                                    VALID_VENDORS, VALID_SPONSORS, VALID_VOLUNTEERS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Name");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullAttendees_returnsEventWithoutAttendees() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, null,
                                                        VALID_VENDORS, VALID_SPONSORS, VALID_VOLUNTEERS);
        Event expectedEvent = new Event(VALID_NAME, Set.of(),
                TECH_CONFERENCE.getVendors(), TECH_CONFERENCE.getSponsors(), TECH_CONFERENCE.getVolunteers());
        assertEquals(expectedEvent, event.toModelType());
    }

    @Test
    public void toModelType_nullVendors_returnsEventWithoutVendors() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_ATTENDEES,
                                                null, VALID_SPONSORS, VALID_VOLUNTEERS);
        Event expectedEvent = new Event(VALID_NAME, TECH_CONFERENCE.getAttendees(),
                Set.of(), TECH_CONFERENCE.getSponsors(), TECH_CONFERENCE.getVolunteers());
        assertEquals(expectedEvent, event.toModelType());
    }

    @Test
    public void toModelType_nullSponsors_returnsEventWithoutSponsors() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_ATTENDEES,
                                                        VALID_VENDORS, null, VALID_VOLUNTEERS);
        Event expectedEvent = new Event(VALID_NAME, TECH_CONFERENCE.getAttendees(),
                TECH_CONFERENCE.getVendors(), Set.of(), TECH_CONFERENCE.getVolunteers());
        assertEquals(expectedEvent, event.toModelType());
    }

    @Test
    public void toModelType_nullVolunteers_returnsEventWithoutVolunteers() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_NAME, VALID_ATTENDEES,
                                                        VALID_VENDORS, VALID_SPONSORS, null);
        Event expectedEvent = new Event(VALID_NAME, TECH_CONFERENCE.getAttendees(),
                TECH_CONFERENCE.getVendors(), TECH_CONFERENCE.getSponsors(), Set.of());
        assertEquals(expectedEvent, event.toModelType());
    }
}
