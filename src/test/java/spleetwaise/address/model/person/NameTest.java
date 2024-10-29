package spleetwaise.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import spleetwaise.address.testutil.Assert;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("X Æ A-12")); // contains unsupported ligature
        assertFalse(Name.isValidName("刘关张")); // contains chinese characters
        assertFalse(Name.isValidName("عبد العزيز")); // contains arabic characters

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names

        // new valid test cases based on the updated regex to allow more legal names
        assertTrue(Name.isValidName("O'Connor")); // name with apostrophe
        assertTrue(Name.isValidName("Jean-Luc")); // name with hyphen
        assertTrue(Name.isValidName("J.P. Morgan")); // name with periods
        assertTrue(Name.isValidName("John s/o Tan")); // name with slash
        assertTrue(Name.isValidName("Anne-Marie & Sons")); // name with ampersand and hyphen
        assertTrue(Name.isValidName("John \"Johnny\" Doe")); // name with quotes
        assertTrue(Name.isValidName("Richard (Rick) Roe")); // name with parentheses
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
