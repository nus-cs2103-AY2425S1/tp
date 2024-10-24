package seedu.ddd.model.contact.common;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ddd.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactId(null));
    }

    @Test
    public void constructor_invalidId_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new ContactId(invalidId));
    }

    @Test
    public void isValidId() {
        // null ID
        assertThrows(NullPointerException.class, () -> ContactId.isValidContactId(null));

        // invalid IDs
        assertFalse(ContactId.isValidContactId(-1));
        assertFalse(ContactId.isValidContactId(-10));
        assertFalse(ContactId.isValidContactId(""));
        assertFalse(ContactId.isValidContactId(" "));

        // valid IDs
        assertTrue(ContactId.isValidContactId(0));
        assertTrue(ContactId.isValidContactId(1));
        assertTrue(ContactId.isValidContactId(10));
        assertTrue(ContactId.isValidContactId(100));
        assertTrue(ContactId.isValidContactId("0"));
        assertTrue(ContactId.isValidContactId("1"));
        assertTrue(ContactId.isValidContactId("10"));
        assertTrue(ContactId.isValidContactId("100"));
    }

    @Test
    public void equals() {
        ContactId contactId = new ContactId(0);

        // same values -> returns true
        assertTrue(contactId.equals(new ContactId(0)));

        // same object -> returns true
        assertTrue(contactId.equals(contactId));

        // null -> returns false
        assertFalse(contactId.equals(null));

        // different types -> returns false
        assertFalse(contactId.equals("Other type"));

        // different values -> returns false
        assertFalse(contactId.equals(new ContactId(1)));
    }
}
