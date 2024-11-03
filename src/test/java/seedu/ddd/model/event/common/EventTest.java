package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContacts.ALICE;
import static seedu.ddd.testutil.contact.TypicalContacts.BENSON;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_VENDOR;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DESCRIPTION;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_NAME;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_VENDOR_LIST;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_2;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_NAME_1;
import static seedu.ddd.testutil.event.TypicalEvents.VALID_EVENT;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_A;
import static seedu.ddd.testutil.event.TypicalEvents.WEDDING_B;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.exceptions.ContactNotFoundException;
import seedu.ddd.testutil.event.EventBuilder;


public class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new Event(null, null, null, DEFAULT_EVENT_ID));
    }

    @Test
    public void constructor_emptyClients_throwsAssertionError() {
        assertThrows(AssertionError.class, () ->
                new Event(
                    DEFAULT_EVENT_NAME,
                    DEFAULT_EVENT_DESCRIPTION,
                    DEFAULT_EVENT_DATE,
                    new ArrayList<>(),
                    DEFAULT_EVENT_VENDOR_LIST,
                    DEFAULT_EVENT_ID
                ));
    }

    @Test
    public void removeContact_missingContact_throwsContactNotFoundException() {
        // WEDDING_A does not contain VALID_CLIENT and does not contain VALID_VENDOR
        Event event = new EventBuilder(WEDDING_A).build();
        assert !event.getContacts().contains(VALID_CLIENT);
        assert !event.getContacts().contains(VALID_VENDOR);

        assertThrows(ContactNotFoundException.class, () -> event.removeContact(VALID_CLIENT));
        assertThrows(ContactNotFoundException.class, () -> event.removeContact(VALID_VENDOR));
    }

    @Test
    public void isSameEvent() {
        assertTrue(WEDDING_A.isSameEvent(WEDDING_A));

        // different name
        Event copied = new EventBuilder(WEDDING_A)
                .withName(VALID_EVENT_NAME_1)
                .build();
        assertFalse(WEDDING_A.isSameEvent(copied));

        // different description
        copied = new EventBuilder(WEDDING_A)
            .withDescription(VALID_EVENT_DESCRIPTION_2)
            .build();
        assertTrue(WEDDING_A.isSameEvent(copied));

        // different clients
        copied = new EventBuilder(WEDDING_A)
            .withClients(ALICE)
            .build();
        assertTrue(WEDDING_A.isSameEvent(copied));

        // same name
        copied = new EventBuilder(WEDDING_B)
            .withName(WEDDING_A.getName().fullName)
            .build();
        assertTrue(WEDDING_A.isSameEvent(copied));

        // different vendors
        copied = new EventBuilder(WEDDING_A)
            .withVendors(new ArrayList<>())
            .build();
        assertTrue(WEDDING_A.isSameEvent(copied));
    }

    @Test
    public void equals() {
        assertEquals(WEDDING_A, WEDDING_A);
        assertEquals(WEDDING_B, WEDDING_B);
        assertNotEquals(WEDDING_A, WEDDING_B);

        // different type
        assertNotEquals(WEDDING_A, 1);

        Event copied = new EventBuilder(WEDDING_A).build();
        assertEquals(WEDDING_A, copied);

        // different name
        copied = new EventBuilder(WEDDING_A)
                .withName(VALID_CLIENT_NAME)
                .build();
        assertNotEquals(WEDDING_A, copied);

        // different clients
        copied = new EventBuilder(WEDDING_A)
                .withClients(ALICE)
                .build();
        assertEquals(WEDDING_A, copied);

        // different vendors
        copied = new EventBuilder(WEDDING_A)
                .withVendors(BENSON)
                .build();
        assertEquals(WEDDING_A, copied);

        // different description
        copied = new EventBuilder(WEDDING_A)
                .withDescription(WEDDING_B.getDescription().description)
                .build();
        assertEquals(WEDDING_A, copied);
    }


    @Test
    public void toStringMethod() {
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
