package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class EventDurationTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventDuration(null, null));
    }

    @Test
    public void isValidDuration() {
        // null duration
        assertThrows(NullPointerException.class, () -> EventDuration.isValidDuration(null, null));

        // invalid duration
        assertFalse(EventDuration.isValidDuration(LocalDate.of(2024, 1, 1),
                LocalDate.of(2023, 1, 1))); // end date before start date

        // valid duration
        assertTrue(EventDuration.isValidDuration(LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 1))); // same start and end date
        assertTrue(EventDuration.isValidDuration(LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2))); // end date after start date
    }

    @Test
    public void equals() {
        EventDuration eventDuration = new EventDuration(LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2));

        // same values -> returns true
        assertTrue(eventDuration.equals(new EventDuration(LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 2))));

        // same object -> returns true
        assertTrue(eventDuration.equals(eventDuration));

        // null -> returns false
        assertFalse(eventDuration.equals(null));

        // different types -> returns false
        assertFalse(eventDuration.equals(5.0f));

        // different values -> returns false
        assertFalse(eventDuration.equals(new EventDuration(LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 1, 3))));
    }
}
