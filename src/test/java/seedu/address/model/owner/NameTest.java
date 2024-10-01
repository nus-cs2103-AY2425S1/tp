package seedu.address.model.owner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new seedu.address.model.person.Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new seedu.address.model.person.Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> seedu.address.model.person.Name.isValidName(null));

        // invalid name
        assertFalse(seedu.address.model.person.Name.isValidName("")); // empty string
        assertFalse(seedu.address.model.person.Name.isValidName(" ")); // spaces only
        assertFalse(seedu.address.model.person.Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(seedu.address.model.person.Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(seedu.address.model.person.Name.isValidName("peter jack")); // alphabets only
        assertTrue(seedu.address.model.person.Name.isValidName("12345")); // numbers only
        assertTrue(seedu.address.model.person.Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(seedu.address.model.person.Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(seedu.address.model.person.Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        seedu.address.model.person.Name name = new seedu.address.model.person.Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new seedu.address.model.person.Name("Valid Name")));

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
