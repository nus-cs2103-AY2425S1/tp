package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameOldTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NameOld(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new NameOld(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> NameOld.isValidName(null));

        // invalid name
        assertFalse(NameOld.isValidName("")); // empty string
        assertFalse(NameOld.isValidName(" ")); // spaces only
        assertFalse(NameOld.isValidName("^")); // only non-alphanumeric characters
        assertFalse(NameOld.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(NameOld.isValidName("peter jack")); // alphabets only
        assertTrue(NameOld.isValidName("12345")); // numbers only
        assertTrue(NameOld.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(NameOld.isValidName("Capital Tan")); // with capital letters
        assertTrue(NameOld.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        NameOld name = new NameOld("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new NameOld("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new NameOld("Other Valid Name")));
    }
}
