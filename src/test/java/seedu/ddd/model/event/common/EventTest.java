package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_2;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_A;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_B;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.ddd.testutil.event.EventBuilder;


public class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Event(null, null, null, DEFAULT_EVENT_ID));
    }

    @Test
    public void isSameEventTest() {
        Event event = new EventBuilder().build();

        // Different description
        Event testEvent = new EventBuilder(event).withDescription(VALID_EVENT_DESCRIPTION_2).build();
        assertFalse(event.isSameEvent(testEvent));

        // Different clients
        // testEvent = new EventBuilder(event).withDescription().build();
        assertFalse(event.isSameEvent(testEvent));

        // Different vendors
        testEvent = new EventBuilder(event).withVendors(new ArrayList<>()).build();
        assertTrue(event.isSameEvent(testEvent));
    }

    @Test
    public void equalsTest() {
        assertEquals(WEDDING_A, WEDDING_A);
        assertEquals(WEDDING_B, WEDDING_B);
        assertNotEquals(WEDDING_A, WEDDING_B);
    }
}
