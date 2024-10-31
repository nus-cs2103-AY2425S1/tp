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
import seedu.address.model.supplier.Supplier;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Supplier> filteredSuppliers;
    private final FilteredList<Delivery> filteredDeliveries;
    private final SortedList<Delivery> sortedDeliveries;
    private final SortedList<Supplier> sortedSuppliers;
    private boolean isViewingSupplierFilteredList = true;

    private boolean isViewingDeliveryFilteredList = true;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredSuppliers = new FilteredList<>(this.addressBook.getSupplierList());
        filteredDeliveries = new FilteredList<>(this.addressBook.getDeliveryList());
        sortedDeliveries = new SortedList<>(this.addressBook.getDeliveryList());
        sortedSuppliers = new SortedList<>(this.addressBook.getSupplierList());
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

    //=========== Supplier List Methods ==========================================================================
    @Override
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return addressBook.hasSupplier(supplier);
    }

    @Override
    public void deleteSupplier(Supplier target) {
        addressBook.removeSupplier(target);
    }

    @Override
    public void addSupplier(Supplier supplier) {
        addressBook.addSupplier(supplier);
        updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
    }

    @Override
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireAllNonNull(target, editedSupplier);

        addressBook.setSupplier(target, editedSupplier);
    }

    @Override
    public ObservableList<Supplier> getModifiedSupplierList() {
        return isViewingSupplierFilteredList ? getFilteredSupplierList() : getSortedSupplierList();
    }

    //===========Sorted Supplier List Accessors  ====================================================================
    @Override
    public ObservableList<Supplier> getSortedSupplierList() {
        return sortedSuppliers;
    }
    @Override
    public void updateSortedSupplierList(Comparator<Supplier> comparator) {
        requireNonNull(comparator);
        isViewingSupplierFilteredList = false;
        sortedSuppliers.setComparator(comparator);
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
        return isViewingDeliveryFilteredList ? getFilteredDeliveryList() : getSortedDeliveryList();
    }

    //=========== Filtered Delivery List Accessors =============================================================
    @Override
    public ObservableList<Delivery> getFilteredDeliveryList() {
        return filteredDeliveries;
    }

    @Override
    public void updateFilteredDeliveryList(Predicate<Delivery> predicate) {
        requireNonNull(predicate);
        isViewingDeliveryFilteredList = true;
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
        isViewingDeliveryFilteredList = false;
        sortedDeliveries.setComparator(comparator);
    }

    //=========== Filtered Supplier List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Supplier} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Supplier> getFilteredSupplierList() {
        return filteredSuppliers;
    }

    @Override
    public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
        requireNonNull(predicate);
        isViewingSupplierFilteredList = true;
        filteredSuppliers.setPredicate(predicate);
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
                && filteredSuppliers.equals(otherModelManager.filteredSuppliers);
    }

}
