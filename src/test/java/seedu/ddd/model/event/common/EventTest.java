package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContacts.ALICE;
import static seedu.ddd.testutil.contact.TypicalContacts.BENSON;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_2;
import static seedu.ddd.testutil.event.TypicalEvents.VALID_EVENT;
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
        assertTrue(WEDDING_A.isSameEvent(WEDDING_A));

        // different description
        Event copied = new EventBuilder(WEDDING_A)
            .withDescription(VALID_EVENT_DESCRIPTION_2)
            .build();
        assertFalse(WEDDING_A.isSameEvent(copied));

        // different clients
        copied = new EventBuilder(WEDDING_A)
            .withClients(ALICE)
            .build();
        assertFalse(WEDDING_A.isSameEvent(copied));

        // same description and clients
        copied = new EventBuilder(WEDDING_B)
            .withClients(WEDDING_A.getClients())
            .withDescription(WEDDING_A.getDescription().description)
            .build();
        assertTrue(WEDDING_A.isSameEvent(copied));

        // different vendors
        copied = new EventBuilder(WEDDING_A)
            .withVendors(new ArrayList<>())
            .build();
        assertTrue(WEDDING_A.isSameEvent(copied));
    }

    @Test
    public void equalsTest() {
        assertEquals(WEDDING_A, WEDDING_A);
        assertEquals(WEDDING_B, WEDDING_B);
        assertNotEquals(WEDDING_A, WEDDING_B);

        Event copied = new EventBuilder(WEDDING_A).build();
        assertEquals(WEDDING_A, copied);

        // different clients
        copied = new EventBuilder(WEDDING_A)
            .withClients(ALICE)
            .build();
        assertNotEquals(WEDDING_A, copied);

        // different vendors
        copied = new EventBuilder(WEDDING_A)
            .withVendors(BENSON)
            .build();
        assertNotEquals(WEDDING_A, copied);
    }

    @Test
    public void toStringTest() {
        String expectedEventString = Event.class.getCanonicalName()
                + "{name=" + VALID_EVENT.getName()
                + ", description=" + VALID_EVENT.getDescription()
                + ", date=" + VALID_EVENT.getDate()
                + ", clients=" + VALID_EVENT.getClients()
                + ", vendors=" + VALID_EVENT.getVendors()
                + ", id=" + VALID_EVENT.getEventId() + "}";
        assertEquals(expectedEventString, VALID_EVENT.toString());
    }

}
