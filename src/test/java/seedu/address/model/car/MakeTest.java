package seedu.address.model.car;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MakeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Make(null));
    }

    @Test
    public void constructor_invalidMake_throwsIllegalArgumentException() {
        String invalidModel = "";
        assertThrows(IllegalArgumentException.class, () -> new Make(invalidModel));
    }

    @Test
    public void isValidMake() {
        // null make
        assertThrows(NullPointerException.class, () -> Make.isValidMake(null));

        // invalid make
        assertFalse(Make.isValidMake("")); // empty string
        assertFalse(Make.isValidMake(" ")); // spaces not allowed
        assertFalse(Make.isValidMake("^")); // only non-alphanumeric characters
        assertFalse(Make.isValidMake("Hyundai*")); // contains non-alphanumeric characters
        assertFalse(Make.isValidMake("B M W")); // spaces not allowed
        assertFalse(Make.isValidMake("12345")); // numbers only
        assertFalse(Make.isValidMake("hyundai")); // first char not capitalised
        assertFalse(Make.isValidMake("hYUNDAI")); // first char not capitalised

        // valid make
        assertTrue(Make.isValidMake("Hyundai")); // alphabets only
        assertTrue(Make.isValidMake("Bu94t1")); // alphanumeric characters
        assertTrue(Make.isValidMake("HYUNDAI")); // all capital letters
        assertTrue(Make.isValidMake("SuperLongCarMakeNameForHyundaiForTest")); // long makes
    }

    @Test
    public void equals() {
        Make make = new Make("ValidMake");

        // same values -> returns true
        assertTrue(make.equals(new Make("ValidMake")));

        // same object -> returns true
        assertTrue(make.equals(make));

        // null -> returns false
        assertFalse(make.equals(null));

        // different types -> returns false
        assertFalse(make.equals(5.0f));

        // different values -> returns false
        assertFalse(make.equals(new Make("OtherValidModel")));
    }
}
