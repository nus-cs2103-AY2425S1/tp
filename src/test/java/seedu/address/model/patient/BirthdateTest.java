package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class BirthdateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Birthdate(null));
    }

    @Test
    public void constructor_emptyBirthdate_throwsIllegalArgumentException() {
        String invalidBirthdate = "";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    @Test
    public void constructor_spaceOnlyBirthdate_throwsIllegalArgumentException() {
        String invalidBirthdate = " ";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    //Testing day upper bound + 1
    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidBirthdate = "1997-02-30";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    //Testing month upper bound + 1
    @Test
    public void constructor_invalidMonth_throwsIllegalArgumentException() {
        String invalidBirthdate = "1997-13-01";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    //Testing month lower bound - 1
    @Test
    public void constructor_invalidMonthZero_throwsIllegalArgumentException() {
        String invalidBirthdate = "1997-00-01";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    //Testing day lower bound - 1
    @Test
    public void constructor_invalidDayZero_throwsIllegalArgumentException() {
        String invalidBirthdate = "1997-01-00";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    @Test
    public void constructor_invalidFormatSlash_throwsIllegalArgumentException() {
        String invalidBirthdate = "1997/01/01";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    //Testing invalid date order
    @Test
    public void constructor_invalidFormatDash_throwsIllegalArgumentException() {
        String invalidBirthdate = "01-01-1997";
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    @Test
    public void constructor_futureDate_throwsIllegalArgumentException() {
        String invalidBirthdate = LocalDate.now().plusDays(1).toString();
        assertThrows(IllegalArgumentException.class, () -> new Birthdate(invalidBirthdate));
    }

    @Test
    public void isValidBirthdate() {
        // null birthdate
        assertThrows(NullPointerException.class, () -> Birthdate.isValidBirthdate(null));

        // invalid birthdates
        assertFalse(Birthdate.isValidBirthdate("")); // empty string
        assertFalse(Birthdate.isValidBirthdate(" ")); // spaces only
        assertFalse(Birthdate.isValidBirthdate("1997-02-30")); // invalid date
        assertFalse(Birthdate.isValidBirthdate("1997-13-01")); // invalid month
        assertFalse(Birthdate.isValidBirthdate("1997-00-01")); // invalid month
        assertFalse(Birthdate.isValidBirthdate("1997-01-00")); // invalid day
        assertFalse(Birthdate.isValidBirthdate("1997/01/01")); // invalid format
        assertFalse(Birthdate.isValidBirthdate("01-01-1997")); // invalid format
        assertFalse(Birthdate.isValidBirthdate(LocalDate.now().plusDays(1).toString())); // future date

        // valid birthdates
        assertTrue(Birthdate.isValidBirthdate("1997-01-01"));
        assertTrue(Birthdate.isValidBirthdate("2000-02-29")); // leap year
        assertTrue(Birthdate.isValidBirthdate(LocalDate.now().toString())); // current date
    }

    @Test
    public void equals() {
        Birthdate birthdate1 = new Birthdate("1997-01-01");
        Birthdate birthdate2 = new Birthdate("1997-01-01");
        Birthdate birthdate3 = new Birthdate("2000-02-29");

        // same object -> returns true
        assertTrue(birthdate1.equals(birthdate1));

        // same values -> returns true
        assertTrue(birthdate1.equals(birthdate2));

        // different types -> returns false
        assertFalse(birthdate1.equals(1));

        // null -> returns false
        assertFalse(birthdate1.equals(null));

        // different birthdate -> returns false
        assertFalse(birthdate1.equals(birthdate3));
    }
}
