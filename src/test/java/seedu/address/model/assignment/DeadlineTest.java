package seedu.address.model.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void constructor_validDeadline_success() {
        // valid deadline
        Deadline deadline = new Deadline("2024-12-01");
        assertEquals(LocalDate.of(2024, 12, 1), deadline.deadline);
    }

    @Test
    public void constructor_invalidDeadline_throwsIllegalArgumentException() {
        // invalid deadlines
        assertThrows(IllegalArgumentException.class, () -> new Deadline("2024-13-01")); // invalid month
        assertThrows(IllegalArgumentException.class, () -> new Deadline("2024-12-32")); // invalid day
        assertThrows(IllegalArgumentException.class, () -> new Deadline("invalid-date")); // non-date string
    }

    @Test
    public void isValidDeadline() {
        // valid deadlines
        assertTrue(Deadline.isValidDeadline("2024-01-01")); // regular date
        assertTrue(Deadline.isValidDeadline("2000-02-29")); // leap year date

        // invalid deadlines
        assertFalse(Deadline.isValidDeadline("2024-13-01")); // invalid month
        assertFalse(Deadline.isValidDeadline("2024-12-32")); // invalid day
        assertFalse(Deadline.isValidDeadline("01-01-2024")); // wrong format
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline("some text")); // non-date string
    }

    @Test
    public void testToString() {
        Deadline deadline = new Deadline("2024-12-01");
        assertEquals("2024-12-01", deadline.deadline.format(Deadline.DATE_TIME_FORMATTER));
    }

    @Test
    public void testEquals() {
        Deadline deadline1 = new Deadline("2024-12-01");
        Deadline deadline2 = new Deadline("2024-12-01");
        Deadline deadline3 = new Deadline("2023-12-01");

        assertTrue(deadline1.equals(deadline2)); // same date
        assertFalse(deadline1.equals(deadline3)); // different date
        assertFalse(deadline1.equals(null)); // null
        assertFalse(deadline1.equals("2024-12-01")); // different type
    }
}
