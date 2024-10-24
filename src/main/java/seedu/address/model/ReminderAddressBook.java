package seedu.address.model;

import javafx.collections.ObservableList;
import java.util.List;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Reminder;
import seedu.address.model.person.UniqueReminderList;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the reminder address-book level
 */
public class ReminderAddressBook implements ReadOnlyReminderAddressBook {

    private final UniqueReminderList reminderList;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        reminderList = new UniqueReminderList();
    }

    public ReminderAddressBook() {}

    /**
     * Creates an ReminderAddressBook using the Reminders in the {@code toBeCopied}
     */
    public ReminderAddressBook(ReadOnlyReminderAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the reminder list with {@code reminders}.
     * {@code reminders} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminderList.setReminders(reminders);
    }

    /**
     * Resets the existing data of this {@code ReminderAddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyReminderAddressBook newData) {
        requireNonNull(newData);

        setReminders(newData.getReminderList());
    }

    //// reminder-level operations

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in the reminder address book.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminderList.contains(reminder);
    }

    /**
     * Adds a reminder to the reminder address book.
     */
    public void addReminder(Reminder r) {
        reminderList.add(r);
    }

    /**
     * Removes {@code key} from this {@code ReminderAddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeReminder(Reminder key) {
        reminderList.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("reminder", reminderList)
                .toString();
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return reminderList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderAddressBook)) {
            return false;
        }

        ReminderAddressBook otherReminderAddressBook = (ReminderAddressBook) other;
        return reminderList.equals(otherReminderAddressBook.reminderList);
    }

    @Override
    public int hashCode() {
        return reminderList.hashCode();
    }
}
