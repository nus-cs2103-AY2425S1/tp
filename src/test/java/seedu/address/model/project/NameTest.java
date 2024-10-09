package seedu.address.model.project;

import seedu.address.model.project.Name;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new seedu.address.model.project.Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new seedu.address.model.project.Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> seedu.address.model.project.Name.isValidName(null));

        // invalid name
        assertFalse(seedu.address.model.project.Name.isValidName("")); // empty string
        assertFalse(seedu.address.model.project.Name.isValidName(" ")); // spaces only
        assertFalse(seedu.address.model.project.Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(seedu.address.model.project.Name.isValidName("project*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(seedu.address.model.project.Name.isValidName("project alpha")); // alphabets only
        assertTrue(seedu.address.model.project.Name.isValidName("12345")); // numbers only
        assertTrue(seedu.address.model.project.Name.isValidName("project alpha 2")); // alphanumeric characters
        assertTrue(seedu.address.model.project.Name.isValidName("Capital Project A")); // with capital letters
        assertTrue(seedu.address.model.project.Name.isValidName("Capital Project A Version 5 Revision 2")); // long names
    }

    @Test
    public void equals() {
        seedu.address.model.project.Name name = new seedu.address.model.project.Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new seedu.address.model.project.Name("Valid Name")));

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
