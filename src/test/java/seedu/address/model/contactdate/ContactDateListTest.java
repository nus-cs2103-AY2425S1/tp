package seedu.address.model.contactdate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ContactDateListTest {
    @Test
    public void getLastContacted() {
        ContactDateList contactDateList = new ContactDateList();

        // Throws IndexOutOfBoundsException when list is empty
        try {
            contactDateList.getLastContacted();
        } catch (IndexOutOfBoundsException e) {
            assertTrue(true);
        }

        // test 1
        ContactDate contactDate = new ContactDate("2021-10-10");
        contactDateList.add(contactDate);
        assertEquals(contactDate, contactDateList.getLastContacted());

        // test 2
        ContactDate newContactDate = new ContactDate("2021-10-11");
        contactDateList.add(newContactDate);
        assertEquals(newContactDate, contactDateList.getLastContacted());
    }

    @Test
    public void markAsContacted() {
        ContactDateList contactDateList = new ContactDateList();
        ContactDate contactDate = ContactDate.getCurrentDate();
        contactDateList.markAsContacted();
        assertEquals(1, contactDateList.size());
        assertEquals(contactDate, contactDateList.getLastContacted());
    }

    @Test
    public void markAsContacted_contactDateProvided() {
        ContactDateList contactDateList = new ContactDateList();
        ContactDate contactDate = new ContactDate("2024-10-01");
        contactDateList.markAsContacted(contactDate);
        assertEquals(1, contactDateList.size());
        assertEquals(contactDate, contactDateList.getLastContacted());
    }

    @Test
    public void equals() {
        ContactDateList contactDateList = new ContactDateList();
        ContactDateList contactDateListCopy = new ContactDateList();

        // same object -> returns true
        assertTrue(contactDateList.equals(contactDateList));

        // different object, same values -> returns false
        assertFalse(contactDateList.equals(contactDateListCopy));

        // null -> returns false
        assertFalse(contactDateList.equals(null));

        // different type -> returns false
        assertFalse(contactDateList.equals(5.0f));
    }
}
