package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyClientHub {

    /**
     * Returns an unmodifiable view of the persons list and reminder list.
     * person list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();
    ObservableList<Reminder> getReminderList();

}
