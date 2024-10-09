package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ReminderTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        // Test that the constructor throws NullPointerException for null values
        assertThrows(NullPointerException.class, () -> new Reminder(null, "1 day"));
        assertThrows(NullPointerException.class, () -> new Reminder("2024-10-04 1000", null));
    }

    @Test
    public void toString_validAppointmentDateReminderTime_returnsString() {
        // Test that toString returns the expected string format
        String appointmentDateTime = "2024-10-04 1000";
        String reminderTime = "1 day";
        Reminder reminder = new Reminder(appointmentDateTime, reminderTime);
        assertTrue(reminder.toString().equals(appointmentDateTime + reminderTime));
    }

    @Test
    public void equals() {
        // Create a reminder object
        Reminder reminder = new Reminder("2024-10-04 1000", "1 day");

        // same values -> returns true
        assertTrue(reminder.equals(new Reminder("2024-10-04 1000", "1 day")));

        // same object -> returns true
        assertTrue(reminder.equals(reminder));

        // null -> returns false
        assertFalse(reminder.equals(null));

        // different types -> returns false
        assertFalse(reminder.equals(5.0f));

        // different values -> returns false
        assertFalse(reminder.equals(new Reminder("2024-10-05 1000", "1 day")));
        assertFalse(reminder.equals(new Reminder("2024-10-04 1000", "2 days")));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        Reminder reminder1 = new Reminder("2024-10-04 1000", "1 day");
        Reminder reminder2 = new Reminder("2024-10-04 1000", "1 day");

        // same reminder values should produce the same hash code
        assertTrue(reminder1.hashCode() == reminder2.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHashCode() {
        Reminder reminder1 = new Reminder("2024-10-04 1000", "1 day");
        Reminder reminder2 = new Reminder("2024-10-05 1000", "1 day");
        Reminder reminder3 = new Reminder("2024-10-04 1000", "2 days");

        // different reminder values should produce different hash codes
        assertFalse(reminder1.hashCode() == reminder2.hashCode());
        assertFalse(reminder1.hashCode() == reminder3.hashCode());
    }
}
