package seedu.address.model.contactdate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class ContactDateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContactDate(null));
    }

    @Test
    public void constructor_invalidContactDate_throwsIllegalArgumentException() {
        String invalidContactDate = "";
        assertThrows(IllegalArgumentException.class, () -> new ContactDate(invalidContactDate));
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

}
