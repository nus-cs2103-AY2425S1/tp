package seedu.address.model.rentalinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RentDueDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RentDueDate(null));
    }

    @Test
    public void constructor_invalidRentDueDate_throwsIllegalArgumentException() {
        String invalidRentDueDate = "";
        assertThrows(IllegalArgumentException.class, () -> new RentDueDate(invalidRentDueDate));
    }

    @Test
    public void isValidRentDueDate() {
        // null rent due date
        assertThrows(NullPointerException.class, () -> RentDueDate.isValidDueDate(null));

        // invalid rent due date
        assertFalse(RentDueDate.isValidDueDate("")); // empty string
        assertFalse(RentDueDate.isValidDueDate(" ")); // spaces only
        assertFalse(RentDueDate.isValidDueDate("0")); // invalid date
        assertFalse(RentDueDate.isValidDueDate("abc")); // non-numeric
        assertFalse(RentDueDate.isValidDueDate("-1")); // negative date
        assertFalse(RentDueDate.isValidDueDate("01")); // extra padding 0
        assertFalse(RentDueDate.isValidDueDate("99")); // invalid date

        // valid rent due date
        assertTrue(RentDueDate.isValidDueDate("1")); // first day
        assertTrue(RentDueDate.isValidDueDate("31")); // last day
        assertTrue(RentDueDate.isValidDueDate("20"));
    }

    @Test
    public void equals() {
        RentDueDate rentDueDate = new RentDueDate("5");

        // same values -> returns true
        assertTrue(rentDueDate.equals(new RentDueDate("5")));

        // same object -> returns true
        assertTrue(rentDueDate.equals(rentDueDate));

        // null -> returns false
        assertFalse(rentDueDate.equals(null));

        // different types -> returns false
        assertFalse(rentDueDate.equals(5.0f));

        // different values -> returns false
        assertFalse(rentDueDate.equals(new RentDueDate("1")));

        // value is null
        // one null
        assertFalse(rentDueDate.equals(new RentDueDate()));

        rentDueDate = new RentDueDate();
        assertFalse(rentDueDate.equals(new RentDueDate("15")));

        // both null
        assertTrue(rentDueDate.equals(new RentDueDate()));
    }

    @Test
    public void hashcode() {
        RentDueDate rentDueDate = new RentDueDate("15");

        // same rent due date
        assertEquals(rentDueDate.hashCode(), new RentDueDate("15").hashCode());

        // different rent due date
        assertNotEquals(rentDueDate.hashCode(), new RentDueDate("20").hashCode());
    }

    @Test
    public void toStringMethod() {
        RentDueDate rentDueDate = new RentDueDate("15");

        // same rent due date
        assertEquals(rentDueDate.toString(), new RentDueDate("15").toString());

        // different rent due date
        assertNotEquals(rentDueDate.toString(), new RentDueDate("10").toString());
        assertNotEquals(rentDueDate.toString(), new RentDueDate("30").toString());

        // null value in rent due date
        rentDueDate = new RentDueDate();
        assertEquals("null", rentDueDate.toString());
    }
}
