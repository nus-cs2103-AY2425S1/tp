package seedu.address.model.property;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LandlordNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LandlordName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new LandlordName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> LandlordName.isValidName(null));

        // invalid name
        assertFalse(LandlordName.isValidName("")); // empty string
        assertFalse(LandlordName.isValidName(" ")); // spaces only
        assertFalse(LandlordName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(LandlordName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(LandlordName.isValidName("peter jack")); // alphabets only
        assertTrue(LandlordName.isValidName("12345")); // numbers only
        assertTrue(LandlordName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(LandlordName.isValidName("Capital Tan")); // with capital letters
        assertTrue(LandlordName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void equals() {
        LandlordName name = new LandlordName("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new LandlordName("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new LandlordName("Other Valid Name")));
    }
}
