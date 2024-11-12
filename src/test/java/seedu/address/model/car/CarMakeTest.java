package seedu.address.model.car;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CarMakeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CarMake(null));
    }

    @Test
    public void constructor_invalidCarMake_throwsIllegalArgumentException() {
        String invalidCarMake = "";
        assertThrows(IllegalArgumentException.class, () -> new CarMake(invalidCarMake));
    }

    @Test
    public void isValidCarMake() {
        // null make
        assertThrows(NullPointerException.class, () -> CarMake.isValidCarMake(null));

        // invalid make
        assertFalse(CarMake.isValidCarMake("")); // empty string
        assertFalse(CarMake.isValidCarMake(" ")); // spaces not allowed
        assertFalse(CarMake.isValidCarMake("^")); // only non-alphanumeric characters
        assertFalse(CarMake.isValidCarMake("Hyundai*")); // contains non-alphanumeric characters
        assertFalse(CarMake.isValidCarMake("B M W")); // spaces not allowed
        assertFalse(CarMake.isValidCarMake("12345")); // numbers only
        assertFalse(CarMake.isValidCarMake("hyundai")); // first char not capitalised
        assertFalse(CarMake.isValidCarMake("hYUNDAI")); // first char not capitalised

        // valid make
        assertTrue(CarMake.isValidCarMake("Hyundai")); // alphabets only
        assertTrue(CarMake.isValidCarMake("Bu94t1")); // alphanumeric characters
        assertTrue(CarMake.isValidCarMake("HYUNDAI")); // all capital letters

        // test for length of make (40 characters)
        assertTrue(CarMake.isValidCarMake("SuperLongCarMakeNameTest")); // 24
        assertTrue(CarMake.isValidCarMake("A12345678901234567890123456789012345678")); // 39 characters
        assertTrue(CarMake.isValidCarMake("A123456789012345678901234567890123456789")); // 40 characters
        assertFalse(CarMake.isValidCarMake("A1234567890123456789012345678901234567890")); // 41 characters
    }

    @Test
    public void equals() {
        CarMake carMake = new CarMake("ValidCarMake");

        // same values -> returns true
        assertTrue(carMake.equals(new CarMake("ValidCarMake")));

        // same object -> returns true
        assertTrue(carMake.equals(carMake));

        // null -> returns false
        assertFalse(carMake.equals(null));

        // different types -> returns false
        assertFalse(carMake.equals(5.0f));

        // different values -> returns false
        assertFalse(carMake.equals(new CarMake("OtherValidCarMake")));
    }
}
