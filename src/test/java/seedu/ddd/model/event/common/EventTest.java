package seedu.ddd.model.event.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalContacts.ALICE;
import static seedu.ddd.testutil.TypicalContacts.BENSON;
import static seedu.ddd.testutil.TypicalContacts.CARL;
import static seedu.ddd.testutil.TypicalContacts.DANIEL;
import static seedu.ddd.testutil.TypicalContacts.ELLE;
import static seedu.ddd.testutil.TypicalContacts.GEORGE;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;


public class EventTest {
    public static final EventId DUMMY_EVENTID = new EventId(0);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Event(null, null, null, DUMMY_EVENTID));
    }

    @Test
    public void isSameEventTest() {
        ArrayList<Client> clientList1 = new ArrayList<>();
        clientList1.add(ALICE);
        clientList1.add(CARL);

        ArrayList<Client> clientList2 = new ArrayList<>();
        clientList2.add(ALICE);
        clientList2.add(CARL);
        clientList2.add(ELLE);

        ArrayList<Vendor> vendorList1 = new ArrayList<>();
        vendorList1.add(BENSON);
        vendorList1.add(DANIEL);

        ArrayList<Vendor> vendorList2 = new ArrayList<>();
        vendorList2.add(BENSON);
        vendorList2.add(DANIEL);
        vendorList2.add(GEORGE);

        final Description description1 = new Description("Some description.");
        final Description description2 = new Description("Another description");

        Event sampleEvent = new Event(clientList1, vendorList1, description1, DUMMY_EVENTID);
        Event sameClientDifferentVendorSameDescription = new Event(clientList1, vendorList2,
                description1, DUMMY_EVENTID);
        Event sameClientSameVendorDifferentDescription = new Event(clientList1, vendorList1,
                description2, DUMMY_EVENTID);
        Event differentClientSameVendorSameDescription = new Event(clientList2, vendorList1,
                description1, DUMMY_EVENTID);

        assertTrue(sampleEvent.isSameEvent(sameClientDifferentVendorSameDescription));
        assertFalse(sampleEvent.isSameEvent(sameClientSameVendorDifferentDescription));
        assertFalse(sampleEvent.isSameEvent(differentClientSameVendorSameDescription));
    }

    @Test
    public void equalsTest() {
        Description dummyDescription = new Description("Something not important.");

        ArrayList<Client> clientList1 = new ArrayList<>();
        ArrayList<Vendor> vendorList1 = new ArrayList<>();
        ArrayList<Client> clientList2 = new ArrayList<>();
        ArrayList<Vendor> vendorList2 = new ArrayList<>();

        clientList1.add(ALICE);
        clientList1.add(CARL);
        clientList2.add(CARL);
        clientList2.add(ALICE);
        assertNotEquals(clientList1, clientList2);

        vendorList1.add(BENSON);
        vendorList1.add(DANIEL);
        vendorList2.add(DANIEL);
        vendorList2.add(BENSON);
        assertNotEquals(vendorList1, vendorList2);

        Event event1 = new Event(clientList1, vendorList1, dummyDescription, DUMMY_EVENTID);
        Event event2 = new Event(clientList2, vendorList2, dummyDescription, DUMMY_EVENTID);

        assertEquals(event1, event1);
        assertEquals(event1, event2);
        assertNotEquals(event1, null);
    }
}
