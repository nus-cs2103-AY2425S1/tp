package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
public class PaidTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Paid(null));
    }

    @Test
    void constructor_invalidPaid_throwsIllegalArgumentException() {
        String invalidPaid = "";
        assertThrows(IllegalArgumentException.class, () -> new Paid(invalidPaid));
    }

    @Test
    void isValidPaid() {
        // null paid
        assertThrows(NullPointerException.class, () -> Paid.isValidPaid(null));

        // invalid paids
        assertFalse(Paid.isValidPaid("")); // empty string
        assertFalse(Paid.isValidPaid(" ")); // spaces only
        assertFalse(Paid.isValidPaid("1.234")); // more than 2 decimal places
        assertFalse(Paid.isValidPaid("1.2.3")); // more than 1 decimal point
        assertFalse(Paid.isValidPaid("-1.23")); // negative number

        // valid paids
        assertTrue(Paid.isValidPaid("1")); // 0 decimal places
        assertTrue(Paid.isValidPaid("1.2")); // 1 decimal place
        assertTrue(Paid.isValidPaid("123.23")); // 2 decimal places
        assertTrue(Paid.isValidPaid("0")); // 3 digits
    }

    @Test
    void toStringTest() {
        Paid paid = new Paid("1.23");
        assertEquals("1.23", paid.toString());

        Paid paid2 = new Paid("1.0");
        assertEquals("1.00", paid2.toString());

        Paid paid3 = new Paid("0");
        assertEquals("0.00", paid3.toString());
    }

    @Test
    void equals() {
        Paid paid = new Paid("1.23");
        Paid samePaid = new Paid("1.23");
        Paid differentPaid = new Paid("1.24");

        // same values -> returns true
        assertTrue(paid.equals(samePaid));

        // same object -> returns true
        assertTrue(paid.equals(paid));

        // null -> returns false
        assertFalse(paid.equals(null));

        // different types -> returns false
        assertFalse(paid.equals(5.0f));

        // different values -> returns false
        assertFalse(paid.equals(differentPaid));
    }

    @Test
    void hashCodeTest() {
        Paid paid = new Paid("1.23");
        Paid samePaid = new Paid("1.23");
        Paid differentPaid = new Paid("1.24");

        // same values -> returns true
        assertTrue(paid.hashCode() == samePaid.hashCode());

        // different values -> returns false
        assertFalse(paid.hashCode() == differentPaid.hashCode());

    }
}
