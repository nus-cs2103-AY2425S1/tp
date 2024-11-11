package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.property.Property;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;
    Predicate<Client> PREDICATE_SHOW_ALL_BUYERS_ONLY = Client::isBuyer;
    Predicate<Client> PREDICATE_SHOW_ALL_SELLERS_ONLY = Client::isSeller;
    Predicate<Meeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;
    Predicate<Property> PREDICATE_SHOW_ALL_PROPERTIES = unused -> true;

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
     * Returns the user prefs' client book file path.
     */
    Path getClientBookFilePath();

    /**
     * Sets the user prefs' client book file path.
     */
    void setClientBookFilePath(Path clientBookFilePath);

    /**
     * Replaces client book data with the data in {@code clientBook}.
     */
    void setClientBook(ReadOnlyClientBook clientBook);

    /** Returns the ClientBook */
    ReadOnlyClientBook getClientBook();

    /**
     * Returns true if a Client with the same identity as {@code client} exists in the client book.
     */
    boolean hasClient(Client client);

    /**
     * Returns true if {@code client} is a Buyer and a Buyer with the same email as {@code client}
     * exists in the client book or if {@code client} is a Seller and a Seller with the same email
     * as {@code client} exists in the client book.
     */
    boolean sameEmailExists(Client client);

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
     * The client identity of {@code editedClient} must not be the same as another existing client in the client book.
     */
    void setClient(Client target, Client editedClient);

    /** Returns an unmodifiable view of the filtered client list */
    ObservableList<Client> getFilteredClientList();

    /**
     * Checks if the filtered client list is empty.
     *
     * @return {@code true} if the filtered client list is empty, {@code false} otherwise.
     */
    boolean isFilteredClientListEmpty();

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
     * Checks if the filtered meeting list is empty.
     *
     * @return {@code true} if the filtered meeting list is empty, {@code false} otherwise.
     */
    boolean isFilteredMeetingListEmpty();

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
     * Checks if the filtered property list is empty.
     *
     * @return {@code true} if the filtered property list is empty, {@code false} otherwise.
     */
    boolean isFilteredPropertyListEmpty();

    /**
     * Updates the filter of the filtered meeting list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMeetingList(Predicate<Meeting> predicate);

    // Managing UI
    ReadOnlyObjectProperty<ModelManager.DisplayMode> getReadOnlyDisplayMode();
    void setDisplayClients();
    void setDisplayProperties();
    void setDisplayMeetings();
}
