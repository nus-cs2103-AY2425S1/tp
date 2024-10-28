package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of reminders that does not allow nulls.
 * Supports a minimal set of list operations.
 */
public class ReminderList implements Iterable<Reminder> {

    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reminder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     *
     * @param toCheck The reminder to check.
     * @return True if the reminder is found in the list, false otherwise.
     */
    public boolean contains(Reminder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameReminder);
    }

    /**
     * Adds a reminder to the list.
     *
     * @param toAdd The reminder to add. Must not already exist in the list.
     */
    public void add(Reminder toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }

    /**
     * Replaces the given reminder {@code target} in the list with {@code editedReminder}.
     *
     * @param target The reminder to be replaced. Must exist in the list.
     * @param editedReminder The reminder to replace {@code target} with.
     * @throws ReminderNotFoundException If {@code target} is not found in the list.
     */
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireAllNonNull(target, editedReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }

        internalList.set(index, editedReminder);
    }

    /**
     * Removes the specified reminder from the list.
     *
     * @param toRemove The reminder to remove. Must exist in the list.
     * @throws ReminderNotFoundException If the reminder is not found in the list.
     */
    public void remove(Reminder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReminderNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     *
     * @param replacement The list to replace the current list with.
     */
    public void setReminders(ReminderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code reminders}.
     *
     * @param reminders The list of reminders to set.
     */
    public void setReminders(List<Reminder> reminders) {
        requireAllNonNull(reminders);
        internalList.setAll(reminders);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     *
     * @return The unmodifiable observable list of reminders.
     */
    public ObservableList<Reminder> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Reminder> iterator() {
        return internalList.iterator();
    }

    /**
     * Checks if this list is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if the lists are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ReminderList)) {
            return false;
        }

        ReminderList otherReminderList = (ReminderList) other;
        return internalList.equals(otherReminderList.internalList);
    }

    /**
     * Returns the hash code of the list.
     *
     * @return The hash code of the internal list.
     */
    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns a string representation of the reminder list.
     *
     * @return A string representing the reminder list.
     */
    @Override
    public String toString() {
        return internalList.toString();
    }

}
