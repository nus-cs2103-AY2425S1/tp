package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.client.Client;
import seedu.address.model.rentalinformation.RentalInformation;
import seedu.address.storage.CommandHistoryStorage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Client> filteredClients;
    private final SortedList<Client> sortedClients;
    private final ObservableList<RentalInformation> visibleRentalInformationList;
    private final ObjectProperty<Client> lastViewedClient = new SimpleObjectProperty<>();
    private final CommandHistoryStorage commandHistoryStorage = new CommandHistoryStorage();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredClients = new FilteredList<>(this.addressBook.getPersonList());
        sortedClients = new SortedList<>(this.addressBook.getPersonList());
        visibleRentalInformationList = FXCollections.observableArrayList();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Client client) {
        requireNonNull(client);
        return addressBook.hasPerson(client);
    }

    @Override
    public void deletePerson(Client target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Client client) {
        addressBook.addPerson(client);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Client target, Client editedClient) {
        requireAllNonNull(target, editedClient);
        addressBook.setPerson(target, editedClient);
    }

    @Override
    public boolean hasRentalInformation(Client client, RentalInformation rentalInformation) {
        requireNonNull(client);
        requireNonNull(rentalInformation);

        return addressBook.hasRentalInformation(client, rentalInformation);
    }

    //=========== Filtered Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Client> getFilteredPersonList() {
        return filteredClients;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);

        if (filteredClients.isEmpty()) {
            updateVisibleRentalInformationList(List.of());
            setLastViewedClient(null);
        }
    }

    //=========== Sorted Client List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Client> getSortedPersonList() {
        return sortedClients;
    }

    @Override
    public void updateSortedPersonList(Comparator<Client> comparator) {
        requireNonNull(comparator);
        sortedClients.setComparator(comparator);
    }

    //=========== Visible Rental Information =================================================================
    @Override
    public ObservableList<RentalInformation> getVisibleRentalInformationList() {
        return visibleRentalInformationList;
    }

    @Override
    public void updateVisibleRentalInformationList(List<RentalInformation> rentalInformationList) {
        requireNonNull(rentalInformationList);
        visibleRentalInformationList.setAll(rentalInformationList);
    }

    @Override
    public ObjectProperty<Client> getLastViewedClientAsObjectProperty() {
        return lastViewedClient;
    }

    @Override
    public Client getLastViewedClient() {
        return lastViewedClient.get();
    }

    @Override
    public void setLastViewedClient(Client client) {
        this.lastViewedClient.set(client);
    }

    //=========== Command History =================================================================
    @Override
    public String getPreviousCommand() {
        return commandHistoryStorage.getPreviousCommand();
    }
    @Override
    public String getNextCommand() {
        return commandHistoryStorage.getNextCommand();
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredClients.equals(otherModelManager.filteredClients);
    }

}
