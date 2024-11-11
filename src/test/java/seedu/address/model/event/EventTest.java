package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_2;
import static seedu.address.testutil.TypicalEvents.MEETING;
import static seedu.address.testutil.TypicalEvents.WORKSHOP;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

public class EventTest {
    @Test
    public void constructor_validInputs_success() {
        Event event = new EventBuilder().build();
        assertEquals(EventBuilder.DEFAULT_EVENT_NAME, event.getEventName().toString());
        assertEquals(EventBuilder.DEFAULT_EVENT_DESCRIPTION, event.getEventDescription().toString());
        assertEquals(EventBuilder.DEFAULT_EVENT_START_DATE, event.getEventStartDate().toString());
        assertEquals(EventBuilder.DEFAULT_EVENT_END_DATE, event.getEventEndDate().toString());
    }

    @Test
    public void isSameEvent() {
        // same object -> returns true
        assertTrue(MEETING.isSameEvent(MEETING));

        // null -> returns false
        assertFalse(MEETING.isSameEvent(null));

        // same name, all other attributes different -> returns true
        Event editedMeeting = new EventBuilder(MEETING).withEventDescription(VALID_EVENT_DESCRIPTION_WORKSHOP)
                .withEventDuration(VALID_EVENT_FROM_DATE_1, VALID_EVENT_TO_DATE_1).build();
        assertTrue(MEETING.isSameEvent(editedMeeting));

        // different name, all other attributes same -> returns false
        editedMeeting = new EventBuilder(MEETING).withEventName(VALID_EVENT_NAME_WORKSHOP).build();
        assertFalse(MEETING.isSameEvent(editedMeeting));

        // name differs in case, all other attributes same -> returns true
        Event editedWorkshop = new EventBuilder(WORKSHOP)
                .withEventName(VALID_EVENT_NAME_WORKSHOP.toLowerCase())
                .build();
        assertTrue(WORKSHOP.isSameEvent(editedWorkshop));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_EVENT_NAME_WORKSHOP + " ";
        editedWorkshop = new EventBuilder(WORKSHOP).withEventName(nameWithTrailingSpaces).build();
        assertFalse(WORKSHOP.isSameEvent(editedWorkshop));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Event meetingCopy = new EventBuilder(MEETING).build();
        assertTrue(MEETING.equals(meetingCopy));

        // same object -> returns true
        assertTrue(MEETING.equals(MEETING));

        // null -> returns false
        assertFalse(MEETING.equals(null));

        // different type -> returns false
        assertFalse(MEETING.equals(5));

        // different event -> returns false
        assertFalse(MEETING.equals(WORKSHOP));

        // different name -> returns false
        Event editedMeeting = new EventBuilder(MEETING).withEventName(VALID_EVENT_NAME_WORKSHOP).build();
        assertFalse(MEETING.equals(editedMeeting));

        // different description -> returns false
        editedMeeting = new EventBuilder(MEETING).withEventDescription(VALID_EVENT_DESCRIPTION_WORKSHOP).build();
        assertFalse(MEETING.equals(editedMeeting));

        // different start date -> returns false
        editedMeeting = new EventBuilder(MEETING)
                .withEventDuration(VALID_EVENT_FROM_DATE_2, VALID_EVENT_TO_DATE_1)
                .build();
        assertFalse(MEETING.equals(editedMeeting));

        // different end date -> returns false
        editedMeeting = new EventBuilder(MEETING)
                .withEventDuration(VALID_EVENT_FROM_DATE_1, VALID_EVENT_TO_DATE_2)
                .build();
        assertFalse(MEETING.equals(editedMeeting));
    }

    @Test
    public void toStringMethod() {
        String expected = Event.class.getCanonicalName() + "{eventName=" + MEETING.getEventName()
                + ", eventDescription=" + MEETING.getEventDescription() + ", eventDuration=from: "
                + MEETING.getEventStartDate() + "; to: " + MEETING.getEventEndDate() + "}";
        assertEquals(expected, MEETING.toString());
    }
}
