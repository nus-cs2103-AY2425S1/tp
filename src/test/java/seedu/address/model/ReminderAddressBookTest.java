package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.REMINDER_ALICE;
import static seedu.address.testutil.TypicalReminders.REMINDER_GEORGE;
import static seedu.address.testutil.TypicalReminders.getTypicalReminderAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Reminder;
import seedu.address.model.person.exceptions.DuplicateReminderException;
import seedu.address.model.util.SampleDataUtil;

public class ReminderAddressBookTest {

    private final ReminderAddressBook reminderAddressBook = new ReminderAddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), reminderAddressBook.getReminderList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderAddressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyReminderAddressBook_replacesData() {
        ReminderAddressBook newData = getTypicalReminderAddressBook();
        reminderAddressBook.resetData(newData);
        assertEquals(newData, reminderAddressBook);
    }

    @Test
    public void resetData_withDuplicateReminders_throwsDuplicatePersonException() {
        // Two reminders with the same fields
        Reminder editedMeetingJason = new Reminder("12-12-2024", "Mock interview with Alice",
                new Name("Alice Pauline"));
        List<Reminder> newReminders = Arrays.asList(REMINDER_ALICE, editedMeetingJason);
        ReminderAddressBookStub newData = new ReminderAddressBookStub(newReminders);

        assertThrows(DuplicateReminderException.class, () -> reminderAddressBook.resetData(newData));
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderAddressBook.hasReminder(null));
    }

    @Test
    public void hasReminder_reminderNotInReminderAddressBook_returnsFalse() {
        assertFalse(reminderAddressBook.hasReminder(REMINDER_GEORGE));
    }

    @Test
    public void hasReminder_reminderInReminderAddressBook_returnsTrue() {
        reminderAddressBook.addReminder(REMINDER_GEORGE);
        assertTrue(reminderAddressBook.hasReminder(REMINDER_GEORGE));
    }

    @Test
    public void notNull_inSampleReminderAddressBook_returnsTrue() {
        ReminderAddressBook reminderAddressBook1 = (ReminderAddressBook) SampleDataUtil.getSampleReminderAddressBook();
        assertTrue(reminderAddressBook1 != null);
    }

    @Test
    public void hasReminder_reminderWithSameIdentityFieldsInReminderAddressBook_returnsTrue() {
        reminderAddressBook.addReminder(REMINDER_ALICE);
        Reminder edited = new Reminder("12-12-2024", "Mock interview with Alice",
                new Name("Alice Pauline"));
        assertTrue(reminderAddressBook.hasReminder(edited));
    }

    @Test
    public void getReminderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> reminderAddressBook.getReminderList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = ReminderAddressBook.class.getCanonicalName()
                          + "{reminders=" + reminderAddressBook.getReminderList() + "}";
        assertEquals(expected, reminderAddressBook.toString());
    }

    /**
     * A stub ReadOnlyReminderAddressBook whose reminders list can violate interface constraints.
     */
    private static class ReminderAddressBookStub implements ReadOnlyReminderAddressBook {
        private final ObservableList<Reminder> reminders = FXCollections.observableArrayList();

        ReminderAddressBookStub(Collection<Reminder> reminders) {
            this.reminders.setAll(reminders);
        }

        @Override
        public ObservableList<Reminder> getReminderList() {
            return reminders;
        }
    }

}
