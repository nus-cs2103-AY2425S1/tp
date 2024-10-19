package seedu.ddd.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.ddd.testutil.Assert.assertThrows;
import static seedu.ddd.testutil.TypicalContacts.ALICE;
import static seedu.ddd.testutil.TypicalContacts.BENSON;
import static seedu.ddd.testutil.TypicalContacts.CARL;
import static seedu.ddd.testutil.TypicalContacts.DANIEL;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.vendor.Vendor;

public class EventTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        ArrayList<Client> clients = new ArrayList<>();
        ArrayList<Vendor> vendors = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> new Event(null, vendors));
        assertThrows(NullPointerException.class, () -> new Event(clients, vendors));
    }

    @Test
    public void equalsTest() {
        String dummyDescription = "";

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

        Event event1 = new Event(clientList1, vendorList1);
        Event event2 = new Event(clientList2, vendorList2);

        assertEquals(event1, event2);
    }
}