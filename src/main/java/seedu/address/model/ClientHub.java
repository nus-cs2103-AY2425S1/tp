package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.ReminderList;

/**
 * Wraps all data at the address-book level.
 * Duplicates are not allowed (by .isSamePerson comparison).
 */
public class ClientHub implements ReadOnlyClientHub {

    private final UniquePersonList persons;
    private final ReminderList reminders;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        reminders = new ReminderList();
    }

    /**
     * Creates an empty ClientHub.
     */
    public ClientHub() {}

    /**
     * Creates a ClientHub using the data from the given {@code toBeCopied}.
     *
     * @param toBeCopied The ReadOnlyClientHub to copy data from.
     */
    public ClientHub(ReadOnlyClientHub toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with the specified {@code persons}.
     * The list must not contain duplicate persons.
     *
     * @param persons The list of persons to set.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the reminder list with the specified {@code reminders}.
     * The list must not contain duplicate reminders.
     *
     * @param reminders The list of reminders to set.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders.setReminders(reminders);
    }

    /**
     * Resets the existing data of this ClientHub with the specified {@code newData}.
     *
     * @param newData The new data to reset with.
     */
    public void resetData(ReadOnlyClientHub newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setReminders(newData.getReminderList());
    }

    //// person-level operations

    /**
     * Checks if a person with the same identity as the specified {@code person} exists in the address book.
     *
     * @param person The person to check.
     * @return True if the person exists, false otherwise.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Checks if a reminder with the same identity as the specified {@code reminder} exists in the address book.
     *
     * @param reminder The reminder to check.
     * @return True if the reminder exists, false otherwise.
     */
    public boolean hasReminder(Reminder reminder) {
        requireNonNull(reminder);
        return reminders.contains(reminder);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     *
     * @param p The person to add.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Adds a reminder to the address book.
     * The reminder must not already exist in the address book.
     *
     * @param r The reminder to add.
     */
    public void addReminder(Reminder r) {
        reminders.add(r);
    }

    /**
     * Replaces the specified {@code target} person in the list with {@code editedPerson}.
     * The {@code target} must exist in the address book, and the {@code editedPerson} must not have the
     * same identity as another existing person in the address book.
     *
     * @param target The person to replace.
     * @param editedPerson The new person to replace with.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Replaces the specified {@code target} reminder in the list with {@code editedReminder}.
     * The {@code target} must exist in the address book.
     *
     * @param target The reminder to replace.
     * @param editedReminder The new reminder to replace with.
     */
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireNonNull(editedReminder);

        reminders.setReminder(target, editedReminder);
    }

    /**
     * Removes the specified {@code key} person from this ClientHub.
     * The person must exist in the address book.
     *
     * @param key The person to remove.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes the specified {@code key} reminder from this ClientHub.
     * The reminder must exist in the address book.
     *
     * @param key The reminder to remove.
     */
    public void removeReminder(Reminder key) {
        reminders.remove(key);
    }

    //// util methods

    /**
     * Returns a string representation of the ClientHub, including the person and reminder lists.
     *
     * @return A string representation of the ClientHub.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("reminders", reminders)
                .toString();
    }

    /**
     * Returns the person list as an unmodifiable observable list.
     *
     * @return The person list.
     */
    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    /**
     * Returns the reminder list as an unmodifiable observable list.
     *
     * @return The reminder list.
     */
    public ObservableList<Reminder> getReminderList() {
        return reminders.asUnmodifiableObservableList();
    }

    /**
     * Checks if this ClientHub is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ClientHub)) {
            return false;
        }

        ClientHub otherClientHub = (ClientHub) other;
        return persons.equals(otherClientHub.persons) && reminders.equals(otherClientHub.reminders);
    }

    /**
     * Returns the hash code of this ClientHub.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
