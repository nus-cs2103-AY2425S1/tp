package seedu.hireme.model.internshipapplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.hireme.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date((String) null));
        assertThrows(NullPointerException.class, () -> new Date((LocalDate) null));
    }

    @Test
    public void constructor_emptyDate_throwsIllegalArgumentException() {
        String emptyDate = "";
        assertThrows(IllegalArgumentException.class, () -> new Date(emptyDate));
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

    @Test
    public void toStringMethod() {
        Date date = new Date("01/01/24");
        assertEquals("01/01/24", date.toString());
    }
}
