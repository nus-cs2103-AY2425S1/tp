package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EventTest {

    private static final String VALID_DESCRIPTION = "Team meeting";
    private static final String VALID_FROM_DATE = "2023-10-01";
    private static final String VALID_TO_DATE = "2023-10-02";
    private static final Description DESCRIPTION_OBJ = new Description(VALID_DESCRIPTION);

    @Test
    public void constructor_validStringDescriptionAndDates_success() {
        Event event = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        assertEquals("[E][ ] Team meeting (from: Oct 01 2023 to: Oct 02 2023)", event.toString());
    }

    @Test
    public void constructor_validDescriptionObject_success() {
        Event event = new Event(DESCRIPTION_OBJ.toString(), VALID_FROM_DATE, VALID_TO_DATE);
        assertEquals("[E][ ] Team meeting (from: Oct 01 2023 to: Oct 02 2023)", event.toString());
    }

    @Test
    public void constructor_withDoneStatus_success() {
        Event event = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE, true);
        assertEquals("[E][X] Team meeting (from: Oct 01 2023 to: Oct 02 2023)", event.toString());
    }

    @Test
    public void getFrom_returnsCorrectDate() {
        Event event = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        assertEquals(VALID_FROM_DATE, event.getFrom().toString());
    }

    @Test
    public void getTo_returnsCorrectDate() {
        Event event = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        assertEquals(VALID_TO_DATE, event.getTo().toString());
    }

    @Test
    public void markAsDone_marksTaskAsDone() {
        Event event = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        event.markAsDone();
        assertTrue(event.getIsDone());
        assertEquals("[E][X] Team meeting (from: Oct 01 2023 to: Oct 02 2023)", event.toString());
    }

    @Test
    public void markAsUndone_marksTaskAsUndone() {
        Event event = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE, true);
        event.markAsUndone();
        assertFalse(event.getIsDone());
        assertEquals("[E][ ] Team meeting (from: Oct 01 2023 to: Oct 02 2023)", event.toString());
    }

    @Test
    public void equals_sameEvent_returnsTrue() {
        Event event1 = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        Event event2 = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        assertTrue(event1.equals(event2));
    }

    @Test
    public void equals_nonEvent_returnsFalse() {
        Event event1 = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        Todo event2 = new Todo(VALID_DESCRIPTION);
        assertFalse(event1.equals(event2));
    }

    @Test
    public void equals_differentEvent_returnsFalse() {
        Event event1 = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        Event event2 = new Event("Workshop", "2023-10-05", "2023-10-06");
        assertFalse(event1.equals(event2));
    }

    @Test
    public void hashCode_sameEvent_returnsSameHashCode() {
        Event event1 = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        Event event2 = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        assertEquals(event1.hashCode(), event2.hashCode());
    }

    @Test
    public void hashCode_differentEvent_returnsDifferentHashCode() {
        Event event1 = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        Event event2 = new Event("Workshop", "2023-10-05", "2023-10-06");
        assertFalse(event1.hashCode() == event2.hashCode());
    }

    @Test
    public void toString_correctlyFormatsEvent() {
        Event event = new Event(VALID_DESCRIPTION, VALID_FROM_DATE, VALID_TO_DATE);
        assertEquals("[E][ ] Team meeting (from: Oct 01 2023 to: Oct 02 2023)", event.toString());
    }
}
