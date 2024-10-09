package seedu.address.model.contactdate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactDateBuilder;

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
        ContactDate contactDate = new ContactDateBuilder().build();
        contactDateList.add(contactDate);
        assertEquals(contactDate, contactDateList.getLastContacted());

        // test 2
        ContactDate newContactDate = new ContactDateBuilder().build();
        contactDateList.add(newContactDate);
        assertEquals(newContactDate, contactDateList.getLastContacted());
    }

    @Test
    public void markAsContacted() {
        ContactDateList contactDateList = new ContactDateList();
        ContactDate contactDate = new ContactDateBuilder().withNotes(VALID_NOTES).build();
        contactDateList.markAsContacted(VALID_NOTES);
        assertEquals(1, contactDateList.size());
        assertEquals(contactDate, contactDateList.getLastContacted());
    }

    @Test
    public void markAsContacted_contactDateProvided() {
        ContactDateList contactDateList = new ContactDateList();
        ContactDate contactDate = new ContactDateBuilder().build();
        contactDateList.markAsContacted(contactDate);
        assertEquals(1, contactDateList.size());
        assertEquals(contactDate, contactDateList.getLastContacted());
    }

    @Test
    public void equals() {
        ContactDateList contactDateList = new ContactDateList();
        ContactDateList contactDateListCopy = new ContactDateList();
        ContactDateList contactDateListCopy2 = new ContactDateList(new ContactDate("2021-10-10", ""));
        ContactDateList contactDateListCopy3 = new ContactDateList(new ContactDate("2021-10-11", ""));

        // same object -> returns true
        assertTrue(contactDateList.equals(contactDateList));

        // different object, same types, same values -> returns true
        assertTrue(contactDateList.equals(contactDateListCopy));

        // different object, same types, different values -> returns false
        assertFalse(contactDateListCopy2.equals(contactDateListCopy3));

        // null -> returns false
        assertFalse(contactDateList.equals(null));

        // different type -> returns false
        assertFalse(contactDateList.equals(5.0f));
    }
}
