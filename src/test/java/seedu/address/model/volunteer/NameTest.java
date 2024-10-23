package seedu.address.model.volunteer;

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
        assertFalse(Name.isValidName("alice*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(Name.isValidName("alice wong")); // alphabets only
        assertTrue(Name.isValidName("12")); // numbers only
        assertTrue(Name.isValidName("alice the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Wong")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
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

    @Test
    public void isSameName() {
        // Same object -> returns true
        Name name = new Name("Bob Tan");
        assertTrue(name.isSameName(name));

        // Same name, different case -> returns true
        Name lowerCaseName = new Name("bob tan");
        Name upperCaseName = new Name("Bob Tan");
        assertTrue(lowerCaseName.isSameName(upperCaseName));

        // Same name, with leading/trailing spaces -> returns true
        Name nameWithSpaces = new Name("  Bob Tan  ");
        assertTrue(name.isSameName(nameWithSpaces));

        // Different names -> returns false
        Name differentName = new Name("Alice Tan");
        assertFalse(name.isSameName(differentName));

        // Null -> returns false
        assertFalse(name.isSameName(null));
    }

    @Test
    public void hashCode_sameName_sameHashCode() {
        Name name1 = new Name("Bob Tan");
        Name name2 = new Name("Bob Tan");

        assertTrue(name1.equals(name2));
        assertTrue(name1.hashCode() == name2.hashCode());
    }

    @Test
    public void hashCode_differentNames_differentHashCode() {
        Name name1 = new Name("Bob Tan");
        Name name2 = new Name("Alice Tan");

        assertFalse(name1.equals(name2));
        assertFalse(name1.hashCode() == name2.hashCode());
    }
}
