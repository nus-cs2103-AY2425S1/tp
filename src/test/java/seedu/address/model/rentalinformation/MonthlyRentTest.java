package seedu.address.model.rentalinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MonthlyRentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MonthlyRent(null));
    }

    @Test
    public void constructor_invalidMonthlyRent_throwsIllegalArgumentException() {
        String invalidMonthlyRent = "";
        assertThrows(IllegalArgumentException.class, () -> new MonthlyRent(invalidMonthlyRent));
    }

    @Test
    public void isValidMonthlyRent() {
        // null address
        assertThrows(NullPointerException.class, () -> MonthlyRent.isValidMonthlyRent(null));

        // invalid monthly rent
        assertFalse(MonthlyRent.isValidMonthlyRent("")); // empty string
        assertFalse(MonthlyRent.isValidMonthlyRent(" ")); // spaces only
        assertFalse(MonthlyRent.isValidMonthlyRent("123.")); // no decimal values
        assertFalse(MonthlyRent.isValidMonthlyRent("123.2")); // 1 decimal place only
        assertFalse(MonthlyRent.isValidMonthlyRent("123.234")); // more than 2 decimal places
        assertFalse(MonthlyRent.isValidMonthlyRent("123.123.")); // multiple decimal points
        assertFalse(MonthlyRent.isValidMonthlyRent("abc")); // non-numeric
        assertFalse(MonthlyRent.isValidMonthlyRent("-11")); // negative value

        // valid monthly rent
        assertTrue(MonthlyRent.isValidMonthlyRent("5")); // small value
        assertTrue(MonthlyRent.isValidMonthlyRent("10.50")); // decimal value
        assertTrue(MonthlyRent.isValidMonthlyRent("100")); // no decimal value
        assertTrue(MonthlyRent.isValidMonthlyRent("100.20")); // decimal value
        assertTrue(MonthlyRent.isValidMonthlyRent("99999")); // large value
    }

    @Test
    public void equals() {
        MonthlyRent monthlyRent = new MonthlyRent("100");

        // same values -> returns true
        assertTrue(monthlyRent.equals(new MonthlyRent("100")));

        // same object -> returns true
        assertTrue(monthlyRent.equals(monthlyRent));

        // null -> returns false
        assertFalse(monthlyRent.equals(null));

        // different types -> returns false
        assertFalse(monthlyRent.equals(1));

        // different values -> returns false
        assertFalse(monthlyRent.equals(new MonthlyRent("10")));

        // value is null
        // one null
        assertFalse(monthlyRent.equals(new MonthlyRent()));

        monthlyRent = new MonthlyRent();
        assertFalse(monthlyRent.equals(new MonthlyRent("10")));

        // both null
        assertTrue(monthlyRent.equals(new MonthlyRent()));
    }

    @Test
    public void hashcode() {
        MonthlyRent monthlyRent = new MonthlyRent("100");

        // same monthly rent
        assertEquals(monthlyRent.hashCode(), new MonthlyRent("100").hashCode());

        // different monthly rent
        assertNotEquals(monthlyRent.hashCode(), new MonthlyRent("200").hashCode());
    }

    @Test
    public void toStringMethod() {
        MonthlyRent monthlyRent = new MonthlyRent("100");

        // same monthly rent
        assertEquals(monthlyRent.toString(), new MonthlyRent("100").toString());

        // different monthly rent
        assertNotEquals(monthlyRent.toString(), new MonthlyRent("200").toString());
        assertNotEquals(monthlyRent.toString(), new MonthlyRent("100.35").toString());

        // null value in monthly rent
        monthlyRent = new MonthlyRent();
        assertEquals("null", monthlyRent.toString());
    }
}
