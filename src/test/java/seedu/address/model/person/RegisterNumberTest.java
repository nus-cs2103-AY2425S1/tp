package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RegisterNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RegisterNumber(null));
    }

    @Test
    public void constructor_invalidRegisterNumber_throwsIllegalArgumentException() {
        String invalidRegisterNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new RegisterNumber(invalidRegisterNumber));
    }

    @Test
    public void isValidRegisterNumber() {
        // null register number
        assertThrows(NullPointerException.class, () -> RegisterNumber.isValidRegisterNumber(null));

        // invalid register number
        assertFalse(RegisterNumber.isValidRegisterNumber("")); // empty string
        assertFalse(RegisterNumber.isValidRegisterNumber(" ")); // spaces only
        assertFalse(RegisterNumber.isValidRegisterNumber("a")); // not a number
        assertFalse(RegisterNumber.isValidRegisterNumber("41")); // not between 1 and 40

        // valid register number
        assertTrue(RegisterNumber.isValidRegisterNumber("1")); // min
        assertTrue(RegisterNumber.isValidRegisterNumber("40")); // max
        assertTrue(RegisterNumber.isValidRegisterNumber("5")); // single digit
        assertTrue(RegisterNumber.isValidRegisterNumber("32")); // double digit
    }

    @Test
    public void toInt() {
        RegisterNumber registerNumber = new RegisterNumber("1");

        assertEquals(registerNumber.toInt(), 1);
        assertNotEquals(registerNumber.toInt(), 2);
    }

    @Test
    public void equals() {
        RegisterNumber registerNumber = new RegisterNumber("1");

        // same values -> returns true
        assertTrue(registerNumber.equals(new RegisterNumber("1")));

        // same object -> returns true
        assertTrue(registerNumber.equals(registerNumber));

        // null -> returns false
        assertFalse(registerNumber.equals(null));

        // different types -> returns false
        assertFalse(registerNumber.equals(5.0f));

        // different values -> returns false
        assertFalse(registerNumber.equals(new RegisterNumber("2")));
    }
}
