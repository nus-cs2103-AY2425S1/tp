package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a list containing all reminders related to a specific contact
 */
public class ReminderList {

    private final ObservableList<Reminder> listOfReminders = FXCollections.observableArrayList();

    public void addReminder(Reminder reminder) {
        requireNonNull(reminder);
        listOfReminders.add(reminder);
    }

    public Reminder getReminder(int index) {
        return listOfReminders.get(index);
    }

    public ObservableList<Reminder> getListOfReminders() {
        return listOfReminders;
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
