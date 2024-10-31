package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

public class ReminderListTest {

    private ReminderList reminderList;
    private final Reminder reminder1 = new Reminder("Alice",
            LocalDateTime.of(2024, 11, 1, 10, 0), new ReminderDescription("Meeting"));
    private final Reminder reminder2 = new Reminder("Bob",
            LocalDateTime.of(2024, 11, 2, 12, 0), new ReminderDescription("Doctor Appointment"));
    private final Reminder reminder3 = new Reminder("Alicey",
            LocalDateTime.of(2024, 11, 3, 14, 0), new ReminderDescription("Conference"));

    @BeforeEach
    public void setUp() {
        reminderList = new ReminderList();
    }

    @Test
    public void contains_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.contains(null));
    }

    @Test
    public void contains_reminderNotInList_returnsFalse() {
        assertFalse(reminderList.contains(reminder1));
    }

    @Test
    public void contains_reminderInList_returnsTrue() {
        reminderList.add(reminder1);
        assertTrue(reminderList.contains(reminder1));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.add(null));
    }

    @Test
    public void add_reminderNotInList_success() {
        reminderList.add(reminder1);
        assertTrue(reminderList.contains(reminder1));
    }

    @Test
    public void setReminder_nullTargetReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.setReminder(null, reminder1));
    }

    @Test
    public void setReminder_nullEditedReminder_throwsNullPointerException() {
        reminderList.add(reminder1);
        assertThrows(NullPointerException.class, () -> reminderList.setReminder(reminder1, null));
    }

    @Test
    public void setReminder_targetReminderNotInList_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> reminderList.setReminder(reminder1, reminder2));
    }

    @Test
    public void setReminder_existingReminder_success() {
        reminderList.add(reminder1);
        reminderList.setReminder(reminder1, reminder2);
        assertTrue(reminderList.contains(reminder2));
        assertFalse(reminderList.contains(reminder1));
    }

    @Test
    public void remove_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderList.remove(null));
    }

    @Test
    public void remove_reminderNotInList_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> reminderList.remove(reminder1));
    }

    @Test
    public void remove_existingReminder_success() {
        reminderList.add(reminder1);
        reminderList.remove(reminder1);
        assertFalse(reminderList.contains(reminder1));
    }

    @Test
    public void setReminders_withReminderList_replacesList() {
        reminderList.add(reminder1);
        ReminderList newReminderList = new ReminderList();
        newReminderList.add(reminder2);
        reminderList.setReminders(newReminderList);
        assertTrue(reminderList.contains(reminder2));
        assertFalse(reminderList.contains(reminder1));
    }

    @Test
    public void setReminders_withList_replacesList() {
        reminderList.add(reminder1);
        List<Reminder> reminderListInput = Arrays.asList(reminder2, reminder3);
        reminderList.setReminders(reminderListInput);
        assertTrue(reminderList.contains(reminder2));
        assertTrue(reminderList.contains(reminder3));
        assertFalse(reminderList.contains(reminder1));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        reminderList.add(reminder1);
        ObservableList<Reminder> observableList = reminderList.asUnmodifiableObservableList();
        assertThrows(UnsupportedOperationException.class, () -> observableList.remove(0));
    }

    @Test
    public void equals_sameContentList_returnsTrue() {
        ReminderList anotherList = new ReminderList();
        anotherList.add(reminder1);
        reminderList.add(reminder1);
        assertEquals(reminderList, anotherList);
    }

    @Test
    public void hashCode_sameContentList_returnsSameHash() {
        ReminderList anotherList = new ReminderList();
        anotherList.add(reminder1);
        reminderList.add(reminder1);
        assertEquals(reminderList.hashCode(), anotherList.hashCode());
    }

    @Test
    public void toString_nonEmptyList() {
        assertEquals(reminderList.asUnmodifiableObservableList().toString(),
                reminderList.toString());
    }
}
