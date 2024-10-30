package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.supplier.Supplier;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Supplier> PREDICATE_SHOW_ALL_SUPPLIERS = unused -> true;
    Predicate<Delivery> PREDICATE_SHOW_ALL_DELIVERIES = unused -> true;

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
     * Returns true if a supplier with the same identity as {@code supplier} exists in the address book.
     */
    boolean hasSupplier(Supplier supplier);

    /**
     * Deletes the given supplier.
     * The supplier must exist in the address book.
     */
    void deleteSupplier(Supplier target);

    /**
     * Adds the given supplier.
     * {@code supplier} must not already exist in the address book.
     */
    void addSupplier(Supplier supplier);

    /**
     * Replaces the given supplier {@code target} with {@code editedSupplier}.
     * {@code target} must exist in the address book.
     * The supplier identity of {@code editedSupplier} must not
     * be the same as another existing supplier in the address book.
     */
    void setSupplier(Supplier target, Supplier editedSupplier);

    /** Returns an unmodifiable view of the filtered supplier list */
    ObservableList<Supplier> getFilteredSupplierList();

    /**
     * Updates the filter of the filtered supplier list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSupplierList(Predicate<Supplier> predicate);

    /**
     * Returns true if a delivery with the same identity as {@code delivery} exists in the address book.
     */
    boolean hasDelivery(Delivery delivery);

    /**
     * Adds the given delivery.
     * {@code delivery} must not already exist in the address book.
     */
    void addDelivery(Delivery delivery);

    /**
     * Deletes the given delivery.
     * The delivery must exist in the address book.
     */
    void deleteDelivery(Delivery delivery);

    /**
     * Replaces the given delivery {@code target} with {@code editedDelivery}.
     * {@code target} must exist in the address book.
     * The delivery identity of {@code editedDelivery} must not be the same as
     * another existing delivery in the address book.
     */
    void setDelivery(Delivery target, Delivery updatedDelivery);

    /**
     * Returns an unmodifiable view of the filtered delivery list
     */
    ObservableList<Delivery> getFilteredDeliveryList();

    /**
     * Updates the filter of the filtered delivery list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredDeliveryList(Predicate<Delivery> predicate);

    /**
     * Returns an unmodifiable view of the sorted delivery list.
     */
    ObservableList<Delivery> getSortedDeliveryList();

    /**
     * Updates the comparator of the sorted delivery list to sort by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void updateSortedDeliveryList(Comparator<Delivery> comparator);

    /**
     * Returns an unmodifiable view of the modified delivery list.
     */
    ObservableList<Delivery> getModifiedDeliveryList();

    /**
     * Returns an unmodifiable view of the modified supplier list.
     */
    ObservableList<Supplier> getModifiedSupplierList();
    /**
     * Returns an unmodifiable view of the sorted supplier list.
     */
    ObservableList<Supplier> getSortedSupplierList();
    /**
     * Updates the comparator of the sorted supplier list to sort by the given {@code comparator}.
     * @throws NullPointerException if {@code comparator} is null.
     */
    void updateSortedSupplierList(Comparator<Supplier> comparator);
}
