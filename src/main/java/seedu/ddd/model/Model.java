package seedu.ddd.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.ddd.commons.core.GuiSettings;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.event.common.Event;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Contact> PREDICATE_SHOW_ALL_CONTACTS = unused -> true;
    Predicate<Event> PREDICATE_SHOW_ALL_EVENTS = all -> true;

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
    boolean hasContact(Contact contact);

    /**
     * Returns true if a event with the same identity as {@code event} exists in the address book.
     */
    boolean hasEvent(Event event);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deleteContact(Contact target);

    /**
     * Deletes the given event.
     * The event must exist in the address book.
     */
    void deleteEvent(Event event);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addContact(Contact contact);

    /**
     * Adds the given event.
     * {@code event} must not already exist in the address book.
     */
    void addEvent(Event event);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setContact(Contact target, Contact editedContact);

    /**
     * Replaces the given event {@code target} with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    void setEvent(Event target, Event editedEvent);

    /** Returns an unmodifiable view of the filtered contact list */
    ObservableList<Contact> getFilteredContactList();

    /** Returns a unmodifiable view of the filtered event list */
    ObservableList<Event> getFilteredEventList();

    /** Returns a unmodifiable view of the list of the items to display */
    ObservableList<Displayable> getDisplayedList();

    /**
     * Updates the filter of the filtered contact list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContactList(Predicate<Contact> predicate);

    /**
     * Returns the size of the filtered contact list.
     * @return an integer denoting the size of the filtered contact list.
     */
    int getFilteredContactListSize();
    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredEventList(Predicate<Event> predicate);

    /**
     * Returns the size of the filtered event list.
     * @return an integer denoting the size of the filtered event list.
     */
    int getFilteredEventListSize();
}
