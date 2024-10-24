package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Reminder;

import java.util.ArrayList;

/**
 * Unmodifiable view of a reminder list
 */
public interface ReadOnlyReminderAddressBook {

    /**
     * Returns an unmodifiable view of the reminder list.
     * This list will not contain any duplicate reminders.
     */
    ObservableList<Reminder> getReminderList();

}
