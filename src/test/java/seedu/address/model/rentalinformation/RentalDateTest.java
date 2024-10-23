package seedu.address.model.rentalinformation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RentalDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RentalDate(null));
    }

    @Test
    public void constructor_invalidRentalDate_throwsIllegalArgumentException() {
        String invalidRentalDate = "";
        assertThrows(IllegalArgumentException.class, () -> new RentalDate(invalidRentalDate));
    }

    @Test
    public void isValidRentalDate() {
        // null rental date
        assertThrows(NullPointerException.class, () -> RentalDate.isValidRentalDate(null));

        // invalid rental date
        assertFalse(RentalDate.isValidRentalDate("")); // empty string
        assertFalse(RentalDate.isValidRentalDate(" ")); // spaces only
        assertFalse(RentalDate.isValidRentalDate("31/16/2024")); // invalid month
        assertFalse(RentalDate.isValidRentalDate("99/12/2024")); // invalid date
        assertFalse(RentalDate.isValidRentalDate("12/12/3012")); // invalid year
        assertFalse(RentalDate.isValidRentalDate("12/12/0000")); // invalid year
        assertFalse(RentalDate.isValidRentalDate("abc")); // non-numeric
        assertFalse(RentalDate.isValidRentalDate("-1/12/2024")); // negative date
        assertFalse(RentalDate.isValidRentalDate("001/12/2024")); // extra padding 0
        assertFalse(RentalDate.isValidRentalDate("01//12/2024")); // double slash
        assertFalse(RentalDate.isValidRentalDate("0112/2024")); // missing slash
        assertFalse(RentalDate.isValidRentalDate("01122024")); // missing slash
        assertFalse(RentalDate.isValidRentalDate("01/122024")); // missing slash
        assertFalse(RentalDate.isValidRentalDate("1/1/2024")); // no padding
        assertFalse(RentalDate.isValidRentalDate("01-01-2024")); // wrong divider

        // valid rental date
        assertTrue(RentalDate.isValidRentalDate("01/01/2024"));
        assertTrue(RentalDate.isValidRentalDate("31/12/2024"));
        assertTrue(RentalDate.isValidRentalDate("06/07/1995"));
        assertTrue(RentalDate.isValidRentalDate("08/11/2030"));
    }

    @Test
    public void equals() {
        RentalDate rentalDate = new RentalDate("01/01/2024");

        // same values -> returns true
        assertTrue(rentalDate.equals(new RentalDate("01/01/2024")));

        // same object -> returns true
        assertTrue(rentalDate.equals(rentalDate));

        // null -> returns false
        assertFalse(rentalDate.equals(null));

        // different types -> returns false
        assertFalse(rentalDate.equals(5.0f));

        // different values -> returns false
        assertFalse(rentalDate.equals(new RentalDate("31/12/2024")));

        // value is null
        // one null
        assertFalse(rentalDate.equals(new RentalDate()));

        rentalDate = new RentalDate();
        assertFalse(rentalDate.equals(new RentalDate("31/12/2024")));

        // both null
        assertTrue(rentalDate.equals(new RentalDate()));
    }

    @Test
    public void hashcode() {
        RentalDate rentalDate = new RentalDate("01/01/2024");

        // same rental date
        assertEquals(rentalDate.hashCode(), new RentalDate("01/01/2024").hashCode());

        // different rental date
        assertNotEquals(rentalDate.hashCode(), new RentalDate("02/01/2024").hashCode());
    }

    @Test
    public void toStringMethod() {
        RentalDate rentalDate = new RentalDate("01/01/2024");

        // same rental date
        assertEquals(rentalDate.toString(), new RentalDate("01/01/2024").toString());

        // different rental date
        assertNotEquals(rentalDate.toString(), new RentalDate("02/01/2024").toString());
        assertNotEquals(rentalDate.toString(), new RentalDate("01/01/2023").toString());

        // null value in rental date
        rentalDate = new RentalDate();
        assertEquals("null", rentalDate.toString());
    }
}
