package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.Reminder;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Reminder> PREDICATE_SHOW_ALL_REMINDERS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getClientHubFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setClientHubFilePath(Path clientHubFilePath);

    /**
     * Replaces address book data with the data in {@code clientHub}.
     */
    void setClientHub(ReadOnlyClientHub clientHub);

    /** Returns the ClientHub */
    ReadOnlyClientHub getClientHub();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a reminder with the same identity as {@code reminder} exists in the address book.
     */
    boolean hasReminder(Reminder reminder);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Deletes the given reminder.
     * The reminder must exist in the address book.
     */
    void deleteReminder(Reminder target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Adds the given reminder.
     * {@code reminder} must not already exist in the address book.
     */
    void addReminder(Reminder reminder);
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Replaces the given reminder {@code target} with {@code editedReminder}.
     * {@code target} must exist in the address book.
     * The reminder identity of {@code editedReminder}
     */
    void setReminder(Reminder target, Reminder editedReminder);
    /**
     * Returns the number of persons in the address book.
     */
    int getDisplayPersonsListSize();

    /**
     * Returns the number of reminders in the address book.
     */
    int getDisplayRemindersListSize();

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getDisplayPersons();

    /** Returns an unmodifiable view of the filtered reminder list */
    ObservableList<Reminder> getDisplayReminders();

    /** Updates the current list to display all contacts in the list */
    void updateUnfilteredList();

    /** Updates the current list to display all reminders in the list */
    void updateUnfilteredReminderList();

    /**
     * Updates the current list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the current list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredReminderList(Predicate<Reminder> predicate);

    /**
     * Updates the current list to sort by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void updateSortedPersonList(Comparator<Person> comparator);
}
