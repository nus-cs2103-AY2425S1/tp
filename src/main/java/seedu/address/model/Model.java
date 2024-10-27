package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contactrecord.ContactRecord;
import seedu.address.model.contactrecord.ContactRecordList;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with similar identity as {@code person} exists in the address book.
     * The conditions for similarity are defined in {@code Person::isSimilarPerson}.
     *
     * @param person Person to check
     * @return True if a similar person exists, false otherwise
     */
    boolean hasSimilarPerson(Person person);

    /**
     * Returns true if a person with similar identity as {@code person} exists in the address book,
     * possibly excluding one person from the check.
     * The conditions for similarity are defined in {@code Person::isSimilarPerson}.
     *
     * @param person Person to check
     * @param exclude Person to exclude from the check (can be null)
     * @return True if a similar person exists, false otherwise
     */
    boolean hasSimilarPerson(Person person, Person exclude);

    /**
     * Returns true if the current view is the history view.
     */
    boolean isHistoryView();

    /**
     * Sets the current view to the history view.
     *
     * @param historyView True if the current view is the history view, false otherwise
     */
    void setHistoryView(boolean historyView);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Marks the given person as contacted.
     * The person must exist in the address book.
     */
    void markAsContacted(Person target, ContactRecord contactRecord);

    /**
     * Returns the call history of the given person in the address book.
     * The person must exist in the address book.
     */
    ContactRecordList getCallHistory(Person target);

    /**
     * Updates the displayed list to show the call history of the given person.
     * The call history must not be empty.
     */
    void updateDisplayedList(ContactRecordList callHistory);

    /**
     * Returns the displayed call history.
     */
    ObservableList<ContactRecord> getDisplayedCallHistory();

    /**
     * Returns the person in the address book with the given Nric.
     */
    Person getPersonByNric(Nric nric);

    /**
     * Returns an unmodifiable view of the sorted and filtered person list
     */
    SortedList<Person> getSortedFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds a command text to the command history.
     * @param commandText The command text to be added to the history.
     */
    void addCommandTextToHistory(String commandText);

    /**
     * Returns the previous command text from the command history.
     */
    String getPreviousCommandTextFromHistory();

    /**
     * Returns the next command text from the command history.
     */
    String getNextCommandTextFromHistory();
}
