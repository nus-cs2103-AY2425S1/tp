package seedu.address.model.contactrecord;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactRecordBuilder;

public class ContactRecordListTest {
    @Test
    public void getLastContacted() {
        ContactRecordList contactRecordList = new ContactRecordList();

        // Throws IndexOutOfBoundsException when list is empty
        try {
            contactRecordList.getLastContacted();
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }

        // test 1
        ContactRecord contactRecord = new ContactRecordBuilder().build();
        contactRecordList.add(contactRecord);
        assertEquals(contactRecord, contactRecordList.getLastContacted());

        // test 2
        ContactRecord newContactRecord = new ContactRecordBuilder().build();
        contactRecordList.add(newContactRecord);
        assertEquals(newContactRecord, contactRecordList.getLastContacted());
    }

    @Test
    public void markAsContacted() {
        ContactRecordList contactRecordList = new ContactRecordList();
        ContactRecord contactRecord = new ContactRecordBuilder().withNotes(VALID_NOTES).build();
        contactRecordList.markAsContacted(VALID_NOTES);
        assertEquals(1, contactRecordList.size());
        assertEquals(contactRecord, contactRecordList.getLastContacted());
    }

    @Test
    public void markAsContacted_contactRecordProvided() {
        ContactRecordList contactRecordList = new ContactRecordList();
        ContactRecord contactRecord = new ContactRecordBuilder().build();
        contactRecordList.markAsContacted(contactRecord);
        assertEquals(1, contactRecordList.size());
        assertEquals(contactRecord, contactRecordList.getLastContacted());
    }

    @Test
    public void equals() {
        ContactRecordList contactRecordList = new ContactRecordList();
        ContactRecordList contactRecordListCopy = new ContactRecordList();
        ContactRecordList contactRecordListCopy2 = new ContactRecordList(new ContactRecord("2021-10-10", ""));
        ContactRecordList contactRecordListCopy3 = new ContactRecordList(new ContactRecord("2021-10-11", ""));

        // same object -> returns true
        assertTrue(contactRecordList.equals(contactRecordList));

        // different object, same types, same values -> returns true
        assertTrue(contactRecordList.equals(contactRecordListCopy));

        // different object, same types, different values -> returns false
        assertFalse(contactRecordListCopy2.equals(contactRecordListCopy3));

        // null -> returns false
        assertFalse(contactRecordList.equals(null));

        // different type -> returns false
        assertFalse(contactRecordList.equals(5.0f));
    }
}
