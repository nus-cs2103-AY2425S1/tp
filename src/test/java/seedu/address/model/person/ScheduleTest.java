package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ScheduleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Schedule(null));
    }

    @Test
    public void toString_validDateTime_returnsString() {
        String validDateTime = "2024-10-04 1000";
        Schedule schedule = new Schedule(validDateTime);
        assertTrue(schedule.toString().equals(validDateTime));
    }

    @Test
    public void equals() {
        Schedule schedule = new Schedule("2024-10-04 1000");

        // same values -> returns true
        assertTrue(schedule.equals(new Schedule("2024-10-04 1000")));

        // same object -> returns true
        assertTrue(schedule.equals(schedule));

        // null -> returns false
        assertFalse(schedule.equals(null));

        // different types -> returns false
        assertFalse(schedule.equals(5.0f));

        // different values -> returns false
        assertFalse(schedule.equals(new Schedule("2024-10-05 1000")));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        Schedule schedule1 = new Schedule("2024-10-04 1000");
        Schedule schedule2 = new Schedule("2024-10-04 1000");

        // same schedule values should produce the same hash code
        assertTrue(schedule1.hashCode() == schedule2.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHashCode() {
        Schedule schedule1 = new Schedule("2024-10-04 1000");
        Schedule schedule2 = new Schedule("2024-10-05 1000");

        // different schedule values should produce different hash codes
        assertFalse(schedule1.hashCode() == schedule2.hashCode());
    }
}
