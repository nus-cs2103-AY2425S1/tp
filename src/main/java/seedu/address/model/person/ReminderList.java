package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a list containing all reminders related to a specific contact
 */
public class ReminderList {

    private final ObservableList<Reminder> listOfReminders = FXCollections.observableArrayList();

    /**
     * Adds a reminder to the list of reminders.
     * @param reminder Reminder to add.
     */
    public void addReminder(Reminder reminder) {
        requireNonNull(reminder);
        listOfReminders.add(reminder);
    }

    /**
     * Gets the reminder at the specified index.
     *
     * @param index Index of reminder to retrieve.
     * @return Reminder that was retrieved.
     */
    public Reminder getReminder(int index) {
        return listOfReminders.get(index);
    }

    /**
     * Gets the list of reminders.
     *
     * @return List of reminders.
     */
    public ObservableList<Reminder> getListOfReminders() {
        return listOfReminders;
    }

    /**
     * Gets the size of reminder list.
     *
     * @return Size of reminder list.
     */
    public int numberOfReminders() {
        return listOfReminders.size();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < listOfReminders.size(); i++) {
            //index
            str.append(listOfReminders.get(i).toString());
        }
        return str.toString();
    }
}
