package hallpointer.address.model.member;

import static hallpointer.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertFalse(Name.isValidName("3ater")); // must start with a letter
        assertFalse(Name.isValidName("/Dodo")); // also must start with a letter

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets and spaces only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters ok
        assertTrue(Name.isValidName("Capital Tan")); // capital letters also ok
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(Name.isValidName("Tag S/o Keith")); // slashes are allowed
        assertTrue(Name.isValidName("dog s/0 CAT")); // all in combination
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same up to case -> returns true
        assertTrue(name.equals(new Name("valid name")));
        assertTrue(name.equals(new Name("VALID NAME")));
        assertTrue(name.equals(new Name("valid nAMe")));

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
