package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EcNameTest {

    @Test
    public void isValidEcName() {
        // invalid name

        // EP: null
        assertFalse(EcName.isValidEcName(null));

        // EP: spaces only
        assertFalse(EcName.isValidEcName(" "));

        // EP: only non-alphanumeric characters
        assertFalse(EcName.isValidEcName("^"));

        // EP: contains non-alphanumeric characters
        assertFalse(EcName.isValidEcName("jane*"));

        // contains non-alphanumeric characters

        // valid name
        // EP: empty string
        assertTrue(EcName.isValidEcName(""));
        // EP: alphabets only
        assertTrue(EcName.isValidEcName("leo wilson"));
        // EP: numbers only
        assertTrue(EcName.isValidEcName("12345"));
        // EP: alphanumeric characters
        assertTrue(EcName.isValidEcName("Jack the 3rd"));
        // EP: with capital letters
        assertTrue(EcName.isValidEcName("Capital Tan"));
        // EP: long names
        assertTrue(EcName.isValidEcName("David Roger Jackson Ray Jr 2nd"));

    }
    @Test
    public void equals() {
        EcName ecName = new EcName("Valid Name");

        // EP: same values -> returns true
        assertTrue(ecName.equals(new EcName("Valid Name")));

        // EP: same object -> returns true
        assertTrue(ecName.equals(ecName));

        // EP: null -> returns false
        assertFalse(ecName.equals(null));

        // EP: different types -> returns false
        assertFalse(ecName.equals(5.0f));

        // EP: different values -> returns false
        assertFalse(ecName.equals(new Name("Jack Name")));
    }
}
