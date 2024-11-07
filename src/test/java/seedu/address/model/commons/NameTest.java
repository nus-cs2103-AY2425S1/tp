package seedu.address.model.commons;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

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
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("John s/o jane")); // with s/o
        assertTrue(Name.isValidName("John S/o jane")); // with uppercase S
        assertTrue(Name.isValidName("John s/O jane")); // with uppercase O
        assertTrue(Name.isValidName("John S/O jane")); // with uppercase S and O
        assertTrue(Name.isValidName("John s/o   jane")); // with spaces after s/o
        assertFalse(Name.isValidName("John s/o ")); // with nothing behind s/o
        assertFalse(Name.isValidName("John s/o   ")); // with nothing behind s/o
        assertFalse(Name.isValidName("Johns/o ")); // grouped s/o
        assertFalse(Name.isValidName("John s/ojane ")); // grouped s/o
        assertFalse(Name.isValidName("s/o john")); // with nothing in front of s/o
        assertFalse(Name.isValidName("John s/o jane d/o john")); // more than one s/o

        // repeated for d/o
        assertTrue(Name.isValidName("John d/o jane")); // with d/o
        assertTrue(Name.isValidName("John d/o jane")); // with uppercase D
        assertTrue(Name.isValidName("John d/o jane")); // with uppercase O
        assertTrue(Name.isValidName("John d/o jane")); // with uppercase D and O
        assertTrue(Name.isValidName("John d/o  jane")); // with spaces after d/o
        assertFalse(Name.isValidName("John d/o")); // with nothing behind d/o
        assertFalse(Name.isValidName("John d/o  ")); // with nothing behind d/o
        assertFalse(Name.isValidName("Johnd/o")); // grouped d/o
        assertFalse(Name.isValidName("John d/ojane ")); // grouped d/o
        assertFalse(Name.isValidName("d/o john")); // with nothing in front of d/o
        assertFalse(Name.isValidName("John d/o jane s/o john")); // more than one d/o
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // different casing -> returns true
        assertTrue(name.equals(new Name("vAliD NAMe")));

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
