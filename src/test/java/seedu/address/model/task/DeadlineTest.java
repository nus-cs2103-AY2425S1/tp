package seedu.address.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

//@@author gho7sie

public class DeadlineTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Deadline(null));
    }

    @Test
    public void isValidDeadline() {
        //invalid strings
        assertFalse(Deadline.isValidDeadline("")); // empty string
        assertFalse(Deadline.isValidDeadline(" "));
        assertFalse(Deadline.isValidDeadline("^"));
        assertFalse(Deadline.isValidDeadline("wow a deadline")); // string
        assertFalse(Deadline.isValidDeadline("Wowdeadline")); // string with no spaces


        // invalid date format
        assertFalse(Deadline.isValidDeadline("2024/09/09")); // date only
        assertFalse(Deadline.isValidDeadline("1800")); // time only
        assertFalse(Deadline.isValidDeadline("2025-02-29 1800")); // valid format, invalid leap year
        assertFalse(Deadline.isValidDeadline("-0001-02-29 1800")); // negative year
        assertFalse(Deadline.isValidDeadline("2025-02-29 3000")); // invalid time
        assertFalse(Deadline.isValidDeadline("2025-04-32 1800")); // invalid date
        assertFalse(Deadline.isValidDeadline("2025-13-29 1800")); // invalid month
        assertFalse(Deadline.isValidDeadline("2025-02-29 6 AM")); // wrong time format

        // valid date
        assertTrue(Deadline.isValidDeadline("2024-02-29 1800")); // valid date
        assertTrue(Deadline.isValidDeadline("2024-02-29 1800")); // valid leap year
    }

    @Test
    public void testHashCode() {
        Deadline deadline = new Deadline(LocalDateTime.of(2024, 9, 13, 0, 0));

        // different value -> false
        assertNotEquals(deadline.hashCode(), new Deadline(LocalDateTime.of(2024, 9, 14, 0, 0)).hashCode());

        assertEquals(deadline.hashCode(), deadline.hashCode());
    }

    @Test
    public void testEquals() {
        Deadline deadline = new Deadline(LocalDateTime.of(2024, 9, 13, 0, 0));

        // null -> false
        assertFalse(deadline.equals(null));

        // not LocalDateTime -> false
        assertFalse(deadline.equals("huh"));
        assertFalse(deadline.equals(1));

        // different value -> false
        assertFalse(deadline.equals(new Deadline(LocalDateTime.of(2025, 9, 13, 0, 0))));

        // same values -> true
        assertTrue(deadline.equals(new Deadline(LocalDateTime.of(2024, 9, 13, 0, 0))));

        // same object -> true
        assertTrue(deadline.equals(deadline));
    }

    @Test
    public void testToString() {
        Deadline deadline = new Deadline(LocalDateTime.of(2024, 9, 13, 0, 0));
        String expectedDeadlineString = "13 Sep 2024 0000";
        assertEquals(deadline.toString(), expectedDeadlineString);
    }

}
