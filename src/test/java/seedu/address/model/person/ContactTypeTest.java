package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContactTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactType(null));
    }

    @Test
    public void constructor_invalidContactType_throwsIllegalArgumentException() {
        String invalidContactType = "";
        assertThrows(IllegalArgumentException.class, () -> new ContactType(invalidContactType));
    }

    @Test
    public void isValidContactType() {
        // null name
        assertThrows(NullPointerException.class, () -> ContactType.isValidContactType(null));

        // invalid contact type
        assertFalse(ContactType.isValidContactType("")); // empty string
        assertFalse(ContactType.isValidContactType(" ")); // spaces only
        assertFalse(ContactType.isValidContactType("intern"));
        // contains a string that is not work, school or personal

        // valid contact type
        assertTrue(ContactType.isValidContactType("work"));
        assertTrue(ContactType.isValidContactType("school"));
        assertTrue(ContactType.isValidContactType("personal"));
        assertTrue(ContactType.isValidContactType("WORK")); // capital letters

    }

    @Test
    public void equals() {
        ContactType contactType = new ContactType("work");

        // same values -> returns true
        assertTrue(contactType.equals(new ContactType("work")));

        // same object -> returns true
        assertTrue(contactType.equals(contactType));

        // null -> returns false
        assertFalse(contactType.equals(null));

        // different types -> returns false
        assertFalse(contactType.equals(5.0f));

        // different values -> returns false
        assertFalse(contactType.equals(new ContactType("school")));
    }
}

