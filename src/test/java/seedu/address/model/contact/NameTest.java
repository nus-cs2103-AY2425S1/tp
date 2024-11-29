package seedu.address.model.contact;

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
        // EP: null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        // EP: empty string
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName("    ")); // spaces only

        // EP: non-alphabets characters only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("1234")); // number characters

        // EP: alphabets and disallowed non-alphabets characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("timmy 12345"));
        assertFalse(Name.isValidName("Liu Huan, Gladys")); // comma present

        // EP: incorrect use of allowed special characters
        assertFalse(Name.isValidName("Ravin d/o Sandra @ Joseph")); // more than 1 special characters
        assertFalse(Name.isValidName("d/o Sandra")); // missing 1st part
        assertFalse(Name.isValidName("Ravin s/o")); // missing 2nd part
        assertFalse(Name.isValidName("Ravins/oJane")); // lack of white space around s/o
        assertFalse(Name.isValidName("(Chen Zi Hao)")); // bracket portion only
        assertFalse(Name.isValidName("Timothy Tan (Chen Zi Hao")); // missing close bracket
        assertFalse(Name.isValidName("(Chen Zi Hao) Timmy")); // brackets should not be at the start
        assertFalse(Name.isValidName("Dang(Chen Zi Hao) Timmy")); // brackets should not be at the middle

        // valid name
        // EP: use of alphabets only
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("peTer Jack")); // capital letters are okay
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters

        // EP: use of special characters allowed
        assertTrue(Name.isValidName("Ravi d/O Sandra"));
        assertTrue(Name.isValidName("Ravi s/o Sandra"));
        assertTrue(Name.isValidName("Lynette Cheryl Luth @ Ong Jia Yi"));
        assertTrue(Name.isValidName("Lynette Cheryl Luth @Ong Jia Yi")); // spaces don't matter for @
        assertTrue(Name.isValidName("Dave Tan Tze How (Chen Zi Hao)")); // brackets are allowed
        assertTrue(Name.isValidName("Dave Tan Tze How(Chen Zi Hao)")); // whitespace not required for ()
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

        // null -> returns false
        assertFalse(name.equalsIgnoreCase(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }

    @Test
    public void isHashCode() {
        Name aaron = new Name("aaron");

        assertTrue(aaron.hashCode() > 0);
    }
}
