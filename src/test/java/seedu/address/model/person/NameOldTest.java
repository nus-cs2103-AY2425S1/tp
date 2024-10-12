package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameOldTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void validate() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.validate(null));

        // invalid name
        assertFalse(Name.validate("")); // empty string
        assertFalse(Name.validate(" ")); // spaces only
        assertFalse(Name.validate("^")); // only non-alphanumeric characters
        assertFalse(Name.validate("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.validate("peter jack")); // alphabets only
        assertTrue(Name.validate("12345")); // numbers only
        assertTrue(Name.validate("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.validate("Capital Tan")); // with capital letters
        assertTrue(Name.validate("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
