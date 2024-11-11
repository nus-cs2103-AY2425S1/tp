package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class ReminderTest {

    private final String samplePersonName = "Alice";
    private final LocalDateTime sampleDateTime = LocalDateTime.of(2024, 11, 1, 10, 0);
    private final ReminderDescription sampleDescription = new ReminderDescription("Meeting at NUS");

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, sampleDateTime, sampleDescription));
        assertThrows(NullPointerException.class, () -> new Reminder(samplePersonName, null, sampleDescription));
        assertThrows(NullPointerException.class, () -> new Reminder(samplePersonName, sampleDateTime, null));
    }

    @Test
    public void constructor_validFields_success() {
        Reminder reminder = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        assertNotNull(reminder);
        assertEquals(samplePersonName, reminder.getPersonName());
        assertEquals(sampleDateTime, reminder.getDateTime());
        assertEquals(sampleDescription, reminder.getDescription());
    }

    @Test
    public void equals_sameAttributes_returnsTrue() {
        Reminder reminder1 = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        Reminder reminder2 = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        assertTrue(reminder1.equals(reminder2));
    }

    @Test
    public void equals_differentAttributes_returnsFalse() {
        Reminder reminder = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        Reminder differentNameReminder = new Reminder("Bob", sampleDateTime, sampleDescription);
        Reminder differentDateTimeReminder = new Reminder(samplePersonName,
                LocalDateTime.of(2024, 11, 1, 15, 0), sampleDescription);
        Reminder differentDescriptionReminder = new Reminder(samplePersonName,
                sampleDateTime, new ReminderDescription("Doctor Appointment"));

        assertNotEquals(reminder, differentNameReminder);
        assertNotEquals(reminder, differentDateTimeReminder);
        assertNotEquals(reminder, differentDescriptionReminder);
    }

    @Test
    public void isSameReminder_sameDateTimeAndDescription_returnsTrue() {
        Reminder reminder1 = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        Reminder reminder2 = new Reminder("Bob", sampleDateTime, sampleDescription); // Different person name
        assertTrue(reminder1.isSameReminder(reminder2));
    }

    @Test
    public void isSameReminder_differentDateTimeOrDescription_returnsFalse() {
        Reminder reminder = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        Reminder differentDateTimeReminder = new Reminder(samplePersonName,
                LocalDateTime.of(2024, 11, 1, 15, 0), sampleDescription);
        Reminder differentDescriptionReminder = new Reminder(samplePersonName,
                sampleDateTime, new ReminderDescription("Different Description"));

        assertFalse(reminder.isSameReminder(differentDateTimeReminder));
        assertFalse(reminder.isSameReminder(differentDescriptionReminder));
    }

    @Test
    public void getReminderWithFullName_newFullName_success() {
        Reminder reminder = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        String newFullName = "Alice Johnson";
        Reminder updatedReminder = reminder.getReminderWithFullName(newFullName);

        assertEquals(newFullName, updatedReminder.getPersonName());
        assertEquals(reminder.getDateTime(), updatedReminder.getDateTime());
        assertEquals(reminder.getDescription(), updatedReminder.getDescription());
    }

    @Test
    public void hashCode_sameAttributes_returnsSameHash() {
        Reminder reminder1 = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        Reminder reminder2 = new Reminder(samplePersonName, sampleDateTime, sampleDescription);
        assertEquals(reminder1.hashCode(), reminder2.hashCode());
    }
}
