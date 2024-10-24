package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DaysAttendedTest {

    @Test
    public void constructor_invalidDaysAttended_throwsIllegalArgumentException() {
        // Negative value
        int invalidDays = -1;
        assertThrows(IllegalArgumentException.class, () -> new DaysAttended(invalidDays));
    }

    @Test
    public void constructor_validDaysAttended_success() {
        // Zero value
        int zeroDays = 0;
        DaysAttended daysAttendedZero = new DaysAttended(zeroDays);
        assertEquals(zeroDays, daysAttendedZero.getDaysAttended());

        // Positive value
        int positiveDays = 5;
        DaysAttended daysAttendedPositive = new DaysAttended(positiveDays);
        assertEquals(positiveDays, daysAttendedPositive.getDaysAttended());
    }

    @Test
    public void isValidDaysAttended() {
        // Negative value -> returns false
        assertFalse(DaysAttended.isValidDaysAttended(-1));

        // Zero value -> returns true
        assertTrue(DaysAttended.isValidDaysAttended(0));

        // Positive value -> returns true
        assertTrue(DaysAttended.isValidDaysAttended(5));
    }

    @Test
    public void increment_success() {
        DaysAttended daysAttended = new DaysAttended(5);
        daysAttended.increment();
        assertEquals(6, daysAttended.getDaysAttended());
    }

    @Test
    public void decrement_success() {
        // When days attended is greater than 0
        DaysAttended daysAttended = new DaysAttended(5);
        daysAttended.decrement();
        assertEquals(4, daysAttended.getDaysAttended());

        // When days attended is 0 (should not decrement below 0)
        daysAttended = new DaysAttended(0);
        daysAttended.decrement();
        assertEquals(0, daysAttended.getDaysAttended());
    }

    @Test
    public void reset_success() {
        DaysAttended daysAttended = new DaysAttended(5);
        daysAttended.reset();
        assertEquals(0, daysAttended.getDaysAttended());
    }

    @Test
    public void equals() {
        DaysAttended days1 = new DaysAttended(5);
        DaysAttended days2 = new DaysAttended(5);
        DaysAttended days3 = new DaysAttended(3);

        // Same object -> returns true
        assertTrue(days1.equals(days1));

        // Same values -> returns true
        assertTrue(days1.equals(days2));

        // Different values -> returns false
        assertFalse(days1.equals(days3));

        // Different type -> returns false
        assertFalse(days1.equals(5));

        // Null -> returns false
        assertFalse(days1.equals(null));
    }

    @Test
    public void hashCodeTest() {
        DaysAttended days1 = new DaysAttended(5);
        DaysAttended days2 = new DaysAttended(5);
        DaysAttended days3 = new DaysAttended(3);

        // Same values -> same hash code
        assertEquals(days1.hashCode(), days2.hashCode());

        // Different values -> different hash code
        assertFalse(days1.hashCode() == days3.hashCode());
    }

    @Test
    public void toStringTest() {
        DaysAttended daysAttended = new DaysAttended(5);
        assertEquals("5", daysAttended.toString());

        daysAttended = new DaysAttended(0);
        assertEquals("0", daysAttended.toString());
    }
}
