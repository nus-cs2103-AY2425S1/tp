package seedu.address.model.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RateTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Rate(null));
    }

    @Test
    void constructor_invalidRate_throwsIllegalArgumentException() {
        String invalidRate = "";
        assertThrows(IllegalArgumentException.class, () -> new Rate(invalidRate));
    }

    @Test
    void isValidRate() {
        String maxValueString = Double.toString(Double.MAX_VALUE);
        // null rate
        assertThrows(NullPointerException.class, () -> Rate.isValidRate(null));

        // invalid rates
        assertFalse(Rate.isValidRate("")); // empty string
        assertFalse(Rate.isValidRate(" ")); // spaces only
        assertFalse(Rate.isValidRate("1.234")); // more than 2 decimal places
        assertFalse(Rate.isValidRate("1.2.3")); // more than 1 decimal point
        assertFalse(Rate.isValidRate("-1.23")); // negative number
        assertFalse(Rate.isValidRate("1000.01")); // more than max value
        assertFalse(Rate.isValidRate("0")); // 0 rate


        // valid rates
        assertTrue(Rate.isValidRate("1")); // 0 decimal places
        assertTrue(Rate.isValidRate("1.2")); // 1 decimal place
        assertTrue(Rate.isValidRate("123.23")); // 2 decimal places

        // boundary value
        assertTrue(Rate.isValidRate("0.01"));
        assertTrue(Rate.isValidRate("1000")); // boundary value

    }

    @Test
    void toStringTest() {
        Rate rate = new Rate("1.23");
        assertEquals("1.23", rate.toString());

        Rate rate2 = new Rate("1.0");
        assertEquals("1.00", rate2.toString());

        Rate rate3 = new Rate("0");
        assertEquals("0.00", rate3.toString());
    }

    @Test
    void equals() {
        Rate rate = new Rate("1.23");
        Rate sameRate = new Rate("1.23");
        Rate differentRate = new Rate("1.24");

        // same values -> returns true
        assertTrue(rate.equals(sameRate));

        // same object -> returns true
        assertTrue(rate.equals(rate));

        // null -> returns false
        assertFalse(rate.equals(null));

        // different types -> returns false
        assertFalse(rate.equals(5.0f));

        // different values -> returns false
        assertFalse(rate.equals(differentRate));
    }

    @Test
    public void hashCodeTest() {
        Rate rate = new Rate("1.23");
        Rate sameRate = new Rate("1.23");
        Rate differentRate = new Rate("1.24");

        // same values -> returns true
        assertEquals(rate.hashCode(), sameRate.hashCode());

        // different values -> returns false
        assertNotEquals(rate.hashCode(), differentRate.hashCode());

    }
}
