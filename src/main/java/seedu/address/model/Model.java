package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.property.Property;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Client> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;
    Predicate<Client> PREDICATE_SHOW_ALL_BUYERS_ONLY = Client::isBuyer;
    Predicate<Client> PREDICATE_SHOW_ALL_SELLERS_ONLY = Client::isSeller;
    Predicate<Meeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;

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

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns the user prefs' client book file path.
     */
    Path getClientBookFilePath();

    /**
     * Sets the user prefs' client book file path.
     */
    void setClientBookFilePath(Path addressBookFilePath);

    /**
     * Replaces client book data with the data in {@code clientBook}.
     */
    void setClientBook(ReadOnlyClientBook clientBook);

    /** Returns the ClientBook */
    ReadOnlyClientBook getClientBook();

    /**
     * Returns true if a Client with the same identity as {@code Client} exists in the address book.
     */
    boolean hasClient(Client client);

    /**
     * Deletes the given client.
     * The person must exist in the client book.
     */
    void deleteClient(Client target);

    /**
     * Adds the given client.
     * {@code client} must not already exist in the client book.
     */
    void addClient(Client client);

    /**
     * Replaces the given client {@code target} with {@code editedClient}.
     * {@code target} must exist in the client book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    void setClient(Client target, Client editedClient);

    /** Returns an unmodifiable view of the filtered client list */
    ObservableList<Client> getFilteredClientList();

    /**
     * Updates the filter of the filtered client list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClientList(Predicate<Client> predicate);

    /**
     * Returns the user prefs' property book file path.
     */
    Path getPropertyBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setPropertyBookFilePath(Path propertyBookFilePath);

    /**
     * Returns the property book.
     */
    ReadOnlyPropertyBook getPropertyBook();

    /**
     * Returns true if a person with the same identity as {@code property} exists in the address book.
     */
    boolean hasProperty(Property property);

    /**
     * Deletes the given property.
     * {@code property} must exist in the address book.
     */
    void deleteProperty(Property property);

    /**
     * Adds the given property.
     * {@code property} must not already exist in the address book.
     */
    void addProperty(Property property);

    /** Returns an unmodifiable view of the filtered property list */
    ObservableList<Property> getFilteredPropertyList();

    /**
     * Updates the filter of the filtered property list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPropertyList(Predicate<Property> predicate);

    /**
     * Returns the user prefs' meeting book file path.
     */
    Path getMeetingBookFilePath();

    /**
     * Sets the user prefs' meeting book file path.
     */
    void setMeetingBookFilePath(Path meetingBookFilePath);

    /**
     * Replaces meeting book data with the data in {@code meetingBook}.
     */
    void setMeetingBook(ReadOnlyMeetingBook meetingBook);

    /**
     * Returns the meeting book.
     */
    ReadOnlyMeetingBook getMeetingBook();

    /**
     * Returns true if a meeting with the same identity as {@code meeting} exists in the meeting book.
     */
    boolean hasMeeting(Meeting meeting);
    /**
     * Adds the given meeting.
     * {@code meeting} must not already exist in the meeting book.
     */
    void addMeeting(Meeting meeting);

    /**
     * Deletes the given meeting.
     * {@code meeting} must exist in the meeting book.
     */
    void deleteMeeting(Meeting meeting);


    /** Returns an unmodifiable view of the filtered meeting list */
    ObservableList<Meeting> getFilteredMeetingList();

    /**
     * Updates the filter of the filtered meeting list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMeetingList(Predicate<Meeting> predicate);

    // Managing UI
    ObjectProperty<ModelManager.DisplayMode> getDisplayMode();
    void setDisplayClients();
    void setDisplayProperties();
    void setDisplayMeetings();
}
