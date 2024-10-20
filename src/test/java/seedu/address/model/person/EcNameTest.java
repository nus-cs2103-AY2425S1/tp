package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EcNameTest {

    @Test
    public void isValidEcName() {
        // invalid name
        assertFalse(EcName.isValidEcName(null));
        assertFalse(EcName.isValidEcName(" ")); // spaces only
        assertFalse(EcName.isValidEcName("^")); // only non-alphanumeric characters
        assertFalse(EcName.isValidEcName("jane*"));
        // contains non-alphanumeric characters

        // valid name
        assertTrue(EcName.isValidEcName("")); // empty string
        assertTrue(EcName.isValidEcName("leo wilson")); // alphabets only
        assertTrue(EcName.isValidEcName("12345")); // numbers only
        assertTrue(EcName.isValidEcName("Jack the 3rd")); // alphanumeric characters
        assertTrue(EcName.isValidEcName("Capital Tan")); // with capital letters
        assertTrue(EcName.isValidEcName("David Roger Jackson Ray Jr 2nd"));
        // long names
    }
    @Test
    public void equals() {
        EcName ecName = new EcName("Valid Name");

        // same values -> returns true
        assertTrue(ecName.equals(new EcName("Valid Name")));

        // same object -> returns true
        assertTrue(ecName.equals(ecName));

        // null -> returns false
        assertFalse(ecName.equals(null));

        // different types -> returns false
        assertFalse(ecName.equals(5.0f));

        // different values -> returns false
        assertFalse(ecName.equals(new Name("Jack Name")));
    }
}
