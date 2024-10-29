package seedu.address.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Reminder;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicateReminderException;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.MEETINGJASON;
import static seedu.address.testutil.TypicalReminders.getTypicalReminderAddressBook;

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
        Reminder editedMeetingJason = new Reminder("10-10-2022", "project meeting", new Name("Jason"));
        List<Reminder> newReminders = Arrays.asList(MEETINGJASON, editedMeetingJason);
        ReminderAddressBookStub newData = new ReminderAddressBookStub(newReminders);

        assertThrows(DuplicateReminderException.class, () -> reminderAddressBook.resetData(newData));
    }

    @Test
    public void hasReminder_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> reminderAddressBook.hasReminder(null));
    }

    @Test
    public void hasReminder_reminderNotInReminderAddressBook_returnsFalse() {
        assertFalse(reminderAddressBook.hasReminder(MEETINGJASON));
    }

    @Test
    public void hasReminder_reminderInReminderAddressBook_returnsTrue() {
        reminderAddressBook.addReminder(MEETINGJASON);
        assertTrue(reminderAddressBook.hasReminder(MEETINGJASON));
    }

    @Test
    public void notNull_inSampleReminderAddressBook_returnsTrue() {
        ReminderAddressBook reminderAddressBook1 = (ReminderAddressBook) SampleDataUtil.getSampleReminderAddressBook();
        assertTrue(reminderAddressBook1 != null);
    }

    @Test
    public void hasReminder_reminderWithSameIdentityFieldsInReminderAddressBook_returnsTrue() {
        reminderAddressBook.addReminder(MEETINGJASON);
        Reminder editedMeetingJason = new Reminder("10-10-2022", "project meeting", new Name("Jason"));
        assertTrue(reminderAddressBook.hasReminder(editedMeetingJason));
    }

    @Test
    public void getReminderList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> reminderAddressBook.getReminderList().remove(0));
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
