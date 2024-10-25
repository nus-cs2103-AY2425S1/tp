package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.contact.TypicalContactFields.*;
import static seedu.ddd.testutil.event.TypicalEventFields.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;
import seedu.ddd.testutil.event.EventBuilder;


public class EventTest {
    public static final EventId DUMMY_EVENTID = new EventId(0);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null, null, null, DUMMY_EVENTID));
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

    // @Test
    // public void equalsTest() {
    //     Description dummyDescription = new Description("Something not important.");

    //     ArrayList<Client> clientList1 = new ArrayList<>();
    //     ArrayList<Vendor> vendorList1 = new ArrayList<>();
    //     ArrayList<Client> clientList2 = new ArrayList<>();
    //     ArrayList<Vendor> vendorList2 = new ArrayList<>();

    //     clientList1.add(ALICE);
    //     clientList1.add(CARL);
    //     clientList2.add(CARL);
    //     clientList2.add(ALICE);
    //     assertNotEquals(clientList1, clientList2);

    //     vendorList1.add(BENSON);
    //     vendorList1.add(DANIEL);
    //     vendorList2.add(DANIEL);
    //     vendorList2.add(BENSON);
    //     assertNotEquals(vendorList1, vendorList2);

    //     Event event1 = new Event(clientList1, vendorList1, dummyDescription, DUMMY_EVENTID);
    //     Event event2 = new Event(clientList2, vendorList2, dummyDescription, DUMMY_EVENTID);

    //     assertEquals(event1, event1);
    //     assertEquals(event1, event2);
    //     assertNotEquals(event1, null);
    // }
}
