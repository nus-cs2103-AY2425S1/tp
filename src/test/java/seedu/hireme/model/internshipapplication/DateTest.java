package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.hireme.logic.validator.DateValidator;

public class DateTest {

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate));
    }

    @Test
    public void validate() {
        // invalid date
        assertFalse(DateValidator.of().validate("")); // empty string
        assertFalse(DateValidator.of().validate(" ")); // spaces only
        assertFalse(DateValidator.of().validate("///")); // backslashes only
        assertFalse(DateValidator.of().validate("120524")); // numbers only
        assertFalse(DateValidator.of().validate("12052024")); // numbers only with 4 digit year
        assertFalse(DateValidator.of().validate("12/05/2024")); // invalid year format
        assertFalse(DateValidator.of().validate("12\05\2024")); // forward slashes
        assertFalse(DateValidator.of().validate("12/50/24")); // invalid month
        assertFalse(DateValidator.of().validate("-1/05/24")); // invalid day
        assertFalse(DateValidator.of().validate("12?05?24")); // invalid special characters
        assertFalse(DateValidator.of().validate("12/05/30")); // future date

        // valid date
        assertTrue(DateValidator.of().validate("12/05/24")); // valid date
        assertTrue(DateValidator.of().validate("29/11/19")); // numbers only

    }

    @Test
    public void compare_datesOrder_returnsInteger() {
        Date date1 = new Date("10/01/18");
        Date date2 = new Date("20/05/24");

        assertTrue(date1.compareTo(date2) < 0);
        assertTrue(date2.compareTo(date1) > 0);
        assertEquals(0, date1.compareTo(date1));
        assertEquals(0, date2.compareTo(date2));
    }

    @Test
    public void equals() {
        Date date = new Date("01/01/20");

        // same values -> returns true
        assertTrue(date.equals(new Date("01/01/20")));

        // same object -> returns true
        assertTrue(date.equals(date));

        // null -> returns false
        assertFalse(date.equals(null));

        // different types -> returns false
        assertFalse(date.equals(5.0f));

        // different values -> returns false
        assertFalse(date.equals(new Date("05/05/21")));
    }
}
