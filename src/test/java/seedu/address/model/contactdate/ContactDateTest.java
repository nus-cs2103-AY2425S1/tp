package seedu.address.model.contactdate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NOTES;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContactDateBuilder;


public class ContactDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactDate(null, VALID_NOTES));
    }

    @Test
    public void constructor_invalidContactDate_throwsIllegalArgumentException() {
        String invalidContactDate = "";
        assertThrows(IllegalArgumentException.class, () -> new ContactDate(invalidContactDate, VALID_NOTES));
    }

    @Test
    public void isValidContactDate() {
        // null tag contact date
        assertThrows(NullPointerException.class, () -> ContactDate.isValidContactDate(null));

        // invalid contact date
        assertFalse(ContactDate.isValidContactDate("2020-13-01"));
        assertFalse(ContactDate.isValidContactDate("2020-01-32"));
        assertFalse(ContactDate.isValidContactDate(""));

        // valid contact date
        assertTrue(ContactDate.isValidContactDate("2020-01-01"));
    }

    @Test
    public void createCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        ContactDate contactDate = ContactDate.createCurrentDate(VALID_NOTES);
        assertEquals(contactDate.value, currentDate);
        assertEquals(contactDate.getNotes(), VALID_NOTES);
    }

    @Test
    public void equals() {
        ContactDate contactDate = new ContactDateBuilder().build();

        // same values -> returns true
        ContactDate contactDateCopy = new ContactDateBuilder().build();
        assertTrue(contactDate.equals(contactDateCopy));

        // same object -> returns true
        assertTrue(contactDate.equals(contactDate));

        // null -> returns false
        assertFalse(contactDate.equals(null));

        // different type -> returns false
        assertFalse(contactDate.equals(5.0f));

        // different contact date -> returns false
        ContactDate differentContactDate = new ContactDateBuilder().withDate("2020-01-02").build();
        assertFalse(contactDate.equals(differentContactDate));

        // different notes -> returns false
        ContactDate differentNotes = new ContactDateBuilder().withNotes("Different notes").build();
        assertFalse(contactDate.equals(differentNotes));
    }

}
