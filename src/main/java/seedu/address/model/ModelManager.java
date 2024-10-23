package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Delivery> filteredDeliveries;
    private final SortedList<Delivery> sortedDeliveries;
    private boolean isViewingFilteredList = true;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredDeliveries = new FilteredList<>(this.addressBook.getDeliveryList());
        sortedDeliveries = new SortedList<>(this.addressBook.getDeliveryList());
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

    //=========== Person List Methods ==========================================================================
    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    //=========== Delivery List Methods ======================================================================
    @Override
    public boolean hasDelivery(Delivery delivery) {
        requireNonNull(delivery);
        return addressBook.hasDelivery(delivery);
    }

    @Override
    public void deleteDelivery(Delivery target) {
        addressBook.removeDelivery(target);
    }

    @Override
    public void addDelivery(Delivery target) {
        addressBook.addDelivery(target);
        updateFilteredDeliveryList(PREDICATE_SHOW_ALL_DELIVERIES); // Refresh the list after adding
    }

    @Override
    public void setDelivery(Delivery target, Delivery updatedDelivery) {
        requireNonNull(target);
        requireNonNull(updatedDelivery);

        addressBook.setDelivery(target, updatedDelivery);
    }

    @Override
    public ObservableList<Delivery> getModifiedDeliveryList() {
        return isViewingFilteredList ? getFilteredDeliveryList() : getSortedDeliveryList();
    }

    //=========== Filtered Delivery List Accessors =============================================================
    @Override
    public ObservableList<Delivery> getFilteredDeliveryList() {
        return filteredDeliveries;
    }

    @Override
    public void updateFilteredDeliveryList(Predicate<Delivery> predicate) {
        requireNonNull(predicate);
        isViewingFilteredList = true;
        filteredDeliveries.setPredicate(predicate);
    }

    //=========== Sorted Delivery List Accessors ===============================================================
    @Override
    public ObservableList<Delivery> getSortedDeliveryList() {
        return sortedDeliveries;
    }

    @Override
    public void updateSortedDeliveryList(Comparator<Delivery> comparator) {
        requireNonNull(comparator);
        isViewingFilteredList = false;
        sortedDeliveries.setComparator(comparator);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
