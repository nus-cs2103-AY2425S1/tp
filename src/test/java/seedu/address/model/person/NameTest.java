package seedu.address.model.person;

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
        assertFalse(Name.isValidName("")); // EP: empty string
        assertFalse(Name.isValidName(" ")); // EP: space only
        assertFalse(Name.isValidName(" a")); // EP: space as first character
        assertFalse(Name.isValidName("1")); // EP: number
        assertFalse(Name.isValidName("test123")); // Number with alphabets

        // valid name
        assertTrue(Name.isValidName("^")); // EP: only special characters
        assertTrue(Name.isValidName("peter")); // EP: alphabets only
        assertTrue(Name.isValidName("peter jack")); // EP: alphabets with space
        assertTrue(Name.isValidName("peter s/o john")); // contains special and alphabetical characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson What Ray Jr")); // long names
    }

    @Test
    public void hasConsecutiveWhitespaces() {
        Name whitespaceName = new Name("test  test");
        Name nonWhitespaceName = new Name("test test");

        // does not have consecutive whitespaces
        assertFalse(nonWhitespaceName.hasConsecutiveWhitespaces()); // EP: no consecutive whitespaces
        // has consecutive whitespaces
        assertTrue(whitespaceName.hasConsecutiveWhitespaces()); // EP: consecutive whitespaces
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
