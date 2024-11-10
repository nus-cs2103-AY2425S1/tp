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
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("timmy 12345")); // numbers only
        assertFalse(Name.isValidName("d/o Sandra")); // missing 1st part
        assertFalse(Name.isValidName("Ravin d/o")); // missing 2nd part
        assertFalse(Name.isValidName("Ravin d/o Sandra @ Joseph")); // more than 1 special characters
        assertFalse(Name.isValidName("(Chen Zi Hao")); // bracket portion only
        assertFalse(Name.isValidName("Liu Jun Ning, Gladys, Hock")); // more than 1 comma
        assertFalse(Name.isValidName("Liu Huan, Gladys")); // long names
        assertFalse(Name.isValidName("Tan Sock Chew, Timothy @ Lim Yong June, Brandon")); // D/O cannot hmm

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Ravi d/O Sandra"));
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("Ravi s/o Sandra"));
        assertTrue(Name.isValidName("Lynette Cheryl Luth @ Ong Jia Yi")); // long names
        assertTrue(Name.isValidName("Dave Tan Tze How (Chen Zi Hao)")); // long names
        assertTrue(Name.isValidName("Ravi @Sandra")); // no need spacing
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
