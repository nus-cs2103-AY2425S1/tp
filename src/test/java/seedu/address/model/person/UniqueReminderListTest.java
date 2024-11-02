package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalReminders.REMINDER_ALICE;
import static seedu.address.testutil.TypicalReminders.REMINDER_BENSON;
import static seedu.address.testutil.TypicalReminders.REMINDER_GEORGE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateReminderException;
import seedu.address.model.person.exceptions.ReminderNotFoundException;

public class UniqueReminderListTest {

    private final UniqueReminderList uniqueReminderList = new UniqueReminderList();

    @Test
    public void contains_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.contains(null));
    }

    @Test
    public void contains_reminderNotInList_returnsFalse() {
        assertFalse(uniqueReminderList.contains(REMINDER_GEORGE));
    }

    @Test
    public void contains_reminderInList_returnsTrue() {
        uniqueReminderList.add(REMINDER_ALICE);
        assertTrue(uniqueReminderList.contains(REMINDER_ALICE));
    }

    @Test
    public void add_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.add(null));
    }

    @Test
    public void add_duplicateReminder_throwsDuplicateReminderException() {
        uniqueReminderList.add(REMINDER_ALICE);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList.add(REMINDER_ALICE));
    }

    @Test
    public void remove_nullReminder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.remove(null));
    }

    @Test
    public void remove_reminderDoesNotExist_throwsReminderNotFoundException() {
        assertThrows(ReminderNotFoundException.class, () -> uniqueReminderList.remove(REMINDER_GEORGE));
    }

    @Test
    public void remove_existingReminder_removesReminder() {
        uniqueReminderList.add(REMINDER_ALICE);
        uniqueReminderList.remove(REMINDER_ALICE);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullUniqueReminderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((UniqueReminderList) null));
    }

    @Test
    public void setReminders_uniqueReminderList_replacesOwnListWithProvidedUniqueReminderList() {
        uniqueReminderList.add(REMINDER_ALICE);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(REMINDER_BENSON);
        uniqueReminderList.setReminders(expectedUniqueReminderList);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueReminderList.setReminders((List<Reminder>) null));
    }

    @Test
    public void setReminders_list_replacesOwnListWithProvidedList() {
        uniqueReminderList.add(REMINDER_ALICE);
        List<Reminder> reminderList = Collections.singletonList(REMINDER_BENSON);
        uniqueReminderList.setReminders(reminderList);
        UniqueReminderList expectedUniqueReminderList = new UniqueReminderList();
        expectedUniqueReminderList.add(REMINDER_BENSON);
        assertEquals(expectedUniqueReminderList, uniqueReminderList);
    }

    @Test
    public void setReminders_listWithDuplicateReminders_throwsDuplicateReminderException() {
        List<Reminder> listWithDuplicateReminders = Arrays.asList(REMINDER_ALICE, REMINDER_ALICE);
        assertThrows(DuplicateReminderException.class, () -> uniqueReminderList
                                                             .setReminders(listWithDuplicateReminders));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueReminderList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueReminderList.asUnmodifiableObservableList().toString(), uniqueReminderList.toString());
    }
}
