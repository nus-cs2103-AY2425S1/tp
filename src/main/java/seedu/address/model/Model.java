package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Client> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

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
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    boolean hasPerson(Client client);

    /**
     * Deletes the given client.
     * The client must exist in the address book.
     */
    void deletePerson(Client target);

    /**
     * Adds the given client.
     * {@code client} must not already exist in the address book.
     */
    void addPerson(Client client);

    /**
     * Replaces the given client {@code target} with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    void setPerson(Client target, Client editedClient);

    /**
     * Returns true if a rental information with the same details as {@code rentalInformation} exists
     * for the specified client in the address book.
     */
    boolean hasRentalInformation(Client client, RentalInformation rentalInformation);

    /** Returns an unmodifiable view of the filtered client list */
    ObservableList<Client> getFilteredPersonList();

    /**
     * Updates the filter of the filtered client list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Client> predicate);

    /** Returns an unmodifiable view of the sorted client list */
    ObservableList<Client> getSortedPersonList();

    /**
     * Updates the sorted client list.
     */
    void updateSortedPersonList(Comparator<Client> comparator);

    /** Returns an unmodifiable view of the visible rental information list. */
    ObservableList<RentalInformation> getVisibleRentalInformationList();

    /**
     * Updates the rental information that is visible in the right pane.
     * @throws NullPointerException if {@code rentalInformation} is null.
     */
    void updateVisibleRentalInformationList(List<RentalInformation> rentalInformationList);

    /** Returns the last viewed client as ObjectProperty */
    ObjectProperty<Client> getLastViewedClientAsObjectProperty();

    /** Returns the last viewed client as Client */
    Client getLastViewedClient();

    /**
     * Sets the last viewed client of this model.
     */
    void setLastViewedClient(Client client);

    String getPreviousCommand();

    String getNextCommand();
}
